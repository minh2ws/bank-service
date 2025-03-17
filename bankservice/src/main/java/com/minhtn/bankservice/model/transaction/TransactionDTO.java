package com.minhtn.bankservice.model.transaction;

import com.minhtn.bankservice.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {
    private String tranId;
    private String traceNo;
    private Date tranTime;
    private String refNo;
    private String dbcr;
    private BigDecimal tranAmount;
    private BigDecimal feeAmount;
    private BigDecimal vatAmount;
    private String description;
    private String currCode;
    private String destinationAcct;
    private String destinationName;
    private String userCreate;
    private String userApprove;
    private Date approveTime;
    private Boolean status;
    private String merchantId;
    private String terminalId;
    private String branchId;
    private String bracnhName;
    private String trancode;
    private String trancodeName;
    private String accountId;
    private String provinceId;
    private String districtId;
    private String wardId;
    private String accquireBank;

    public static TransactionDTO fromEntity(Transaction transaction) {
        return TransactionDTO.builder()
                .tranId(transaction.getTranId())
                .traceNo(transaction.getTraceNo())
                .tranTime(transaction.getTranTime())
                .refNo(transaction.getRefNo())
                .dbcr(transaction.getDbcr())
                .tranAmount(transaction.getTranAmount())
                .feeAmount(transaction.getFeeAmount())
                .vatAmount(transaction.getVatAmout())
                .description(transaction.getDescription())
                .currCode(transaction.getCurrCode())
                .destinationAcct(transaction.getDestinationAcct())
                .destinationName(transaction.getDestinationName())
                .userCreate(transaction.getUserCreate())
                .userApprove(transaction.getUserApprove())
                .approveTime(transaction.getApproveTime())
                .status(transaction.getStatus())
                .merchantId(transaction.getMerchantId())
                .terminalId(transaction.getTerminalId())
                .branchId(transaction.getBranch().getBranchId())
                .trancode(transaction.getTrancode().getTrancodeId())
                .accountId(transaction.getAccount().getAccountId())
                .provinceId(transaction.getProvinceId())
                .districtId(transaction.getDistrictId())
                .wardId(transaction.getWardId())
                .accquireBank(transaction.getAcquireBank())
                .build();
    }

    public static TransactionDTO fromEntityRefIdOnly(Transaction transaction) {
        return TransactionDTO.builder()
                .tranId(transaction.getTranId())
                .traceNo(transaction.getTraceNo())
                .tranTime(transaction.getTranTime())
                .refNo(transaction.getRefNo())
                .dbcr(transaction.getDbcr())
                .tranAmount(transaction.getTranAmount())
                .feeAmount(transaction.getFeeAmount())
                .vatAmount(transaction.getVatAmout())
                .description(transaction.getDescription())
                .currCode(transaction.getCurrCode())
                .destinationAcct(transaction.getDestinationAcct())
                .destinationName(transaction.getDestinationName())
                .userCreate(transaction.getUserCreate())
                .userApprove(transaction.getUserApprove())
                .approveTime(transaction.getApproveTime())
                .status(transaction.getStatus())
                .merchantId(transaction.getMerchantId())
                .terminalId(transaction.getTerminalId())
                .branchId(transaction.getBranch().getBranchId())
                .trancode(transaction.getTrancode().getTrancodeId())
                .accountId(transaction.getAccount().getAccountId())
                .provinceId(transaction.getProvinceId())
                .districtId(transaction.getDistrictId())
                .wardId(transaction.getWardId())
                .accquireBank(transaction.getAcquireBank())
                .build();
    }
}
