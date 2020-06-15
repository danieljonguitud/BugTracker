package com.effectivo.BugTracker.persistence.service;

import com.effectivo.BugTracker.persistence.model.Project;
import com.effectivo.BugTracker.persistence.repository.IProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private IProjectRepository IProjectRepository;

    public ProjectService (IProjectRepository IProjectRepository){
        this.IProjectRepository = IProjectRepository;
    }

    public void createProject(Project project){
       IProjectRepository.save(project);
    }

    public Project updateProject(Project project){
       return IProjectRepository.save(project);
    }

    public List<Project> findAllProjectsByUserId(Long userId){
        List<Project> retrievedProjects = new ArrayList<>();
        IProjectRepository.findAllByUserId(userId).forEach(retrievedProjects::add);
        return retrievedProjects;
    }

    public Optional<Project> getProjectById(Long id){
        Optional<Project> project = IProjectRepository.findById(id);
        return project;
    }

    public void deleteProject(Long id){
        IProjectRepository.deleteById(id);
    }

    public Boolean existById(Long id){
        return IProjectRepository.existsById(id);
    }

}
