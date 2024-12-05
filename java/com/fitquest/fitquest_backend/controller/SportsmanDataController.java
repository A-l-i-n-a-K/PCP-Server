package com.fitquest.fitquest_backend.controller;

import com.fitquest.fitquest_backend.model.SportsmanData;
import com.fitquest.fitquest_backend.model.SportsmanDataId;
import com.fitquest.fitquest_backend.repository.SportsmanDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/sportsmanData")
@CrossOrigin("http://localhost:3000")
public class SportsmanDataController {

    @Autowired
    private SportsmanDataRepository sportsmanRepository;

    @GetMapping("/datas")
    public List<SportsmanData> getSportsmanDataBySportsmanId(@RequestParam Long sportsmanId) {
        return sportsmanRepository.findBySportsmanId(sportsmanId);
    }

    @GetMapping("/statistics")
    public Map<String, Double> getStatistics(
            @RequestParam Long sportsmanId,
            @RequestParam String indicator,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate
    ) {
        // Логирование полученных параметров
        System.out.println("Получены параметры: sportsmanId=" + sportsmanId + ", indicator=" + indicator + ", startDate=" + startDate + ", endDate=" + endDate);

        // Получаем отфильтрованные данные с учетом того, что даты могут быть пустыми
        List<SportsmanData> dataList = sportsmanRepository.findFilteredData(sportsmanId, indicator, startDate, endDate);

        // Вычисление средней величины
        double averageValue = dataList.stream()
                .mapToDouble(data -> Double.parseDouble(data.getMeaning()))
                .average()
                .orElse(0);

        // Вычисление среднего изменения
        double averageChange = 0;
        if (dataList.size() > 1) {
            double[] values = dataList.stream()
                    .mapToDouble(data -> Double.parseDouble(data.getMeaning()))
                    .toArray();
            for (int i = 1; i < values.length; i++) {
                averageChange += Math.abs(values[i] - values[i - 1]);
            }
            averageChange /= (values.length - 1);
        }

        // Возврат статистики
        Map<String, Double> statistics = new HashMap<>();
        statistics.put("averageValue", averageValue);
        statistics.put("averageChange", averageChange);
        return statistics;
    }


    @PostMapping("/data")
    public List<SportsmanData> newSportsmanData(@RequestBody List<SportsmanData> newSportsmanDataList) {
        return sportsmanRepository.saveAll(newSportsmanDataList);
    }

    @PutMapping("/data/{sportsmanId}/{dataId}")
    public SportsmanData updateSportsmanData(
            @RequestBody SportsmanData newSportsmanData,
            @PathVariable Long sportsmanId,
            @PathVariable Long dataId
    ) {
        SportsmanDataId sportsmanDataId = new SportsmanDataId();
        sportsmanDataId.setSportsmanId(sportsmanId);
        sportsmanDataId.setDataId(dataId);

        return sportsmanRepository.findById(sportsmanDataId)
                .map(sportsmanData -> {
                    sportsmanData.setMeaning(newSportsmanData.getMeaning());
                    sportsmanData.setDate(newSportsmanData.getDate());
                    return sportsmanRepository.save(sportsmanData);
                })
                .orElseThrow(() -> new RuntimeException("Данные не найдены для ID: " + sportsmanDataId));
    }

    @DeleteMapping("/data/{sportsmanId}/{dataId}")
    public void deleteSportsmanData(@PathVariable Long sportsmanId, @PathVariable Long dataId) {
        SportsmanDataId sportsmanDataId = new SportsmanDataId();
        sportsmanDataId.setSportsmanId(sportsmanId);
        sportsmanDataId.setDataId(dataId);

        sportsmanRepository.findById(sportsmanDataId)
                .ifPresentOrElse(
                        sportsmanData -> sportsmanRepository.delete(sportsmanData),
                        () -> {
                            throw new RuntimeException("Данные не найдены для ID: " + sportsmanDataId);
                        }
                );
    }
}
