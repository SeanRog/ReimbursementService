package com.vertigo.controllers;

import com.vertigo.models.ErsReimbursement;
import com.vertigo.services.ErsReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/reimbursements")
public class ErsReimbursementController {

    ErsReimbursementService ersReimbursementService;

    @Autowired
    public ErsReimbursementController(ErsReimbursementService ersReimbursementService) {
        this.ersReimbursementService = ersReimbursementService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ErsReimbursement> getAllReimbursements() {
        return ersReimbursementService.getAllReimbursements();
    }

}
