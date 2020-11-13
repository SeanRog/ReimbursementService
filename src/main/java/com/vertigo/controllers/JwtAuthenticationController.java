package com.vertigo.controllers;

import com.vertigo.exceptions.UsernameAlreadyExistsException;
import com.vertigo.models.ErsUser;
import com.vertigo.services.JwtUserDetailsService;
import com.vertigo.util.JwtTokenUtil;
import com.vertigo.web.dtos.ErsUserDTO;
import com.vertigo.web.dtos.JwtRequest;
import com.vertigo.web.dtos.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


/**
 * Expose a POST API /authenticate using the JwtAuthenticationController.
 * The POST API gets username and password in the body.
 * Using Spring Authentication Manager we authenticate the username and password.
 * If the credentials are valid, a JWT token is created using the JWTTokenUtil and provided to the client.
 */
@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationController(AuthenticationManager authenticationManager,
                                       JwtTokenUtil jwtTokenUtil,
                                       JwtUserDetailsService userDetailsService) {

        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;

    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody ErsUser user) throws Exception{
        try{
            return ResponseEntity.ok(userDetailsService.save(user));
        } catch(UsernameAlreadyExistsException e) {
            return new ResponseEntity<>("Username Already Taken!", HttpStatus.CONFLICT);
        }

    }

}
