package com.minhtn.bankservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Table(name = "customer_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class CustomerType extends BaseEntity implements Serializable {
    @Id
    @Column(name = "customer_type_id")
    private String customerTypeId;

    private String name;

    private String description;

    @Column(name = "max_amount_limit")
    private String maxAmountLimit;
}
