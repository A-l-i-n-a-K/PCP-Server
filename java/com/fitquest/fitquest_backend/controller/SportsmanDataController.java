package com.fitquest.fitquest_backend.controller;

import com.fitquest.fitquest_backend.model.SportsmanData;
import com.fitquest.fitquest_backend.model.SportsmanDataId;
import com.fitquest.fitquest_backend.repository.SportsmanDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sportsmanData")
@CrossOrigin("http://localhost:3000")
public class SportsmanDataController {

    @Autowired
    private SportsmanDataRepository sportsmanRepository;

    @PostMapping("/data")
    public List<SportsmanData> newSportsmanData(@RequestBody List<SportsmanData> newSportsmanDataList){
        return sportsmanRepository.saveAll(newSportsmanDataList);
    }

    @GetMapping("/datas")
    public List<SportsmanData> getSportsmanDataBySportsmanId(@RequestParam Long sportsmanId) {
        return sportsmanRepository.findBySportsmanId(sportsmanId);
    }

    @GetMapping("/datas/{dataId}")
    public SportsmanData getUserById(@PathVariable SportsmanDataId dataId){
        try{ return sportsmanRepository.findById(dataId)
                .orElseThrow(() -> new RuntimeException("Данные не найден для ID: " + dataId));
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении данных: " + e.getMessage(), e);
        }
    }

    @PutMapping("/data/{sportsmanId}/{dataId}")
    public SportsmanData updateSportsmanData(@RequestBody SportsmanData newSportsmanData,
                                             @PathVariable Long sportsmanId,
                                             @PathVariable Long dataId) {
        // Создаем составной ключ для поиска
        SportsmanDataId sportsmanDataId = new SportsmanDataId();
        sportsmanDataId.setSportsmanId(sportsmanId);
        sportsmanDataId.setDataId(dataId);

        // Ищем данные по составному ключу
        return sportsmanRepository.findById(sportsmanDataId)
                .map(sportsmanData -> {
                    // Обновляем только те поля, которые приходят в запросе
                    sportsmanData.setMeaning(newSportsmanData.getMeaning());
                    sportsmanData.setDate(newSportsmanData.getDate());
                    // Сохраняем обновленные данные в базе
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
                .ifPresentOrElse(sportsmanData -> {
                    sportsmanRepository.delete(sportsmanData);
                }, () -> {
                    throw new RuntimeException("Данные не найдены для ID: " + sportsmanDataId);
                });
    }


}