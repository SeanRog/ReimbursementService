package com.vertigo.controllers;


import com.vertigo.models.ErsUser;
import com.vertigo.services.ErsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/users"})
public class ErsUserController {

    private ErsUserService ersUserService;

    @Autowired
    public ErsUserController(ErsUserService ersUserService) {
        this.ersUserService = ersUserService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ErsUser> getAllUsers() {
        return ersUserService.getAllUsers();
    }

}
