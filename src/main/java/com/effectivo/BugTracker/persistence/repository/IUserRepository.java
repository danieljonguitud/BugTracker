package com.effectivo.BugTracker.persistence.repository;

import com.effectivo.BugTracker.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
