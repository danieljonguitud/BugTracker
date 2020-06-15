package com.effectivo.BugTracker.persistence.repository;

import com.effectivo.BugTracker.persistence.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByUserId(Long userId);
}
