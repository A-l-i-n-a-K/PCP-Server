package com.fitquest.fitquest_backend.model;

import com.fitquest.fitquest_backend.model.enums.Gender;
import com.fitquest.fitquest_backend.model.enums.SportType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SportsmanProfile {

    @Id
    private Long id;
    private String name;
    private int age;
    private String email;
    private String phone;
    private Gender gender;
    private SportType sport;
    private Long coachId;
    private String profilePhoto;  // Хранит Base64 строку для фото
    private String fio; // Новое поле для ФИО

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public SportType getSport() {
        return sport;
    }

    public void setSport(SportType sport) {
        this.sport = sport;
    }

    public Long getCoachId() {
        return coachId;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }
}
