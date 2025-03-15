package com.minhtn.bankservice.controller;

import com.minhtn.bankservice.handler.ServiceException;
import com.minhtn.bankservice.model.account.*;
import com.minhtn.bankservice.model.search.ParameterSearchAccount;
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
@RequestMapping(Constant.ACCOUNT_SERVICE_URL)
@Tag(name = "Account API")
@Slf4j
public class AccountController extends BaseController {

    @Operation(summary = "Tìm kiếm tài khoản", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = ListWrapper.class)))
    })
    @GetMapping("/search")
    public ListWrapper<AccountDTO> search(@RequestParam(value = "accountId", required = false) String accountId,
                                        @RequestParam(value = "currCode", required = false) String currCode,
                                        @RequestParam(value = "authStat", required = false) String authStat,
                                        @RequestParam(value = "authBy", required = false) String authBy,
                                        @RequestParam(value = "closeDateFrom", required = false) Long closeDateFrom,
                                        @RequestParam(value = "closeDateTo", required = false) Long closeDateTo,
                                        @RequestParam(value = "branchId", required = false) String branchId,
                                        @RequestParam(value = "authAtFrom", required = false) Long authAtFrom,
                                        @RequestParam(value = "authAtTo", required = false) Long authAtTo,
                                        @RequestParam(value = "createFrom", required = false) Long createFrom,
                                        @RequestParam(value = "createTo", required = false) Long createTo,
                                        @RequestParam(value = "createBy", required = false) String createBy,
                                        @RequestParam(value = "updateAtFrom", required = false) Long updateAtFrom,
                                        @RequestParam(value = "updateAtTo", required = false) Long updateAtTo,
                                        @RequestParam(value = "updateBy", required = false) String updateBy,
                                        @RequestParam(value = "customerId", required = false) String customerId,
                                        @RequestParam(value = "accountTypeId", required = false) String accountTypeId,
                                        @RequestParam(value = "accountStatusId", required = false) String accountStatusId,
                                        @RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
                                        @RequestParam(value = "sortField", required = false) String sortField,
                                        @RequestParam(value = "descSort", required = false, defaultValue = "false") Boolean sortBy,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "50")
                                            @Min(value = 1, message = "page_size phải lớn hơn 0")
                                            @Max(value = 50, message = "page_size phải bé hơn 50")
                                            @Parameter(description = "Default: 15") Integer pageSize) {
        log.info("Search account");
        if (currentPage == null || currentPage <= 0) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 15;
        }
        Long startIndex = (long) (currentPage - 1) * pageSize;
        ParameterSearchAccount parameterSearchAccount = new ParameterSearchAccount();
        parameterSearchAccount.setAccountId(accountId);
        parameterSearchAccount.setCurrCode(currCode);
        parameterSearchAccount.setAuthBy(authBy);
        parameterSearchAccount.setBranchId(branchId);
        parameterSearchAccount.setCustomerId(customerId);
        parameterSearchAccount.setAccountTypeId(accountTypeId);
        parameterSearchAccount.setAccountStatusId(accountStatusId);
        parameterSearchAccount.setAuthStat(authStat);
        parameterSearchAccount.setCreateBy(createBy);
        parameterSearchAccount.setUpdateBy(updateBy);
        parameterSearchAccount.setStartIndex(startIndex);
        parameterSearchAccount.setPageSize(pageSize);
        parameterSearchAccount.setSortField(sortField);
        parameterSearchAccount.setDescSort(sortBy);
        if (closeDateFrom != null) {
            parameterSearchAccount.setCloseDateFrom(new Date(closeDateFrom));
        }
        if (closeDateTo != null) {
            parameterSearchAccount.setCloseDateTo(new Date(closeDateTo));
        }
        if (authAtFrom != null) {
            parameterSearchAccount.setAuthAtFrom(new Date(authAtFrom));
        }
        if (authAtTo != null) {
            parameterSearchAccount.setAuthAtTo(new Date(authAtTo));
        }
        if (createFrom != null) {
            parameterSearchAccount.setCreateFrom(new Date(createFrom));
        }
        if (createTo != null) {
            parameterSearchAccount.setCreateTo(new Date(createTo));
        }
        if (updateAtFrom != null) {
            parameterSearchAccount.setUpdateAtFrom(new Date(updateAtFrom));
        }
        if (updateAtTo != null) {
            parameterSearchAccount.setUpdateAtTo(new Date(updateAtTo));
        }

        return accountService.search(parameterSearchAccount);
    }

    @Operation(summary = "Lấy thông tin tài khoản", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = ObjectResponseWrapper.class)))
    })
    @GetMapping("/{id}")
    public ObjectResponseWrapper getAccount(@PathVariable String id) {
        ParameterSearchAccount parameterSearchAccount = new ParameterSearchAccount();
        parameterSearchAccount.setAccountId(id);
        parameterSearchAccount.setStartIndex(0L);
        parameterSearchAccount.setPageSize(1);
        ListWrapper<AccountDTO> account = accountService.search(parameterSearchAccount);
        if (account.getData().isEmpty()) {
            throw new ServiceException("Account not found");
        }
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(account.getData().getFirst())
                .build();
    }

    @Operation(summary = "Thêm mới tài khoản", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @PostMapping("/register")
    public ObjectResponseWrapper register(@RequestBody @Valid CreateAccountDTO createAccountDTO) {
        log.info("Create account");
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(accountService.createAccount(createAccountDTO))
                .build();
    }

    @Operation(summary = "Cập nhật thông tin tài khoản", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @PutMapping("/update")
    public ObjectResponseWrapper update(@RequestBody UpdateAccountDTO updateAccountDTO) {
        log.info("Update account");
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(accountService.updateAccount(updateAccountDTO))
                .build();
    }

    @Operation(summary = "Cập nhật thông tin tài khoản", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @PutMapping("/updateStatus")
    public ObjectResponseWrapper updateStatus(@RequestBody UpdateStatusAccountDTO updateStatusAccountDTO) {
        log.info("Update status account");
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(accountService.updateStatusAccount(updateStatusAccountDTO))
                .build();
    }

    @Operation(summary = "Duyệt thông tin tài khoản", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = UserDTO.class)))
    })
    @PutMapping("/check")
    public ObjectResponseWrapper checkCust(@RequestBody @Valid CheckedAccountDTO checkedAccountDTO) {
        log.info("Check account");
        Boolean status = accountService.checkAccount(checkedAccountDTO);
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(status)
                .build();
    }
}