package com.vertigo.controllers;

import com.vertigo.exceptions.InvalidRequestException;
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

    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ErsReimbursement> getAllReimbursements() {
        return ersReimbursementService.getAllReimbursements();
    }


    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getReimbursementById(@PathVariable int id, Authentication authentication) {

        try {

            ErsReimbursement ersReimbursement;
            ersReimbursement = ersReimbursementService.getReimbursementById(id);
            ErsUser currentUser = ersUserService.findErsUserByUsername(authentication.getName());
            boolean isManager = false;
            for(GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if(grantedAuthority.getAuthority().toString().equals("ROLE_MANAGER")) isManager = true;
            }

            if(currentUser == null || ersReimbursement == null || ersReimbursement.getAuthor().getId() != currentUser.getId() && !isManager) {
                return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
            }

            return ResponseEntity.ok(ersReimbursementService.getReimbursementById(id));

        } catch(ResourceNotFoundException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);

        } catch(InvalidRequestException e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }

    }

    @GetMapping(value = "/authorid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping(value = "/status/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity getReimbursementByStatusId(@PathVariable int id) {

        try {
            return ResponseEntity.ok(ersReimbursementService.getReimbursementsByStatusId(id));
        } catch(ResourceNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "/type/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity getReimbursementByTypeId(@PathVariable int id) {

        try {
            return ResponseEntity.ok(ersReimbursementService.getReimbursementsByTypeId(id));
        } catch(ResourceNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createReimbursement(@RequestBody ErsReimbursement ersReimbursement, Authentication authentication) {
        try{
            ErsUser ersUser = ersUserService.findErsUserByUsername(authentication.getName());
            ersReimbursement.setAuthor(ersUser);
            return ResponseEntity.ok(ersReimbursementService.save(ersReimbursement));
        } catch(RuntimeException e) {
            return new ResponseEntity("There was a problem creating the reimbursement " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateReimbursement(@RequestBody ErsReimbursement changedErsReimbursement, Authentication authentication) {

        ErsReimbursement oldErsReimbursement = ersReimbursementService.getReimbursementById(changedErsReimbursement.getId());

        try{
            ErsUser ersUser = ersUserService.findErsUserByUsername(authentication.getName());

            boolean isManager = false;

            for(GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if(grantedAuthority.getAuthority().equals("ROLE_MANAGER")) isManager = true;
            }

            if(oldErsReimbursement.getStatus().getId() == 1 && isManager) {
                return ResponseEntity.ok(ersReimbursementService.save(changedErsReimbursement));
            }

            if(oldErsReimbursement.getAuthor().getId() != ersUser.getId() || oldErsReimbursement.getStatus().getId() != 1){
                return new ResponseEntity("You are not authorized to modify this resource", HttpStatus.FORBIDDEN);
            }

            if(changedErsReimbursement.getStatus().getId() != 1) {
                return new ResponseEntity("You are not authorized to approve or deny", HttpStatus.BAD_REQUEST);
            }

            return ResponseEntity.ok(ersReimbursementService.save(changedErsReimbursement));
        } catch(RuntimeException e) {
            return new ResponseEntity("There was a problem updating the reimbursement " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
