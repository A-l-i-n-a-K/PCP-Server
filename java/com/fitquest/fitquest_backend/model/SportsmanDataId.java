package com.fitquest.fitquest_backend.model;

// Класс составного ключа
import java.io.Serializable;

public class SportsmanDataId implements Serializable {
    private Long sportsmanId;
    private Long dataId;

    public Long getSportsmanId() {
        return sportsmanId;
    }

    public void setSportsmanId(Long sportsmanId) {
        this.sportsmanId = sportsmanId;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }
}