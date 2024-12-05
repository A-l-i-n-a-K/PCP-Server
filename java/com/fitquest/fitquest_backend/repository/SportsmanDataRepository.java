package com.fitquest.fitquest_backend.repository;

import com.fitquest.fitquest_backend.model.SportsmanData;
import com.fitquest.fitquest_backend.model.SportsmanDataId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SportsmanDataRepository extends JpaRepository<SportsmanData, SportsmanDataId> {
    List<SportsmanData> findBySportsmanId(Long sportsmanId);

    @Query("SELECT d FROM SportsmanData d WHERE d.sportsmanId = :sportsmanId AND d.indicator = :indicator AND " +
            "(:startDate IS NULL OR d.date >= :startDate) AND " +
            "(:endDate IS NULL OR d.date <= :endDate)")
    List<SportsmanData> findFilteredData(
            @Param("sportsmanId") Long sportsmanId,
            @Param("indicator") String indicator,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
}
