package com.minhtn.bankservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Table(name = "trancode")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Trancode extends BaseEntity implements Serializable {
    @Id
    @Column(name = "trancode_id")
    @Access(AccessType.PROPERTY)
    private String trancodeId;

    private String name;

    @Column(name = "eng_name")
    private String engName;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "DB_CR")
    private String dbcr;

    private String description;
}
