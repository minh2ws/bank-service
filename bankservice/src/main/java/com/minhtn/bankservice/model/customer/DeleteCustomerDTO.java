package com.minhtn.bankservice.model.customer;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCustomerDTO{

    @NotBlank(message = "Customer Id is required")
    private String customerId;
    @NotBlank(message = "Update By is required")
    private String updateBy;
}
