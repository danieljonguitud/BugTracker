package com.effectivo.BugTracker.persistence.repository;

import com.effectivo.BugTracker.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
