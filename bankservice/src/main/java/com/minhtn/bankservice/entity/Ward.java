package com.minhtn.bankservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Table(name = "ward")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Ward implements Serializable {
    @Id
    @Column(name = "ward_id")
    @Access(AccessType.PROPERTY)
    private String wardId;

    private String name;

    @Column(name = "eng_name")
    private String engName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "eng_full_name")
    private String engFullName;

    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;
}
