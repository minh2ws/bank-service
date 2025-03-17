package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.Transaction;
import com.minhtn.bankservice.model.search.ParameterSearchTransaction;
import com.minhtn.bankservice.model.wrapper.ListWrapper;

public interface TransactionRepositoryCustom {
    ListWrapper<Transaction> searchTransaction(ParameterSearchTransaction parameterSearchTransaction);
}
