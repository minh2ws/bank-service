package com.minhtn.bankservice.model.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebitTransactionDTO {
    @NotNull(message = "Tran Amount is required")
    private BigDecimal tranAmount;
    private BigDecimal feeAmount;
    private BigDecimal vatAmount;
    private String description;
    @NotBlank(message = "CurrCode is required")
    private String currCode;
    @NotBlank(message = "Account Id is required")
    private String accountId;
    @NotBlank(message = "Destination Account is required")
    private String destinationAcct;
    @NotBlank(message = "Destination Name is required")
    private String destinationName;
    private String userCreate;
    private Boolean status;
    private String merchantId;
    private String terminalId;
    @NotBlank(message = "Branch Id is required")
    private String branchId;
    @NotBlank(message = "Trancode is required")
    private String trancode;
    private String provinceId;
    private String districtId;
    private String wardId;
    private String accquireBank;
}
