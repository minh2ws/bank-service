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
public class InternalTransactionDTO {
    @NotBlank(message = "Account Id is required")
    private String accountId;
    @NotBlank(message = "Destination Account is required")
    private String destinationAcct;
    @NotNull(message = "Tran Amount is required")
    private BigDecimal tranAmount;
    private BigDecimal feeAmount;
    private BigDecimal vatAmount;
    private String description;
    private String userCreate;
    @NotBlank(message = "CurrCode is required")
    private String currCode;
    private String provinceId;
    private String districtId;
    private String wardId;
}
