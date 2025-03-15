package com.minhtn.bankservice.model.search;

import com.minhtn.bankservice.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParameterSearchAccount {
    private String accountId;
    private String currCode;
    private String authStat;
    private String authBy;
    private Date closeDateFrom;
    private Date closeDateTo;
    private String branchId;
    private Date authAtFrom;
    private Date authAtTo;
    private Date createFrom;
    private Date createTo;
    private String createBy;
    private Date updateAtFrom;
    private Date updateAtTo;
    private String updateBy;
    private String customerId;
    private String accountTypeId;
    private String accountStatusId;

    // page

    private Long startIndex;

    private Integer pageSize;

    private String sortField;

    private Boolean descSort = false;

    //build entity graph

    public boolean buildAcount = false;

    // getter, setter custom
    public void setSortField(String sortField) {
        if (sortField == null) return;
        boolean validField = false;
        for (Field field : Account.class.getDeclaredFields()) {
            if (field.getName().equals(sortField)) {
                validField = true;
                break;
            }
        }
        for (Field field : Account.class.getSuperclass().getDeclaredFields()) {
            if (field.getName().equals(sortField)) {
                validField = true;
                break;
            }
        }
        if (!validField) {
            throw new IllegalArgumentException("Invalid sort field");
        }
        this.sortField = sortField;
    }
}
