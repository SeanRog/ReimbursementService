package com.vertigo.services;

import com.vertigo.exceptions.InvalidRequestException;
import com.vertigo.exceptions.ResourceNotFoundException;
import com.vertigo.models.ErsReimbursement;
import com.vertigo.repositories.ErsReimbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ErsReimbursementService {

    ErsReimbursementRepository ersReimbursementRepository;

    @Autowired
    public ErsReimbursementService(ErsReimbursementRepository ersReimbursementRepository) {
        this.ersReimbursementRepository = ersReimbursementRepository;
    }

    public List<ErsReimbursement> getAllReimbursements() {

        try {

            Iterable<ErsReimbursement> ersReimbursementIterable = ersReimbursementRepository.findAll();
            List<ErsReimbursement> ersReimbursements = new ArrayList<>();
            for(ErsReimbursement ersReimbursement : ersReimbursementIterable) {
                ersReimbursements.add(ersReimbursement);
            }
            return ersReimbursements;

        } catch (Exception e) {

            throw new RuntimeException("No reimbursements found");

        }

    }

    public ErsReimbursement getReimbursementById(int id){

        if (id <= 0) {
            throw new InvalidRequestException("ID must be greater than 0");
        }

        Optional<ErsReimbursement> ersReimbursement = ersReimbursementRepository.findById(id);

        if(!ersReimbursement.isPresent()) {
            throw new ResourceNotFoundException();
        }

        return ersReimbursement.get();

    }

    public List<ErsReimbursement> findAllByAuthorId(int id) {
        if (id <= 0) {
            throw new InvalidRequestException("ID must be greater than 0");
        }



        List<ErsReimbursement> ersReimbursements = ersReimbursementRepository.findAllByAuthorId(id);

        if(ersReimbursements.size() == 0) {
            throw new ResourceNotFoundException();
        }

        return ersReimbursements;

    }

}
