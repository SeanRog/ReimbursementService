package com.vertigo.services;

import com.vertigo.models.ErsUser;
import com.vertigo.repositories.ErsUserRepository;
import com.vertigo.web.dtos.ErsUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * JWTUserDetailsService implements the Spring Security UserDetailsService interface.
 *
 * It overrides the loadUserByUsername for fetching user details from the database using the username.
 *
 * The Spring Security Authentication Manager calls this method for getting the user details from the database when
 * authenticating the user details provided by the user.
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private ErsUserRepository ersUserRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println(username);

        return new User(username, "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                new ArrayList<>());

//        return new User(username, "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
//                    new ArrayList<>());

//        if ("javainuse".equals(username)) {
//            return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
//                    new ArrayList<>());
//        } else {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
    }

    public ErsUser save(ErsUserDTO user) {
        ErsUser newUser = new ErsUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return ersUserRepository.save(newUser);
    }

}
