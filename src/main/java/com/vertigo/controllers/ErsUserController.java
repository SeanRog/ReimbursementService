package com.vertigo.controllers;

import com.vertigo.models.ErsUser;
import com.vertigo.services.ErsUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * This controller is used to:
 * add a new user - (Allowed role = ADMIN)
 * update an existing user - (Allowed role = ADMIN)
 * soft delete a target user - (Allowed role = ADMIN)
 *
 */

@RestController
@CrossOrigin
@RequestMapping({"/users"})
public class ErsUserController {

    private ErsUserService ersUserService;

    @Autowired
    public ErsUserController(ErsUserService ersUserService) {

        this.ersUserService = ersUserService;

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ErsUser> getAllUsers() {
        return ersUserService.getAllUsers();
    }


}
