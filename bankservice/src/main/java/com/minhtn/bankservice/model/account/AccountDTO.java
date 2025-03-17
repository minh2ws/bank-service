package com.minhtn.bankservice.model.account;

import com.minhtn.bankservice.entity.Account;
import com.minhtn.bankservice.entity.Customer;
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
    private String createBy;
    private Date createAt;
    private String authStat;
    private String authBy;
    private Date authAt;
    private String updateBy;
    private Date updateAt;
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
                .currCode(account.getCurrCode())
                .createAt(account.getCreateAt())
                .createBy(account.getCreateBy())
                .authStat(account.getAuthStat())
                .authBy(account.getAuthBy())
                .authAt(account.getAuthAt())
                .updateAt(account.getUpdateAt())
                .updateBy(account.getUpdateBy())
                .closeDate(account.getCloseDate())
                .avlBal(account.getAvlBal())
                .minBal(account.getMinBal())
                .maxLimit(account.getMaxLimit())
                .avlLimit(account.getAvlLimit())
                .notes(account.getNotes())
                .branchId(account.getBranch().getBranchId())
                .branchName(account.getBranch().getName())
                .accountTypeId(account.getAccountType().getAccountTypeId())
                .accountTypeName(account.getAccountType().getName())
                .accountStatusId(account.getAccountStatus().getAccountStatusId())
                .accountStatusName(account.getAccountStatus().getName())
                .build();

    }

    public static AccountDTO fromEntityRefIdOnly(Account account, Customer customer) {
        return AccountDTO.builder()
                .accountId(account.getAccountId())
                .currCode(account.getCurrCode())
                .createAt(account.getCreateAt())
                .createBy(account.getCreateBy())
                .authStat(account.getAuthStat())
                .authBy(account.getAuthBy())
                .authAt(account.getAuthAt())
                .updateAt(account.getUpdateAt())
                .updateBy(account.getUpdateBy())
                .closeDate(account.getCloseDate())
                .avlBal(account.getAvlBal())
                .minBal(account.getMinBal())
                .maxLimit(account.getMaxLimit())
                .avlLimit(account.getAvlLimit())
                .notes(account.getNotes())
                .branchId(account.getBranch().getBranchId())
                .customerId(account.getCustomer().getCustomerId())
                .accountTypeId(account.getAccountType().getAccountTypeId())
                .accountStatusId(account.getAccountStatus().getAccountStatusId())
                .build();

    }
}
