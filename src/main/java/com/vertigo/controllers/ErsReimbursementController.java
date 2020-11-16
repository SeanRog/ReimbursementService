package com.vertigo.controllers;

import com.vertigo.models.ErsReimbursement;
import com.vertigo.services.ErsReimbursementService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * This controller is used to:
 * get all reimbursements - (Allowed role = MANAGER),
 * get reimbursements by type - (Allowed role = MANAGER),
 * get reimbursements by status - (Allowed role = MANAGER),
 * get a reimbursement by id - (Allowed role = MANAGER),
 * approve/deny reimbursement - (Allowed role = MANAGER),
 * create a new reimbursement - (Allowed role = USER),
 * update a reimbursement - (Allowed role = USER
 */

@RestController
@CrossOrigin
@RequestMapping("/reimbursements")
public class ErsReimbursementController {

    ErsReimbursementService ersReimbursementService;

    @Autowired
    public ErsReimbursementController(ErsReimbursementService ersReimbursementService) {
        this.ersReimbursementService = ersReimbursementService;
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ErsReimbursement> getAllReimbursements() {
        return ersReimbursementService.getAllReimbursements();
    }

}
