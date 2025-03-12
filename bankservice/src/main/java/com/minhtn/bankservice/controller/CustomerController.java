package com.minhtn.bankservice.controller;

import com.minhtn.bankservice.model.customer.CheckedCustomerDTO;
import com.minhtn.bankservice.model.customer.CreateCustomerDTO;
import com.minhtn.bankservice.model.customer.UpdateCustomerDTO;
import com.minhtn.bankservice.model.user.RegUserDTO;
import com.minhtn.bankservice.model.user.UserDTO;
import com.minhtn.bankservice.model.wrapper.ObjectResponseWrapper;
import com.minhtn.bankservice.service.CustomerService;
import com.minhtn.bankservice.ultility.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.CUSTOMER_SERVICE_URL)
@Tag(name = "Customer API")
public class CustomerController extends BaseController {

    @Operation(summary = "Lấy thông tin khách hàng", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = ObjectResponseWrapper.class)))
    })
    @GetMapping("/{id}")
    public ObjectResponseWrapper getCustomer() {
        return null;
    }

    @Operation(summary = "Thêm mới khách hàng", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @PostMapping("/register")
    public ObjectResponseWrapper register(@RequestBody @Valid CreateCustomerDTO createCustomerDTO) {
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(customerService.createCustomer(createCustomerDTO))
                .build();
    }

    @Operation(summary = "Cập nhật thông tin khách hàng", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @PutMapping("/update")
    public ObjectResponseWrapper update(@RequestBody UpdateCustomerDTO updateCustomerDTO) {
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(customerService.updateCustomer(updateCustomerDTO))
                .build();
    }

    @Operation(summary = "Duyệt thông tin khách hàng", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @PutMapping("/check")
    public ObjectResponseWrapper checkCust(@RequestBody @Valid CheckedCustomerDTO checkedCustomerDTO) {
        Boolean status = customerService.checkCustomer(checkedCustomerDTO);
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(status)
                .build();
    }

    @Operation(summary = "Đóng khách hàng", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @DeleteMapping("/delete")
    public ObjectResponseWrapper delete(@RequestBody String customerId) {
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(customerService.deleteCustomer(customerId))
                .build();
    }
}
