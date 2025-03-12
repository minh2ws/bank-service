package com.minhtn.bankservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Table(name = "branch")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Branch extends BaseEntity implements Serializable {

    @Id
    @Column(name = "branch_id")
    @Access(AccessType.PROPERTY)
    private String branchId;

    private String name;

    private String address;

    @Column(name = "merchant_id")
    private String merchantId;
}
