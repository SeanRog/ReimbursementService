package com.vertigo.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ers_reimbursements")
public class ErsReimbursement {

    @Id
    @Column(name = "reimb_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "submitted", nullable = false)
    private String submitted;

    @Column(name = "resolved")
    private String resolved;

    @Column(name = "description")
    private String description;

    @Column(name = "receipt")
    private String receipt;

    @OneToOne
    @JoinColumn(name = "author_id", nullable = false)
    private ErsUser author;

    @OneToOne
    @JoinColumn(name = "resolver_id")
    private ErsUser resolver;

    @OneToOne
    @JoinColumn(name = "reimb_status_id", nullable = false)
    private ErsReimbursementStatus status;

    @OneToOne
    @JoinColumn(name = "reimb_type_id", nullable = false)
    private ErsReimbursementType type;

}
