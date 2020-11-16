package com.vertigo.services;

import com.vertigo.models.ErsReimbursement;
import com.vertigo.repositories.ErsReimbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

}
