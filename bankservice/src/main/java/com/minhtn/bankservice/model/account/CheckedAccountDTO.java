package com.minhtn.bankservice.model.account;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckedAccountDTO {
    @NotBlank(message = "Account Id is required")
    private String accountId;
    private String authBy;
}
