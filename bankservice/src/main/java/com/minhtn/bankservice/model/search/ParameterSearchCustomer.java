package com.minhtn.bankservice.model.search;

import com.minhtn.bankservice.entity.Customer;
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
public class ParameterSearchCustomer {
    private String customerId;
    private String fullName;
    private String engName;
    private String email;
    private String phone;
    private String address;
    private String idType;
    private String idNumber;
    private Date idIssueDateFrom;
    private Date idIssueDateTo;
    private String idIssuePlace;
    private Date idExpireDateFrom;
    private Date idExpireDateTo;
    private String recordStat;
    private String authStat;
    private String authBy;
    private Date authAtFrom;
    private Date authAtTo;
    private Date createFrom;
    private Date createTo;
    private String createBy;
    private Date updateAtFrom;
    private Date updateAtTo;
    private String updateBy;
    private String countryCode;
    private String provinceId;
    private String wardId;
    private String districtId;
    private String customerTypeId;

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
        for (Field field : Customer.class.getDeclaredFields()) {
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
