package com.minhtn.bankservice.controller;

import com.minhtn.bankservice.handler.ServiceException;
import com.minhtn.bankservice.model.customer.*;
import com.minhtn.bankservice.model.search.ParameterSearchCustomer;
import com.minhtn.bankservice.model.user.UserDTO;
import com.minhtn.bankservice.model.wrapper.ListWrapper;
import com.minhtn.bankservice.model.wrapper.ObjectResponseWrapper;
import com.minhtn.bankservice.ultility.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(Constant.CUSTOMER_SERVICE_URL)
@Tag(name = "Customer API")
@Slf4j
public class CustomerController extends BaseController {

    @Operation(summary = "Tìm kiếm khách hàng", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = ListWrapper.class)))
    })
    @GetMapping("/search")
    public ListWrapper<CustomerDTO> searchCustomer(@RequestParam(value = "custId", required = false) String id,
                                                   @RequestParam(value = "fullName", required = false) String fullName,
                                                   @RequestParam(value = "engName", required = false) String engName,
                                                   @RequestParam(value = "email", required = false) String email,
                                                   @RequestParam(value = "phone", required = false) String phone,
                                                   @RequestParam(value = "address", required = false) String address,
                                                   @RequestParam(value = "idType", required = false) String idType,
                                                   @RequestParam(value = "idNumber", required = false) String idNumber,
                                                   @RequestParam(value = "idIssueDateFrom", required = false) Long idIssueDateFrom,
                                                   @RequestParam(value = "idIssueDateTo", required = false) Long idIssueDateTo,
                                                   @RequestParam(value = "idIssuePlace", required = false) String idIssuePlace,
                                                   @RequestParam(value = "idExpireDateFrom", required = false) Long idExpireDateFrom,
                                                   @RequestParam(value = "idExpireDateTo", required = false) Long idExpireDateTo,
                                                   @RequestParam(value = "recordStat", required = false) String recordStat,
                                                   @RequestParam(value = "authStat", required = false) String authStat,
                                                   @RequestParam(value = "authBy", required = false) String authBy,
                                                   @RequestParam(value = "authAtFrom", required = false) Long authAtFrom,
                                                   @RequestParam(value = "authAtTo", required = false) Long authAtTo,
                                                   @RequestParam(value = "createFrom", required = false) Long createFrom,
                                                   @RequestParam(value = "createTo", required = false) Long createTo,
                                                   @RequestParam(value = "createBy", required = false) String createBy,
                                                   @RequestParam(value = "updateAtFrom", required = false) Long updateAtFrom,
                                                   @RequestParam(value = "updateAtTo", required = false) Long updateAtTo,
                                                   @RequestParam(value = "updateBy", required = false) String updateBy,
                                                   @RequestParam(value = "countryCode", required = false) String countryCode,
                                                   @RequestParam(value = "provinceId", required = false) String provinceId,
                                                   @RequestParam(value = "wardId", required = false) String wardId,
                                                   @RequestParam(value = "districtId", required = false) String districtId,
                                                   @RequestParam(value = "customerTypeId", required = false) String customerTypeId,
                                                   @RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
                                                   @RequestParam(value = "sortField", required = false) String sortField,
                                                   @RequestParam(value = "descSort", required = false, defaultValue = "false") Boolean sortBy,
                                                   @RequestParam(value = "pageSize", required = false, defaultValue = "50")
                                                   @Min(value = 1, message = "page_size phải lớn hơn 0")
                                                   @Max(value = 50, message = "page_size phải bé hơn 50")
                                                   @Parameter(description = "Default: 15") Integer pageSize) {
        log.info("Search customer");
        if (currentPage == null || currentPage <= 0) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 15;
        }
        Long startIndex = (long) (currentPage - 1) * pageSize;
        ParameterSearchCustomer parameterSearchCustomer = ParameterSearchCustomer.builder()
                .customerId(id)
                .fullName(fullName)
                .engName(engName)
                .email(email)
                .phone(phone)
                .address(address)
                .idType(idType)
                .idNumber(idNumber)
                .idIssuePlace(idIssuePlace)
                .recordStat(recordStat)
                .authStat(authStat)
                .authBy(authBy)
                .createBy(createBy)
                .updateBy(updateBy)
                .countryCode(countryCode)
                .provinceId(provinceId)
                .wardId(wardId)
                .districtId(districtId)
                .customerTypeId(customerTypeId)
                .startIndex(startIndex)
                .pageSize(pageSize)
                .sortField(sortField)
                .descSort(sortBy)
                .build();
        if (idIssueDateFrom != null) {
            parameterSearchCustomer.setIdIssueDateFrom(new Date(idIssueDateFrom));
        }
        if (idIssueDateTo != null) {
            parameterSearchCustomer.setIdIssueDateTo(new Date(idIssueDateTo));
        }
        if (idExpireDateFrom != null) {
            parameterSearchCustomer.setIdExpireDateFrom(new Date(idExpireDateFrom));
        }
        if (idExpireDateTo != null) {
            parameterSearchCustomer.setIdExpireDateTo(new Date(idExpireDateTo));
        }
        if (authAtFrom != null) {
            parameterSearchCustomer.setAuthAtFrom(new Date(authAtFrom));
        }
        if (authAtTo != null) {
            parameterSearchCustomer.setAuthAtTo(new Date(authAtTo));
        }
        if (updateAtFrom != null) {
            parameterSearchCustomer.setUpdateAtFrom(new Date(updateAtFrom));
        }
        if (updateAtTo != null) {
            parameterSearchCustomer.setUpdateAtTo(new Date(updateAtTo));
        }
        if (createFrom != null) {
            parameterSearchCustomer.setCreateFrom(new Date(createFrom));
        }
        if (createTo != null) {
            parameterSearchCustomer.setCreateTo(new Date(createTo));
        }

        return customerService.search(parameterSearchCustomer);
    }

    @Operation(summary = "Lấy thông tin khách hàng", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = ObjectResponseWrapper.class)))
    })
    @GetMapping("/{id}")
    public ObjectResponseWrapper getCustomer(@PathVariable String id) {
        ParameterSearchCustomer parameterSearchCustomer = new ParameterSearchCustomer();
        parameterSearchCustomer.setCustomerId(id);
        parameterSearchCustomer.setStartIndex(0L);
        parameterSearchCustomer.setPageSize(1);
        ListWrapper<CustomerDTO> customers = customerService.search(parameterSearchCustomer);
        if (customers.getData().isEmpty()) {
            throw new ServiceException("Customer not found");
        }
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(customerService.search(parameterSearchCustomer))
                .build();
    }

    @Operation(summary = "Thêm mới khách hàng", responses = {
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

    @Operation(summary = "Cập nhật thông tin khách hàng", responses = {
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

    @Operation(summary = "Duyệt thông tin khách hàng", responses = {
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

    @Operation(summary = "Đóng khách hàng", responses = {
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
