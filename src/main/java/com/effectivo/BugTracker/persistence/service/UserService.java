package com.effectivo.BugTracker.persistence.service;

import com.effectivo.BugTracker.persistence.model.User;
import com.effectivo.BugTracker.persistence.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerNewUserAccount(User user){

        User newUser = new User();

        newUser.setUsername(user.getUsername());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());

        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(newUser);

    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
