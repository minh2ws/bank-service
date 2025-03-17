package com.minhtn.bankservice.controller;

import com.minhtn.bankservice.model.search.ParameterSearchTransaction;
import com.minhtn.bankservice.model.transaction.CreditTransactionDTO;
import com.minhtn.bankservice.model.transaction.DebitTransactionDTO;
import com.minhtn.bankservice.model.transaction.InternalTransactionDTO;
import com.minhtn.bankservice.model.transaction.TransactionDTO;
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

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping(Constant.TRANSACTION_SERVICE_URL)
@Tag(name = "Transaction API")
@Slf4j
public class TransactionController extends BaseController {
    @Operation(summary = "Tìm kiếm giao dịch", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = ListWrapper.class)))
    })
    @GetMapping("/search")
    public ListWrapper<TransactionDTO> searchTransaction(@RequestParam(value = "tranId", required = false) String tranId,
                                                         @RequestParam(value = "traceNo", required = false) String traceNo,
                                                         @RequestParam(value = "tranTimeFrom", required = false) Long tranTimeFrom,
                                                         @RequestParam(value = "tranTimeTo", required = false) Long tranTimeTo,
                                                         @RequestParam(value = "refNo", required = false) String refNo,
                                                         @RequestParam(value = "tranAmountFrom", required = false) BigDecimal tranAmountFrom,
                                                         @RequestParam(value = "tranAmountTo", required = false) BigDecimal tranAmountTo,
                                                         @RequestParam(value = "currCode", required = false) String currCode,
                                                         @RequestParam(value = "destinationAcct", required = false) String destinationAcct,
                                                         @RequestParam(value = "destinationName", required = false) String destinationName,
                                                         @RequestParam(value = "userCreate", required = false) String userCreate,
                                                         @RequestParam(value = "userApprove", required = false) String userApprove,
                                                         @RequestParam(value = "approveTimeFrom", required = false) Long approveTimeFrom,
                                                         @RequestParam(value = "approveTimeTo", required = false) Long approveTimeTo,
                                                         @RequestParam(value = "status", required = false) Boolean status,
                                                         @RequestParam(value = "merchantId", required = false) String merchantId,
                                                         @RequestParam(value = "terminalId", required = false) String terminalId,
                                                         @RequestParam(value = "branchId", required = false) String branchId,
                                                         @RequestParam(value = "trancode", required = false) String trancode,
                                                         @RequestParam(value = "accountId", required = false) String accountId,
                                                         @RequestParam(value = "provinceId", required = false) String provinceId,
                                                         @RequestParam(value = "wardId", required = false) String wardId,
                                                         @RequestParam(value = "districtId", required = false) String districtId,
                                                         @RequestParam(value = "acquireBank", required = false) String acquireBank,
                                                         @RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
                                                         @RequestParam(value = "sortField", required = false) String sortField,
                                                         @RequestParam(value = "descSort", required = false, defaultValue = "false") Boolean sortBy,
                                                         @RequestParam(value = "pageSize", required = false, defaultValue = "50")
                                                         @Min(value = 1, message = "page_size phải lớn hơn 0")
                                                         @Max(value = 50, message = "page_size phải bé hơn 50")
                                                         @Parameter(description = "Default: 15") Integer pageSize) {
        log.info("Search transaction");
        if (currentPage == null || currentPage <= 0) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 15;
        }
        Long startIndex = (long) (currentPage - 1) * pageSize;
        ParameterSearchTransaction parameterSearchTransaction = ParameterSearchTransaction.builder()
                .tranId(tranId)
                .traceNo(traceNo)
                .refNo(refNo)
                .tranAmountFrom(tranAmountFrom)
                .tranAmountTo(tranAmountTo)
                .currCode(currCode)
                .destinationAcct(destinationAcct)
                .destinationName(destinationName)
                .userCreate(userCreate)
                .userApprove(userApprove)
                .status(status)
                .merchantId(merchantId)
                .terminalId(terminalId)
                .branchId(branchId)
                .trancode(trancode)
                .accountId(accountId)
                .provinceId(provinceId)
                .wardId(wardId)
                .districtId(districtId)
                .acquireBank(acquireBank)
                .startIndex(startIndex)
                .pageSize(pageSize)
                .sortField(sortField)
                .descSort(sortBy)
                .build();
        if (tranTimeFrom != null) {
            parameterSearchTransaction.setTranTimeFrom(new Date(tranTimeFrom));
        }
        if (tranTimeTo != null) {
            parameterSearchTransaction.setTranTimeTo(new Date(tranTimeTo));
        }
        if (approveTimeFrom != null) {
            parameterSearchTransaction.setApproveTimeFrom(new Date(approveTimeFrom));
        }
        if (approveTimeTo != null) {
            parameterSearchTransaction.setApproveTimeTo(new Date(approveTimeTo));
        }

        return transactionService.search(parameterSearchTransaction);
    }

    @Operation(summary = "Credit transaction", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = TransactionDTO.class)))
    })
    @PostMapping("/credit")
    public ObjectResponseWrapper creditTransaction(@RequestBody @Valid CreditTransactionDTO creditTransactionDTO) {
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(transactionService.creditTransaction(creditTransactionDTO))
                .build();
    }

    @Operation(summary = "Debit transaction", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = TransactionDTO.class)))
    })
    @PostMapping("/debit")
    public ObjectResponseWrapper debitTransaction(@RequestBody @Valid DebitTransactionDTO debitTransactionDTO) {
        if (debitTransactionDTO.getFeeAmount() == null) {
            debitTransactionDTO.setFeeAmount(BigDecimal.ZERO);
        }
        if (debitTransactionDTO.getVatAmount() == null) {
            debitTransactionDTO.setVatAmount(BigDecimal.ZERO);
        }

        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(transactionService.debitTransaction(debitTransactionDTO))
                .build();
    }

    @Operation(summary = "Internal transfer transaction", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = TransactionDTO.class)))
    })
    @PostMapping("/internal-transfer")
    public ObjectResponseWrapper internalTransferTransaction(@RequestBody @Valid InternalTransactionDTO internalTransactionDTO) {
        if (internalTransactionDTO.getFeeAmount() == null) {
            internalTransactionDTO.setFeeAmount(BigDecimal.ZERO);
        }
        if (internalTransactionDTO.getVatAmount() == null) {
            internalTransactionDTO.setVatAmount(BigDecimal.ZERO);
        }

        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(transactionService.internalTransaction(internalTransactionDTO))
                .build();
    }
}
