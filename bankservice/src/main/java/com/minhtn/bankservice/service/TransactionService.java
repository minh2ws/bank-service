package com.minhtn.bankservice.service;

import com.minhtn.bankservice.model.search.ParameterSearchTransaction;
import com.minhtn.bankservice.model.transaction.CreditTransactionDTO;
import com.minhtn.bankservice.model.transaction.DebitTransactionDTO;
import com.minhtn.bankservice.model.transaction.InternalTransactionDTO;
import com.minhtn.bankservice.model.transaction.TransactionDTO;
import com.minhtn.bankservice.model.wrapper.ListWrapper;

import java.util.List;

public interface TransactionService {
    TransactionDTO creditTransaction(CreditTransactionDTO creditTransactionDTO);
    TransactionDTO debitTransaction(DebitTransactionDTO debitTransactionDTO);
    List<TransactionDTO> internalTransaction(InternalTransactionDTO internalTransactionDTO);
    ListWrapper<TransactionDTO> search(ParameterSearchTransaction parameterSearchTransaction);
}
