package com.vertigo.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ers_reimbursement_types", schema = "project1")
public class ErsReimbursementType {

    @Id
    @Column(name = "reimb_type_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "reimb_type")
    private String reimbursementType;

}
