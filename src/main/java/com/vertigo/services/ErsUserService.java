package com.vertigo.services;

import com.vertigo.models.ErsUser;
import com.vertigo.repositories.ErsUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ErsUserService {


    private ErsUserRepository ersUserRepository;
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public ErsUserService(ErsUserRepository ersUserRepository, JwtUserDetailsService jwtUserDetailsService) {
        this.ersUserRepository = ersUserRepository;
        this.jwtUserDetailsService = jwtUserDetailsService;
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

    public ErsUser authenticate(String username, String password) {

        ErsUser ersUser = ersUserRepository.findByUsername(username);

        if(ersUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        if(!ersUser.getPassword().equals(password)) {
            throw new RuntimeException("Password incorrect");
        }

        if(ersUser.getUserRole().equals("ADMIN")) {
            jwtUserDetailsService.loadUserByUsername(ersUser.getUsername());
        }

        return ersUser;

    }

}
