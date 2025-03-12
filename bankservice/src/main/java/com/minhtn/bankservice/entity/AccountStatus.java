package com.minhtn.bankservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "account_status")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class AccountStatus implements Serializable {
    @Id
    @Column(name = "account_status_id")
    private String accountStatusId;

    private String name;

    @Column(name = "eng_name")
    private String engName;

    private String description;

    @Column(name = "response_code")
    private String responseCode;

    @Column(name = "user_action")
    private String userAction;
}
