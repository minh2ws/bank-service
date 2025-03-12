package com.minhtn.bankservice.model.customer;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerDTO {

    @NotBlank(message = "Branch Id is required")
    @Length(min = 3, max = 3, message = "Branch Id must be 3 characters")
    private String branchId;
    @NotBlank(message = "Full Name is required")
    private String fullName;
    private String engName;
    private String email;
    private String phone;
    private String address;
    @NotBlank(message = "Id Type is required")
    private String idType;
    @NotBlank(message = "Id Number is required")
    private String idNumber;
    @NotNull(message = "Id Issue Date is required")
    private Date idIssueDate;
    @NotBlank(message = "Id Issue Place is required")
    private String idIssuePlace;
    @NotNull(message = "Id Expire Date is required")
    private Date idExpireDate;
    private String createBy;
    @NotBlank(message = "Country code is required")
    private String countryCode;
    private String provinceId;
    private String districtId;
    private String wardId;
    @NotBlank(message = "Custtype Id is required")
    private String custypeId;
}
