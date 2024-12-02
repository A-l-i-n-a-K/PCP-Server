package com.fitquest.fitquest_backend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    COACH("Тренер"),
    SPORTSMAN("Спортсмен");

    private final String name;

    // Перепишем метод toString(), чтобы возвращать русскую строку
    @Override
    public String toString() {
        return name;
    }
}
