package com.minhtn.bankservice.model.customer;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerDTO {
    @NotBlank(message = "Customer Id is required")
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
    private String countryCode;
    private String provinceId;
    private String districtId;
    private String wardId;
    private String custypeId;
}
