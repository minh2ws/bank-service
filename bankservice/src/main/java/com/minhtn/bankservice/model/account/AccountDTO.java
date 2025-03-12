package com.minhtn.bankservice.model.account;

import com.minhtn.bankservice.entity.*;
import jakarta.persistence.*;
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
public class AccountDTO {
    private String accountId;
    private Date openDate;
    private String currCode;
    private String recordStat;
    private String authStat;
    private String authBy;
    private String authAt;
    private Date closeDate;
    private BigDecimal avlBal;
    private BigDecimal minBal;
    private BigDecimal maxLimit;
    private BigDecimal avlLimit;
    private String notes;
    private String branchId;
    private String branchName;
    private String customerId;
    private String customerName;
    private String customerFullName;
    private String accountTypeId;
    private String accountTypeName;
    private String accountStatusId;
    private String accountStatusName;

    public static AccountDTO fromEntity(Account account) {
        return AccountDTO.builder()
                .accountId(account.getAccountId())
                .openDate(account.getOpenDate())
                .currCode(account.getCurrCode())
                .recordStat(account.getRecordStat())
                .authStat(account.getAuthStat())
                .authBy(account.getAuthBy())
                .authAt(account.getAuthAt())
                .closeDate(account.getCloseDate())
                .avlBal(account.getAvlBal())
                .minBal(account.getMinBal())
                .maxLimit(account.getMaxLimit())
                .avlLimit(account.getAvlLimit())
                .notes(account.getNotes())
                .branchId(account.getBranch().getBranchId())
                .branchName(account.getBranch().getName())
                .customerId(account.getCustomer().getCustomerId())
                .customerName(account.getCustomer().getFullName())
                .customerFullName(account.getCustomer().getFullName())
                .accountTypeId(account.getAccountType().getAccountTypeId())
                .accountTypeName(account.getAccountType().getName())
                .accountStatusId(account.getAccountStatus().getAccountStatusId())
                .accountStatusName(account.getAccountStatus().getName())
                .build();

    }
}
