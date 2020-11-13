package com.vertigo.controllers;


import com.vertigo.exceptions.UsernameAlreadyExistsException;
import com.vertigo.models.ErsUser;
import com.vertigo.services.ErsUserService;
import com.vertigo.services.JwtUserDetailsService;
import com.vertigo.util.JwtTokenUtil;
import com.vertigo.web.dtos.JwtRequest;
import com.vertigo.web.dtos.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping({"/users"})
public class ErsUserController {

    private ErsUserService ersUserService;
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService userDetailsService;

    public ErsUserController(ErsUserService ersUserService,
                             AuthenticationManager authenticationManager,
                             JwtTokenUtil jwtTokenUtil,
                             JwtUserDetailsService userDetailsService) {

        this.ersUserService = ersUserService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ErsUser> getAllUsers() {
        return ersUserService.getAllUsers();
    }


}
