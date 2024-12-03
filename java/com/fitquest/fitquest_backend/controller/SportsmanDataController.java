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
    List<SportsmanData> getAllSportsmanData(){
        return sportsmanRepository.findAll();
    }

    @GetMapping("/datas/{dataId}")
    public SportsmanData getUserById(@PathVariable SportsmanDataId dataId){
        try{ return sportsmanRepository.findById(dataId)
                .orElseThrow(() -> new RuntimeException("Данные не найден для ID: " + dataId));
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при получении данных: " + e.getMessage(), e);
        }
    }

    @PutMapping("/data/{dataId}")
    public SportsmanData updateSportsmanData(@RequestBody SportsmanData newSportsmanData, @PathVariable SportsmanDataId dataId) {
        return sportsmanRepository.findById(dataId)
                .map(sportsmanData -> {
                    sportsmanData.setIndicator(newSportsmanData.getIndicator());
                    sportsmanData.setMeaning(newSportsmanData.getMeaning());
                    sportsmanData.setDate(newSportsmanData.getDate());
                    return sportsmanRepository.save(sportsmanData);
                })
                .orElseThrow(() -> new RuntimeException("Данные не найдены для ID: " + dataId));
    }
}