package com.fitquest.fitquest_backend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SportType {
    ATHLETICS("Лёгкая атлетика"),
    SWIMMING("Плавание"),
    BASCETBALL("Баскетбол"),
    VOLLEYBALL("Волейбол");

    private final String name;

    // Перепишем метод toString(), чтобы возвращать русскую строку
    @Override
    public String toString() {
        return name;
    }
}
