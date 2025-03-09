package com.minhtn.bankservice.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@MappedSuperclass // This annotation is used to indicate that the class is not an entity itself but a superclass of other entities
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseEntity implements Serializable {

    private String createBy;
    private Date createAt;
    private String updateBy;
    private Date updateAt;
}
