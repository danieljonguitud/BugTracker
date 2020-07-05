package com.effectivo.BugTracker.web.controller;

import com.effectivo.BugTracker.persistence.model.Project;
import com.effectivo.BugTracker.persistence.model.User;
import com.effectivo.BugTracker.persistence.model.dto.ProjectDto;
import com.effectivo.BugTracker.persistence.service.ProjectService;
import com.effectivo.BugTracker.persistence.service.UserService;
import com.effectivo.BugTracker.util.JwtUtil;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/projects")
@Api(tags="Projects Controller", description = "Provide CRUD options for Projects")
public class ProjectController {

    @Autowired
    private final ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ModelMapper modelMapper;

    private String token;
    private String username;

    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @GetMapping()
    public List<ProjectDto> getAllProjects(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        token = authorizationHeader.substring(7);
        username = jwtUtil.extractUsername(token);

        Long userId = userService.findByUsername(username).getId();
        List<Project> projects = projectService.findAllProjectsByUserId(userId);
        return projects.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // @GetMapping("/{id}")
    // public ProjectDto getProjectById(@PathVariable Long id){
        // Optional<Project> project = projectService.getProjectById(id);
        // Project value = project.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // return convertToDto(value);
    // }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createProject(@RequestBody ProjectDto newProjectDto, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        token = authorizationHeader.substring(7);
        username = jwtUtil.extractUsername(token);

        User user = userService.findByUsername(username);

        Project newProject = convertToEntity(newProjectDto);
        newProject.setUser(user);
        projectService.createProject(newProject);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProject(@PathVariable Long id, @RequestBody ProjectDto newProjectDto, HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");
        token = authorizationHeader.substring(7);
        username = jwtUtil.extractUsername(token);

        User user = userService.findByUsername(username);

        Project newProject = convertToEntity(newProjectDto);

        projectService.getProjectById(id).map(project -> {
            project.setName(newProject.getName());
            project.setDescription(newProject.getDescription());
            project.setUser(user);
            return projectService.updateProject(project);
        }).orElseGet(() -> {
            newProject.setId(id);
            newProject.setUser(user);
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
