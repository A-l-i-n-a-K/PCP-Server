package com.fitquest.fitquest_backend.repository;

import com.fitquest.fitquest_backend.model.SportsmanProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SportsmanProfileRepository extends JpaRepository<SportsmanProfile, Long> {
    SportsmanProfile findByEmail(String email);
}
