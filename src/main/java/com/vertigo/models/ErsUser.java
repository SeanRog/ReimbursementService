package com.vertigo.models;

import lombok.Data;

import javax.persistence.*;

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

    @OneToOne
    @JoinColumn(name = "role_id", nullable = false)
    private ErsUserRole userRole;

    @Column(name = "is_active")
    private boolean isActive;


}
