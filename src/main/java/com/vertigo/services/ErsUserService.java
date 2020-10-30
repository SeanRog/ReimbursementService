package com.vertigo.services;

import com.vertigo.models.ErsUser;
import com.vertigo.repositories.ErsUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ErsUserService {


    private ErsUserRepository ersUserRepository;

    @Autowired
    public ErsUserService(ErsUserRepository ersUserRepository) {
        this.ersUserRepository = ersUserRepository;
    }

    @Transactional
    public List<ErsUser> getAllUsers() {
        try {

            Iterable<ErsUser> ersUserIterable = ersUserRepository.findAll();
            List<ErsUser> ersUserList = new ArrayList<>();
            ersUserIterable.forEach(ersUserList::add);
            return ersUserList;

        } catch (Exception e) {

            throw new RuntimeException("Resource Not Found");

        }

    }

}
