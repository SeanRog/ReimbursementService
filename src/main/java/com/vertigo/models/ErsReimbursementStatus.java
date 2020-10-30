package com.vertigo.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ers_reimbursement_statuses")
public class ErsReimbursementStatus {

    @Id
    @Column(name = "reimb_status_id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;

    @Column(name = "reimb_status", nullable = false, unique = true)
    private String reimbStatus;

}
