package com.minhtn.bankservice.model.search;

import com.minhtn.bankservice.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParameterSearchTransaction {
    private String tranId;
    private String traceNo;
    private Date tranTimeFrom;
    private Date tranTimeTo;
    private String refNo;
    private BigDecimal tranAmountFrom;
    private BigDecimal tranAmountTo;
    private String currCode;
    private String destinationAcct;
    private String destinationName;
    private String userCreate;
    private String userApprove;
    private Date approveTimeFrom;
    private Date approveTimeTo;
    private Boolean status;
    private String merchantId;
    private String terminalId;
    private String branchId;
    private String trancode;
    private String accountId;
    private String provinceId;
    private String wardId;
    private String districtId;
    private String acquireBank;

    // page

    private Long startIndex;

    private Integer pageSize;

    private String sortField;

    private Boolean descSort = false;

    //build entity graph

    public boolean buildAcount = false;

    // getter, setter custom
    public void setSortField(String sortField) {
        if (sortField == null) return;
        boolean validField = false;
        for (Field field : Transaction.class.getDeclaredFields()) {
            if (field.getName().equals(sortField)) {
                validField = true;
                break;
            }
        }
        if (!validField) {
            throw new IllegalArgumentException("Invalid sort field");
        }
        this.sortField = sortField;
    }
}
