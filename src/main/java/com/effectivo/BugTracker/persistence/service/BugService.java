package com.effectivo.BugTracker.persistence.service;

import com.effectivo.BugTracker.persistence.model.Bug;
import com.effectivo.BugTracker.persistence.model.Project;
import com.effectivo.BugTracker.persistence.repository.IBugRepository;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BugService {

    private IBugRepository IBugRepository;

    public BugService(IBugRepository IBugRepository){
        this.IBugRepository = IBugRepository;
    }

    public void createBug(Bug bug){
        IBugRepository.save(bug);
    }

    public Bug updateBug(Bug bug){
       return IBugRepository.save(bug);
    }

    public List<Bug> findBugsByProjectId(Long projectId){
        return new ArrayList<>(IBugRepository.findByProjectId(projectId));

        //List<Bug> retrievedProjects = new ArrayList<>();
        //IBugRepository.findByProjectId(projectId).forEach(retrievedProjects::add);
        //return retrievedProjects;
    }


    public Optional<Bug> getBugById(Long id){
        return IBugRepository.findById(id);
    }

    public void deleteBug(Long id){
        IBugRepository.deleteById(id);
    }

    public Boolean existById(Long id){
        return IBugRepository.existsById(id);
    }

    public Integer countBugs(Long projectId){
        return IBugRepository.findByProjectId(projectId).size();
    }
}
