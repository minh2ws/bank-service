package com.minhtn.bankservice.service.impl;

import com.minhtn.bankservice.model.report.AcctTransByAvlRange;
import com.minhtn.bankservice.model.report.ReportAcctTransByAvlRange;
import com.minhtn.bankservice.model.search.ParameterReportCustByLocation;
import com.minhtn.bankservice.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl extends BaseService implements ReportService {
    @Override
    public Long reportCustByLocation(ParameterReportCustByLocation parameterReportCustByLocation) {
        return customerRepository.reportCustByLocation(parameterReportCustByLocation);
    }

    @Override
    public ReportAcctTransByAvlRange reportAcctTransByRange(BigDecimal maxRange, BigDecimal minRange) {
        List<AcctTransByAvlRange> accts = transactionRepository.reportAcctTransByRange(maxRange, minRange);
        return ReportAcctTransByAvlRange.builder()
                .highAvlBal(accts.get(0))
                .medAvlBal(accts.get(1))
                .lowAvlBal(accts.get(2))
                .build();
    }
}
