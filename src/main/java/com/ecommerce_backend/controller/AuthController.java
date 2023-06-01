package com.ecommerce_backend.controller;

import com.ecommerce_backend.exception.ResourceNotFoundException;
import com.ecommerce_backend.model.User;
import com.ecommerce_backend.payload.JwtRequest;
import com.ecommerce_backend.payload.JwtResponse;
import com.ecommerce_backend.payload.UserDto;
import com.ecommerce_backend.security.JwtHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {

        authenticateUser(jwtRequest.getUsername(), jwtRequest.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtHelper.generateToken(userDetails);

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken(token);
        jwtResponse.setUser(modelMapper.map(userDetails, UserDto.class));

        return new ResponseEntity<>(jwtResponse, HttpStatus.ACCEPTED);
    }

    private void authenticateUser(String username, String password) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {

            throw new ResourceNotFoundException("Invalid Username or password");
        } catch (DisabledException e) {


            throw new ResourceNotFoundException("User is not active!");
        }
    }
}