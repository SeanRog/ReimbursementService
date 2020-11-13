package com.vertigo.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ers_user_roles")
public class ErsUserRole {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int id;

    @Column(name = "role_name", unique = true)
    public String roleName;

}
