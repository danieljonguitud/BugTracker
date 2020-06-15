package com.effectivo.BugTracker.web.controller;

import com.effectivo.BugTracker.persistence.model.auth.AuthRequest;
import com.effectivo.BugTracker.persistence.model.auth.AuthResponse;
import com.effectivo.BugTracker.util.JwtUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@Api(tags="Auth Controller", description = "Provide Auth URL")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (Exception e){
            throw new Exception("Invalid username/password");
        }
        final String jwt = jwtUtil.generateToken(authRequest.getUsername());

        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}
