package com.minhtn.bankservice.controller;

import com.minhtn.bankservice.handler.ServiceException;
import com.minhtn.bankservice.model.customer.*;
import com.minhtn.bankservice.model.search.ParameterSearchCustomer;
import com.minhtn.bankservice.model.user.UserDTO;
import com.minhtn.bankservice.model.wrapper.ListWrapper;
import com.minhtn.bankservice.model.wrapper.ObjectResponseWrapper;
import com.minhtn.bankservice.ultility.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constant.ACCOUNT_SERVICE_URL)
@Tag(name = "Account API")
@Slf4j
public class AccountController extends BaseController {

    @Operation(summary = "Lấy thông tin tài khoản", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = ObjectResponseWrapper.class)))
    })
    @GetMapping("/{id}")
    public ObjectResponseWrapper getAccount(@PathVariable String id) {
//        ParameterSearchCustomer parameterSearchCustomer = new ParameterSearchCustomer();
//        parameterSearchCustomer.setCustomerId(id);
//        parameterSearchCustomer.setStartIndex(0L);
//        parameterSearchCustomer.setPageSize(1);
//        ListWrapper<CustomerDTO> customers = customerService.search(parameterSearchCustomer);
//        if (customers.getData().isEmpty()) {
//            throw new ServiceException("Customer not found");
//        }
//        return ObjectResponseWrapper.builder()
//                .status(HttpStatus.OK.value())
//                .message("Success")
//                .data(customerService.search(parameterSearchCustomer))
//                .build();
    }

    @Operation(summary = "Thêm mới tài khoản", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @PostMapping("/register")
    public ObjectResponseWrapper register(@RequestBody @Valid CreateCustomerDTO createCustomerDTO) {
        log.info("Search customer by id");
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(customerService.createCustomer(createCustomerDTO))
                .build();
    }

    @Operation(summary = "Cập nhật thông tin tài khoản", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @PutMapping("/update")
    public ObjectResponseWrapper update(@RequestBody UpdateCustomerDTO updateCustomerDTO) {
        log.info("Update customer");
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(customerService.updateCustomer(updateCustomerDTO))
                .build();
    }

    @Operation(summary = "Duyệt thông tin tài khoản", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @PutMapping("/check")
    public ObjectResponseWrapper checkCust(@RequestBody @Valid CheckedCustomerDTO checkedCustomerDTO) {
        log.info("Check customer");
        Boolean status = customerService.checkCustomer(checkedCustomerDTO);
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(status)
                .build();
    }

    @Operation(summary = "Đóng tài khoản", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @DeleteMapping("/delete")
    public ObjectResponseWrapper delete(@RequestBody DeleteCustomerDTO deleteCustomerDTO) {
        log.info("Delete customer");
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(customerService.deleteCustomer(deleteCustomerDTO))
                .build();
    }
}
