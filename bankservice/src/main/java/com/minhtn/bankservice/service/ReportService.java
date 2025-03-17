package com.minhtn.bankservice.service;

import com.minhtn.bankservice.model.report.ReportAcctTransByAvlRange;
import com.minhtn.bankservice.model.search.ParameterReportCustByLocation;

import java.math.BigDecimal;

public interface ReportService {
    Long reportCustByLocation(ParameterReportCustByLocation parameterReportCustByLocation);
    ReportAcctTransByAvlRange reportAcctTransByRange(BigDecimal maxRange, BigDecimal minRange);
}
