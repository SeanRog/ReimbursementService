package com.vertigo.services;

import com.vertigo.models.ErsReimbursement;
import com.vertigo.repositories.ErsReimbursementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ErsReimbursementService {

    ErsReimbursementRepository ersReimbursementRepository;

    @Autowired
    public ErsReimbursementService(ErsReimbursementRepository ersReimbursementRepository) {
        this.ersReimbursementRepository = ersReimbursementRepository;
    }

    public List<ErsReimbursement> getAllReimbursements() {
        return null;
    }

}
