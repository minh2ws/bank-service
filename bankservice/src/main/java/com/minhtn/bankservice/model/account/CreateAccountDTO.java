package com.minhtn.bankservice.model.account;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountDTO {
    @NotBlank(message = "Customer Id is required")
    private String customerId;
    private String currCode;
    private BigDecimal maxLimit;
    private String notes;
    private String createBy;
    @NotBlank(message = "Branch Id is required")
    @Length(min = 3, max = 3, message = "Branch Id must be 3 characters")
    private String branchId;
    @NotBlank(message = "Account Type Id is required")
    private String accountTypeId;
    @NotBlank(message = "Account Status Id is required")
    private String accountStatusId;
}
