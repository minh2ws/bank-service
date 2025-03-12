package com.minhtn.bankservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Table(name = "country")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Country implements Serializable {
    @Id
    @Column(name = "country_code")
    @Access(AccessType.PROPERTY)
    private String countryCode;

    private String name;

    private Boolean status;

    @Column(name = "country_code_3_digit", unique = true)
    private String countryCode3Digit;

    @Column(name = "numeric_code", unique = true)
    private String numericCode;

}
