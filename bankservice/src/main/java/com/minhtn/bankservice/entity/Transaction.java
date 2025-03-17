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
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Transaction implements Serializable {
    @Id
    @Column(name = "tran_id")
    @Access(AccessType.PROPERTY)
    private String tranId;

    @Column(name = "trace_no")
    private String traceNo;

    @Column(name = "tran_time")
    private Date tranTime;

    @Column(name = "ref_no")
    private String refNo;

    @Column(name = "DB_CR")
    private String dbcr;

    @Column(name = "tran_amount")
    private BigDecimal tranAmount;

    @Column(name = "fee_amount")
    private BigDecimal feeAmount;

    @Column(name = "vat_amount")
    private BigDecimal vatAmout;

    private String description;

    @Column(name = "currcode")
    private String currCode;

    @Column(name = "destination_acct")
    private String destinationAcct;

    @Column(name = "destination_name")
    private String destinationName;

    @Column(name = "user_create")
    private String userCreate;

    @Column(name = "user_approve")
    private String userApprove;

    @Column(name = "approve_time")
    private Date approveTime;

    private Boolean status;

    @Column(name = "merchant_id")
    private String merchantId;

    @Column(name = "terminal_id")
    private String terminalId;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "trancode_id")
    private Trancode trancode;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "province_id")
    private String provinceId;

    @Column(name = "district_id")
    private String districtId;

    @Column(name = "ward_id")
    private String wardId;

    @Column(name = "acquire_bank")
    private String acquireBank;
}
