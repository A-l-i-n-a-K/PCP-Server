package com.fitquest.fitquest_backend.repository;

import com.fitquest.fitquest_backend.model.SportsmanProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SportsmanProfileRepository extends JpaRepository<SportsmanProfile, Long> {
    Optional<SportsmanProfile> findById(Long id);
}

