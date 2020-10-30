package com.vertigo.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ers_users", schema = "project1")
public class ErsUser {

    @Id
    @Column(name = "ers_user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @OneToOne
    @JoinColumn(name = "role_id")
    private ErsUserRole userRole;

    @Column(name = "is_active")
    private boolean isActive;


}
