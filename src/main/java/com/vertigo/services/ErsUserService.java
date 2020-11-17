package com.vertigo.services;

import com.vertigo.exceptions.ResourceNotFoundException;
import com.vertigo.models.ErsUser;
import com.vertigo.repositories.ErsUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public ErsUser findErsUserByUsername(String username) {
        ErsUser ersUser;

        ersUser = ersUserRepository.findByUsername(username);

        if(ersUser == null) throw new ResourceNotFoundException("No user found by that username");

        return ersUser;

    }

    @Transactional
    public ErsUser updateUser(ErsUser updatedErsUser) {

        return ersUserRepository.save(updatedErsUser);

    }

    @Transactional
    public ErsUser deleteUser(String username) {

        ErsUser ersUser = ersUserRepository.findByUsername(username);
        ersUser.setActive(false);
        ersUserRepository.save(ersUser);
        return ersUser;

    }

}
