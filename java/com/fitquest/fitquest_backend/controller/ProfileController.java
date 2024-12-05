package com.fitquest.fitquest_backend.controller;

import com.fitquest.fitquest_backend.model.Profile;
import com.fitquest.fitquest_backend.model.User;
import com.fitquest.fitquest_backend.model.enums.Gender;
import com.fitquest.fitquest_backend.model.enums.SportType;
import com.fitquest.fitquest_backend.repository.ProfileRepository;
import com.fitquest.fitquest_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Profile")
@CrossOrigin("http://localhost:3000")
public class ProfileController {

    @Autowired
    private ProfileRepository sportsmanProfileRepository;

    @Autowired
    private UserRepository userRepository;

    // Получение профиля по ID или создание нового профиля
    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable Long id) {
        try {
            Profile profile = sportsmanProfileRepository.findById(id).orElse(null);

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
    private Profile createDefaultProfile(Long id) {
        Profile newProfile = new Profile();
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
    @PutMapping("/ProfileData/{id}")
    public ResponseEntity<?> updateSportsmanProfile(@RequestBody Profile newSportsmanProfile, @PathVariable Long id) {
        try {
            Profile updatedProfile = sportsmanProfileRepository.findById(id)
                    .map(sportsmanProfile -> {
                        sportsmanProfile.setName(newSportsmanProfile.getName());
                        sportsmanProfile.setFio(newSportsmanProfile.getFio());
                        sportsmanProfile.setAge(newSportsmanProfile.getAge());
                        sportsmanProfile.setEmail(newSportsmanProfile.getEmail());
                        sportsmanProfile.setPhone(newSportsmanProfile.getPhone());
                        sportsmanProfile.setGender(newSportsmanProfile.getGender());
                        sportsmanProfile.setSport(newSportsmanProfile.getSport());
                        sportsmanProfile.setCoachId(newSportsmanProfile.getCoachId());

                        if (newSportsmanProfile.getProfilePhoto() != null) {
                            sportsmanProfile.setProfilePhoto(newSportsmanProfile.getProfilePhoto());
                        }

                        return sportsmanProfileRepository.save(sportsmanProfile);
                    })
                    .orElseThrow(() -> new RuntimeException("Профиль не найден для ID: " + id));

            return ResponseEntity.ok(updatedProfile);
        } catch (Exception e) {
            e.printStackTrace(); // Выводим ошибку в лог
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка обновления профиля: " + e.getMessage());
        }
    }


    // Получить всех спортсменов для конкретного тренера
    @GetMapping("/coach/{coachId}")
    public ResponseEntity<List<Profile>> getSportsmenByCoach(@PathVariable Long coachId) {
        try {
            List<Profile> sportsmen = sportsmanProfileRepository.findByCoachId(coachId);
            return ResponseEntity.ok(sportsmen);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}