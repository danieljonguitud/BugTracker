package com.effectivo.BugTracker.web.controller;

import com.effectivo.BugTracker.persistence.model.Bug;
import com.effectivo.BugTracker.persistence.model.BugCount;
import com.effectivo.BugTracker.persistence.model.Project;
import com.effectivo.BugTracker.persistence.model.User;
import com.effectivo.BugTracker.persistence.model.dto.BugDto;
import com.effectivo.BugTracker.persistence.service.BugService;
import com.effectivo.BugTracker.persistence.service.ProjectService;
import com.effectivo.BugTracker.persistence.service.UserService;
import com.effectivo.BugTracker.util.JwtUtil;
import com.fasterxml.jackson.databind.JsonSerializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/projects")
@Api(tags="Bugs Controller", description = "Provide CRUD options for Bugs")
public class BugController {

    @Autowired
    private final BugService bugService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ModelMapper modelMapper;

    public BugController(BugService bugService){
        this.bugService = bugService;
    }

    @ApiOperation(value = "View an specific Bug from a Project", response = BugDto.class)
    @GetMapping("/{projectId}/bugs/{bugId}")
    public BugDto getBugById(@PathVariable (value = "projectId") Long projectId,
                          @PathVariable (value = "bugId") Long bugId) {

        if (!projectService.existById(projectId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ProjectId " + projectId + " not found");
        }
        Optional<Bug> bug = bugService.getBugById(bugId);
        Bug value = bug.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return convertToDto(value);
    }

    @GetMapping("/{projectId}/bugs")
    public List<BugDto> getAllBugsByProjectId(@PathVariable Long projectId){
        List<Bug> bugs = bugService.findBugsByProjectId(projectId);
        return bugs.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PostMapping("/{projectId}/bugs")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBug(@PathVariable (value = "projectId") Long projectId, @RequestBody BugDto newBugDto){
        Bug newBug = convertToEntity(newBugDto);
        projectService.getProjectById(projectId).map(project -> {
            newBug.setProject(project);
            newBug.setStatus(1);
            bugService.createBug(newBug);
            return project;
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "ProjectId " + projectId + " not found"));
    }

    @PutMapping("/{projectId}/bugs/{bugId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBug(@PathVariable (value = "projectId") Long projectId,
                              @PathVariable (value = "bugId") Long bugId,
                              @RequestBody BugDto updateBugDto){
        if(!projectService.existById(projectId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ProjectId " + projectId + " not found");
        }
        Bug updateBug = convertToEntity(updateBugDto);
        bugService.getBugById(bugId).map(bug -> {
            bug.setTitle(updateBug.getTitle());
            bug.setStatus(updateBug.getStatus());
            bug.setDescription(updateBug.getDescription());
            return bugService.updateBug(bug);
        }).orElseGet(() -> {
            updateBug.setId(bugId);
            return bugService.updateBug(updateBug);
        });
    }


    @DeleteMapping("/{projectId}/bugs/{bugId}")
    public void deleteBug(@PathVariable (value = "projectId") Long projectId,
                          @PathVariable (value = "bugId") Long bugId) {
        if (!projectService.existById(projectId) || !bugService.existById(bugId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ProjectId " + projectId + " or BugId " + bugId + " not found");
        }
        bugService.deleteBug(bugId);


    }

    @GetMapping("/bugs")
    public BugCount countingBugs(HttpServletRequest request){
        String authorizationHeader = request.getHeader("Authorization");

        String token;
        String username;

        token = authorizationHeader.substring(7);
        username = jwtUtil.extractUsername(token);

        Long userId = userService.findByUsername(username).get().getId();
        List<Project> projects = projectService.findAllProjectsByUserId(userId);

        List<Integer> results = new ArrayList<>();

        for (Project project:
        projects){
            results.add(bugService.countBugs(project.getId()));
        }

        Integer count = 0;

        for (Integer i : results){
            count += i;
        }

        return new BugCount(count);

    }

    //Converting to DTO - ENTITY

    private BugDto convertToDto(Bug bug) {
        return modelMapper.map(bug, BugDto.class);
    }

    private Bug convertToEntity(BugDto bugDto) {
        Bug bug = modelMapper.map(bugDto, Bug.class);

        if (bugDto.getId() != null) {
            Optional<Bug> oldBug = bugService.getBugById(bugDto.getId());
        }
        return bug;
    }
}
