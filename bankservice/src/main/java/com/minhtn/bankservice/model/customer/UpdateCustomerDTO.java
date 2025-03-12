package com.minhtn.bankservice.model.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerDTO {
    private String customerId;
    private String fullName;
    private String engName;
    private String email;
    private String phone;
    private String address;
    private String idType;
    private String idNumber;
    private Date idIssueDate;
    private String idIssuePlace;
    private Date idExpireDate;
    private String updateBy;
    private String countryCode;
    private String provinceId;
    private String districtId;
    private String wardId;
    private String custypeId;
}
