package com.minhtn.bankservice.model.account;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccountDTO {
    @NotBlank(message = "Account Id is required")
    private String accountId;
    private BigDecimal maxLimit;
    private String notes;
}
