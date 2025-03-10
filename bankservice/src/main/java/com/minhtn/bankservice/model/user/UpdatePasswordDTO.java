package com.minhtn.bankservice.model.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordDTO {
    private String oldPassword;
    @NotBlank(message = "New password is required")
    private String newPassword;
}
