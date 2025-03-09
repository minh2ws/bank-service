package com.minhtn.bankservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "customer_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Customer extends BaseEntity implements Serializable {
    @Id
    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "eng_name")
    private String engName;

    private String email;

    private String phone;

    private String address;

    @Column(name = "id_type")
    private String idType;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "id_issue_date")
    private String idIssueDate;

    @Column(name = "id_issue_place")
    private String idIssuePlace;

    @Column(name = "record_stat")
    private String recordStat;

    @Column(name = "auth_stat")
    private String authStat;

    @Column(name = "auth_by")
    private String authBy;

    @Column(name = "auth_at")
    private Date authAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_code")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    private Province province;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ward_id")
    private Ward ward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_type_id")
    private CustomerType customerType;
}
