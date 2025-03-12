package com.minhtn.bankservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Account extends BaseEntity implements Serializable {
    @Id
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "open_date")
    private Date openDate;

    @Column(name = "curr_code")
    private String currCode;

    @Column(name = "record_stat")
    private String recordStat;

    @Column(name = "auth_stat")
    private String authStat;

    @Column(name = "auth_by")
    private String authBy;

    @Column(name = "auth_at")
    private String authAt;

    @Column(name = "close_date")
    private Date closeDate;

    @Column(name = "avl_bal")
    private BigDecimal avlBal;

    @Column(name = "min_bal")
    private BigDecimal minBal;

    @Column(name = "max_limit")
    private BigDecimal maxLimit;

    @Column(name = "avl_limit")
    private BigDecimal avlLimit;

    private String notes;

    @Column(name = "branch_id")
    private String branch_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actype_id")
    private AccountType accountType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acctstatus_id")
    private AccountStatus accountStatus;
}
