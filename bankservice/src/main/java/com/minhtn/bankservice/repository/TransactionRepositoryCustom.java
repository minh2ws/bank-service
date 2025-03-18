package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.Transaction;
import com.minhtn.bankservice.model.report.AcctTransByAvlRange;
import com.minhtn.bankservice.model.search.ParameterSearchTransaction;
import com.minhtn.bankservice.model.wrapper.ListWrapper;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepositoryCustom {
    ListWrapper<Transaction> searchTransaction(ParameterSearchTransaction parameterSearchTransaction);
    List<AcctTransByAvlRange> reportAcctTransByRange(BigDecimal maxRange, BigDecimal minRange);
}
