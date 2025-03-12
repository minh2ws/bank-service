package com.minhtn.bankservice.model.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String idIssueDate;
    private String idIssuePlace;
    private String idExpireDate;
    private String recordStat;
    private String authStat;
    private String authBy;
    private String authAt;
    private String createAt;
    private String createBy;
    private String updateAt;
    private String updateBy;
    private String countryCode;
    private String provinceId;
    private String wardId;
    private String districtId;
    private String customerType;
}
