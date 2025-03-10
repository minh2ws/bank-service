package com.minhtn.bankservice.model.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegUserDTO {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "password is required")
    @Length(min = 6, message = "password must be at least 6 characters")
    private String password;
}
