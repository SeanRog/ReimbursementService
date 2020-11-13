package com.vertigo.services;

import com.vertigo.exceptions.UsernameAlreadyExistsException;
import com.vertigo.models.ErsUser;
import com.vertigo.repositories.ErsUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ErsUserService implements UserDetailsService {


    private ErsUserRepository ersUserRepository;
    private JwtUserDetailsService jwtUserDetailsService;
    private PasswordEncoder bcryptEncoder;

    @Autowired
    public ErsUserService(ErsUserRepository ersUserRepository, JwtUserDetailsService jwtUserDetailsService, PasswordEncoder bcryptEncoder) {
        this.ersUserRepository = ersUserRepository;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ErsUser user = ersUserRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public ErsUser save(ErsUser user) throws UsernameAlreadyExistsException {

        ErsUser ersUser = ersUserRepository.findByUsername(user.getUsername());
        if(ersUser != null) {
            throw new UsernameAlreadyExistsException("Username is already taken");
        }

        ErsUser newUser = new ErsUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setUserRole(user.getUserRole());
        newUser.setActive(true);
        return ersUserRepository.save(newUser);
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

//    public ErsUser authenticate(String username, String password) {
//
//        ErsUser ersUser = ersUserRepository.findByUsername(username);
//
//        if(ersUser == null) {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//
//        if(!ersUser.getPassword().equals(password)) {
//            throw new RuntimeException("Password incorrect");
//        }
//
//        if(ersUser.getUserRole().equals("ADMIN")) {
//            jwtUserDetailsService.loadUserByUsername(ersUser.getUsername());
//        }
//
//        return ersUser;
//
//    }

}
