package com.minhtn.bankservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Table(name = "account_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class AccountType extends BaseEntity implements Serializable {
    @Id
    @Column(name = "account_type_id")
    @Access(AccessType.PROPERTY)
    private String accountTypeId;

    private String name;

    private Boolean status;

    private String description;
}
