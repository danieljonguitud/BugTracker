package com.effectivo.BugTracker.web.controller;

import com.effectivo.BugTracker.persistence.model.Project;
import com.effectivo.BugTracker.persistence.model.dto.ProjectDto;
import com.effectivo.BugTracker.persistence.service.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModelMapper modelMapper;

    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @GetMapping()
    public List<ProjectDto> getAllProjects(){
        List<Project> projects = projectService.findAllProjects();
        return projects.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProjectDto getProjectById(@PathVariable Long id){
        Optional<Project> project = projectService.getProjectById(id);
        Project value = project.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return convertToDto(value);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(@RequestBody ProjectDto newProjectDto){
        Project newProject = convertToEntity(newProjectDto);
        projectService.createProject(newProject);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProject(@RequestBody ProjectDto newProjectDto, @PathVariable Long id){
        Project newProject = convertToEntity(newProjectDto);

        projectService.getProjectById(id).map(project -> {
            project.setName(newProject.getName());
            project.setDescription(newProject.getDescription());
            return projectService.updateProject(project);
        }).orElseGet(() -> {
            newProject.setId(id);
            return projectService.updateProject(newProject);
        });
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProject(@PathVariable Long id){
        projectService.deleteProject(id);
    }

    //Converting to DTO - ENTITY

    private ProjectDto convertToDto(Project project) {
        ProjectDto projectDto = modelMapper.map(project, ProjectDto.class);
        return projectDto;
    }

    private Project convertToEntity(ProjectDto projectDto) {
        Project project = modelMapper.map(projectDto, Project.class);

        if (projectDto.getId() != null) {
            Optional<Project> oldProject = projectService.getProjectById(projectDto.getId());
        }
        return project;
    }
}
