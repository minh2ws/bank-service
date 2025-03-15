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
import java.util.Set;

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
    @Access(AccessType.PROPERTY)
    private String accountId;

    @Column(name = "curr_code")
    private String currCode;

    @Column(name = "auth_stat")
    private String authStat;

    @Column(name = "auth_by")
    private String authBy;

    @Column(name = "auth_at")
    private Date authAt;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actype_id")
    private AccountType accountType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acctstatus_id")
    private AccountStatus accountStatus;

    @ManyToMany
    @JoinTable(
            name = "account_status_history",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "account_status_id")
    )
    private Set<AccountStatus> accountStatuses;
}
