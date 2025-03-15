package com.minhtn.bankservice.model.account;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusAccountDTO {
    @NotBlank(message = "Account Id is required")
    private String accountId;
    @NotBlank(message = "Account Status Id is required")
    private String accountStatusId;
}
