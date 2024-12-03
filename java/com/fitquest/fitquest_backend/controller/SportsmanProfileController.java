package com.fitquest.fitquest_backend.controller;

import com.fitquest.fitquest_backend.model.SportsmanProfile;
import com.fitquest.fitquest_backend.model.User;
import com.fitquest.fitquest_backend.model.enums.Gender;
import com.fitquest.fitquest_backend.model.enums.SportType;
import com.fitquest.fitquest_backend.repository.SportsmanProfileRepository;
import com.fitquest.fitquest_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/sportsmanProfile")
@CrossOrigin("http://localhost:3000")
public class SportsmanProfileController {

    @Autowired
    private SportsmanProfileRepository sportsmanProfileRepository;

    @Autowired
    private UserRepository userRepository;

    // Получение профиля по ID или создание нового профиля
    @GetMapping("/{id}")
    public ResponseEntity<SportsmanProfile> getProfileById(@PathVariable Long id) {
        try {
            SportsmanProfile profile = sportsmanProfileRepository.findById(id).orElse(null);

            if (profile == null) {
                profile = createDefaultProfile(id);
                sportsmanProfileRepository.save(profile);
            }

            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Метод для создания нового профиля с дефолтными значениями
    private SportsmanProfile createDefaultProfile(Long id) {
        SportsmanProfile newProfile = new SportsmanProfile();
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            newProfile.setId(id);
            newProfile.setName(user.getName());
            newProfile.setEmail(user.getEmail());
        } else {
            newProfile.setName("");
            newProfile.setEmail("");
        }

        newProfile.setFio("ФИО");
        newProfile.setAge(0);
        newProfile.setPhone("+375330000000");
        newProfile.setGender(Gender.MALE);
        newProfile.setSport(SportType.ATHLETICS);
        newProfile.setCoachId(null);
        newProfile.setProfilePhoto(""); // Пустая строка по умолчанию
        return newProfile;
    }

    // Обновление профиля
    @PutMapping("/sportsmanProfileData/{id}")
    public SportsmanProfile updateSportsmanProfile(@RequestBody SportsmanProfile newSportsmanProfile, @PathVariable Long id) {
        return sportsmanProfileRepository.findById(id)
                .map(sportsmanProfile -> {
                    sportsmanProfile.setName(newSportsmanProfile.getName());
                    sportsmanProfile.setFio(newSportsmanProfile.getFio());
                    sportsmanProfile.setAge(newSportsmanProfile.getAge());
                    sportsmanProfile.setEmail(newSportsmanProfile.getEmail());
                    sportsmanProfile.setPhone(newSportsmanProfile.getPhone());
                    sportsmanProfile.setGender(newSportsmanProfile.getGender());
                    sportsmanProfile.setSport(newSportsmanProfile.getSport());
                    sportsmanProfile.setCoachId(newSportsmanProfile.getCoachId());
                    sportsmanProfile.setProfilePhoto(newSportsmanProfile.getProfilePhoto()); // Сохраняем новое фото
                    return sportsmanProfileRepository.save(sportsmanProfile);
                })
                .orElseThrow(() -> new RuntimeException("Профиль не найден для ID: " + id));
    }
}
