package com.effectivo.BugTracker.web.controller;

import com.effectivo.BugTracker.persistence.model.User;
import com.effectivo.BugTracker.persistence.model.dto.UserDto;
import com.effectivo.BugTracker.persistence.service.UserService;
import io.swagger.annotations.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
@Api(tags="Users Controller", description = "Provide User registration URL")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/registration")
    public void userRegistration(@RequestBody UserDto userDto){
        User newUser = convertToEntity(userDto);
        userService.registerNewUserAccount(newUser);
    }

    // DTOs

    private UserDto convertToDto(User user){
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToEntity(UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }
}
