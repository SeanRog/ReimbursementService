package com.vertigo.services;

import com.vertigo.exceptions.UsernameAlreadyExistsException;
import com.vertigo.models.ErsUser;
import com.vertigo.repositories.ErsUserRepository;
import com.vertigo.web.dtos.ErsUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

        ErsUser ersUser = ersUserRepository.findByUsername(username);
        if(ersUser == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(ersUser.getUsername(), ersUser.getPassword(), getAuthority(ersUser));
    }


    private Set<SimpleGrantedAuthority> getAuthority(ErsUser ersUser) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        ersUser.getRoles().forEach(ersUserRole -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + ersUserRole.getName()));
        });
        return authorities;
    }

    public ErsUser save(ErsUser user) throws UsernameAlreadyExistsException{

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
        newUser.setRoles(user.getRoles());
        newUser.setActive(true);
        return ersUserRepository.save(newUser);
    }

}
