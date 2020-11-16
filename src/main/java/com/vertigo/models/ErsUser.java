package com.vertigo.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "ers_users")
public class ErsUser {

    @Id
    @Column(name = "ers_user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(
            name = "user_id", referencedColumnName = "ers_user_id"),
        inverseJoinColumns = @JoinColumn(
            name = "role_id", referencedColumnName = "role_id"))
    private Collection<ErsUserRole> roles;

    @Column(name = "is_active")
    private boolean isActive;


}
