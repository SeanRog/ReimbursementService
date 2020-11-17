package com.vertigo.controllers;

import com.vertigo.exceptions.ResourceNotFoundException;
import com.vertigo.models.ErsReimbursement;
import com.vertigo.models.ErsUser;
import com.vertigo.services.ErsReimbursementService;

import com.vertigo.services.ErsUserService;
import com.vertigo.services.JwtUserDetailsService;
import com.vertigo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This controller is used to:
 * get all reimbursements - (Allowed role = MANAGER),
 * get reimbursements by type - (Allowed role = MANAGER),
 * get reimbursements by status - (Allowed role = MANAGER),
 * approve/deny reimbursement - (Allowed role = MANAGER),
 * get a reimbursement by id - (Allowed role = MANAGER),
 * get a reimbursement by username by id - (Allowed roles = MANAGER, EMPLOYEE),
 * create a new reimbursement - (Allowed role = EMPLOYEE),
 * update a reimbursement - (Allowed role = EMPLOYEE)
 */

@RestController
@CrossOrigin
@RequestMapping("/reimbursements")
public class ErsReimbursementController {

    ErsReimbursementService ersReimbursementService;
    ErsUserService ersUserService;

    @Autowired
    public ErsReimbursementController(ErsReimbursementService ersReimbursementService, ErsUserService ersUserService) {
        this.ersReimbursementService = ersReimbursementService;
        this.ersUserService = ersUserService;
    }

//    @PreAuthorize("hasRole('MANAGER')")
    @Secured(value = {"ROLE_MANAGER"})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ErsReimbursement> getAllReimbursements() {
        return ersReimbursementService.getAllReimbursements();
    }

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReimbursementById(@PathVariable int id) {

        try {
            return ResponseEntity.ok(ersReimbursementService.getReimbursementById(id));
        } catch(ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/authorid/{id}", method = RequestMethod.GET)
    public ResponseEntity getReimbursementByAuthorId(@PathVariable int id, Authentication authentication) {

        ErsUser ersUser = ersUserService.findErsUserByUsername(authentication.getName());

        boolean isManager = false;

        for(GrantedAuthority auth : authentication.getAuthorities()) {
            if(auth.getAuthority().toString().equals("ROLE_MANAGER")) isManager = true;
        }

        if(ersUser == null || ersUser.getId() != id && !isManager) {
            return new ResponseEntity("You are not authorized", HttpStatus.FORBIDDEN);
        }

        try{

            return ResponseEntity.ok(ersReimbursementService.findAllByAuthorId(id));

        } catch(ResourceNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

}
