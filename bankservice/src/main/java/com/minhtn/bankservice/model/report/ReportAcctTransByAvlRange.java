package com.minhtn.bankservice.model.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportAcctTransByAvlRange {
    private AcctTransByAvlRange highAvlBal;
    private AcctTransByAvlRange medAvlBal;
    private AcctTransByAvlRange lowAvlBal;
}
