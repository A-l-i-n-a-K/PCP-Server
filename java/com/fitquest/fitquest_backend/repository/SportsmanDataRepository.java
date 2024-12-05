package com.fitquest.fitquest_backend.repository;

import com.fitquest.fitquest_backend.model.SportsmanData;
import com.fitquest.fitquest_backend.model.SportsmanDataId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SportsmanDataRepository extends JpaRepository<SportsmanData, SportsmanDataId> {
    Optional<SportsmanData> findById(SportsmanDataId id);
    List<SportsmanData> findBySportsmanId(Long sportsmanId);
}

