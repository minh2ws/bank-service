package com.minhtn.bankservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Entity
@Table(name = "province")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Province implements Serializable {
    @Id
    @Column(name = "province_id")
    private String provinceId;

    private String name;

    @Column(name = "eng_name")
    private String engName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "eng_full_name")
    private String engFullName;

    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_code")
    private Country country;
}
