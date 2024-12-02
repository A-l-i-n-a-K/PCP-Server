package com.fitquest.fitquest_backend.repository;

import com.fitquest.fitquest_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
