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
    private UserRepository userRepository; // Подключение к репозиторию пользователей

    // Получение профиля по ID или создание нового профиля, если его нет
    @GetMapping("/{id}")
    public ResponseEntity<SportsmanProfile> getProfileById(@PathVariable Long id) {
        try {
            // Попытка найти профиль по ID
            SportsmanProfile profile = sportsmanProfileRepository.findById(id).orElse(null);

            if (profile == null) {
                // Если профиль не найден, создаем новый профиль с дефолтными значениями
                profile = createDefaultProfile(id);
                sportsmanProfileRepository.save(profile); // Сохраняем новый профиль
            }

            // Возвращаем профиль
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            // Логирование ошибки и возврат статуса 500
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    // Метод для создания нового профиля с дефолтными значениями
    private SportsmanProfile createDefaultProfile(Long id) {
        SportsmanProfile newProfile = new SportsmanProfile();

        // Получаем пользователя по ID из базы данных
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            newProfile.setId(id);
            newProfile.setName(user.getName()); // Логин из данных пользователя
            newProfile.setEmail(user.getEmail()); // Почта из данных пользователя
        } else {
            // Если пользователь не найден, создаем профиль с дефолтными значениями
            newProfile.setName(""); // Логин по умолчанию
            newProfile.setEmail(""); // Почта по умолчанию
        }

        newProfile.setFio("ФИО");
        newProfile.setAge(0);
        newProfile.setPhone("+375330000000");
        newProfile.setGender(Gender.MALE); // Пол по умолчанию
        newProfile.setSport(SportType.ATHLETICS);
        newProfile.setCoachId(null); // ID тренера может быть пустым
        newProfile.setProfilePhoto(""); // Пустая ссылка на фото

        return newProfile;
    }

    @PostMapping("/sportsmanProfileData")
    public SportsmanProfile newSportsmanProfile(@RequestBody SportsmanProfile newSportsmanProfile) {
        return sportsmanProfileRepository.save(newSportsmanProfile);
    }

    // Обновление профиля
    @PostMapping("/updateSportsmanProfile")
    public SportsmanProfile updateProfile(@RequestBody SportsmanProfile updatedProfile) {
        return sportsmanProfileRepository.save(updatedProfile);
    }
}
