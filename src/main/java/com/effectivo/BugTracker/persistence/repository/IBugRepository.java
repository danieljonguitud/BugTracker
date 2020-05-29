package com.effectivo.BugTracker.persistence.repository;

import com.effectivo.BugTracker.persistence.model.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBugRepository extends JpaRepository<Bug, Long> {
    List<Bug> findByProjectId(Long projectId);
}
