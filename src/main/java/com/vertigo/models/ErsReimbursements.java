package com.vertigo.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ers_reimbursements", schema = "project1")
public class ErsReimbursements {

    @Id
    @Column(name = "reimb_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "submitted")
    private String submitted;

    @Column(name = "resolved")
    private String resolved;

    @Column(name = "description")
    private String description;

    @Column(name = "reciept")
    private double receipt;

    @OneToOne
    @JoinColumn(name = "author_id")
    private ErsUser author;

    @OneToOne
    @JoinColumn(name = "resolver_id")
    private ErsUser resolver;

    @OneToOne
    @JoinColumn(name = "reimb_status_id")
    private ErsReimbursementStatus status;

    @OneToOne
    @JoinColumn(name = "reimb_type_id")
    private ErsReimbursementType type;

}
