package com.minhtn.bankservice.service.impl;

import com.minhtn.bankservice.entity.*;
import com.minhtn.bankservice.handler.ServiceException;
import com.minhtn.bankservice.model.search.ParameterSearchTransaction;
import com.minhtn.bankservice.model.transaction.CreditTransactionDTO;
import com.minhtn.bankservice.model.transaction.DebitTransactionDTO;
import com.minhtn.bankservice.model.transaction.InternalTransactionDTO;
import com.minhtn.bankservice.model.transaction.TransactionDTO;
import com.minhtn.bankservice.model.wrapper.ListWrapper;
import com.minhtn.bankservice.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl extends BaseService implements TransactionService {
    @Override
    @Transactional
    public TransactionDTO creditTransaction(CreditTransactionDTO creditTransactionDTO) {
        //compare currency code
        if (!"VND".equals(creditTransactionDTO.getCurrCode())) {
            throw new ServiceException("VND only");
        }
        //check account id is existed
        Account account = accountRepository.findById(creditTransactionDTO.getAccountId())
                .orElseThrow(() -> new ServiceException("Account not found"));
        if (!account.getCurrCode().equals("VND")) {
            throw new ServiceException("Account with currency VND only");
        }
        //check account status is available to credit
        AccountStatus accountStatus = account.getAccountStatus();
        if (accountStatus.getAccountStatusId().equals("C")) {
            throw new ServiceException("Account is closed");
        }
        Trancode trancode = trancodeRepository.findById(creditTransactionDTO.getTrancode())
                .orElseThrow(() -> new ServiceException("Trancode not found"));
        if(!trancode.getDbcr().equals("+")) {
            throw new ServiceException("Trancode is not correct for credit");
        }
        Branch branch = branchRepository.findById(creditTransactionDTO.getBranchId())
                        .orElseThrow(() -> new ServiceException("Branch not found"));

        Transaction transaction = Transaction.builder()
                .tranId(String.format("%020d", transactionRepository.getTranIdSeq()))
                .account(account)
                .traceNo(String.format("%06d", transactionRepository.getTranNumSeq()))
                .tranTime(new Date())
                .trancode(trancode)
                .refNo("CK" + String.format("%020d", transactionRepository.getTranIdSeq()))
                .dbcr(trancode.getDbcr())
                .tranAmount(creditTransactionDTO.getTranAmount())
                .feeAmount(BigDecimal.ZERO)
                .vatAmout(BigDecimal.ZERO)
                .description(creditTransactionDTO.getDescription())
                .currCode(creditTransactionDTO.getCurrCode())
                .destinationAcct(creditTransactionDTO.getDestinationAcct())
                .destinationName(creditTransactionDTO.getDestinationName())
                .userCreate(creditTransactionDTO.getUserCreate())
                .status(true)
                .merchantId(creditTransactionDTO.getMerchantId())
                .terminalId(creditTransactionDTO.getTerminalId())
                .branch(branch)
                .provinceId(creditTransactionDTO.getProvinceId())
                .districtId(creditTransactionDTO.getDistrictId())
                .wardId(creditTransactionDTO.getWardId())
                .acquireBank(creditTransactionDTO.getAccquireBank())
                .build();
        transactionRepository.save(transaction);
        account.setAvlBal(account.getAvlBal().add(creditTransactionDTO.getTranAmount()));
        return TransactionDTO.fromEntityRefIdOnly(transaction);
    }

    @Override
    @Transactional
    public TransactionDTO debitTransaction(DebitTransactionDTO debitTransactionDTO) {
        //compare currency code
        if (!"VND".equals(debitTransactionDTO.getCurrCode())) {
            throw new ServiceException("VND only");
        }
        //check account id is existed
        Account account = accountRepository.findById(debitTransactionDTO.getAccountId())
                .orElseThrow(() -> new ServiceException("Account not found"));
        if (!account.getAuthStat().equals("A")) {
            throw new ServiceException("Account not checked");
        }
        if (!account.getCurrCode().equals("VND")) {
            throw new ServiceException("Account with currency VND only");
        }
        //check account status is available to debit
        AccountStatus accountStatus = account.getAccountStatus();
        if (!accountStatus.getAccountStatusId().equals("N")) {
            throw new ServiceException("Account status is not available to debit");
        }
        Trancode trancode = trancodeRepository.findById(debitTransactionDTO.getTrancode())
                .orElseThrow(() -> new ServiceException("Trancode not found"));
        if(!trancode.getDbcr().equals("-")) {
            throw new ServiceException("Trancode is not correct for debit");
        }
        Branch branch = branchRepository.findById(debitTransactionDTO.getBranchId())
                .orElseThrow(() -> new ServiceException("Branch not found"));

        //add lock row account
        accountRepository.lockRecordAccount(debitTransactionDTO.getAccountId());

        //check amount limit
        if(account.getAvlLimit()
                .compareTo(debitTransactionDTO.getTranAmount()) < 0) {
            throw new ServiceException("Amount limit not enough");
        }
        //check available balance
        if (account.getAvlBal().compareTo(debitTransactionDTO.getTranAmount()
                .add(debitTransactionDTO.getFeeAmount())
                .add(debitTransactionDTO.getVatAmount())) < 0) {
            throw new ServiceException("Amount available not enough");
        }

        Transaction transaction = Transaction.builder()
                .tranId(String.format("%020d", transactionRepository.getTranIdSeq()))
                .account(account)
                .traceNo(String.format("%06d", transactionRepository.getTranNumSeq()))
                .tranTime(new Date())
                .trancode(trancode)
                .refNo("CK" + String.format("%020d", transactionRepository.getTranIdSeq()))
                .dbcr(trancode.getDbcr())
                .tranAmount(debitTransactionDTO.getTranAmount())
                .feeAmount(debitTransactionDTO.getFeeAmount())
                .vatAmout(debitTransactionDTO.getVatAmount())
                .description(debitTransactionDTO.getDescription())
                .currCode(debitTransactionDTO.getCurrCode())
                .destinationAcct(debitTransactionDTO.getDestinationAcct())
                .destinationName(debitTransactionDTO.getDestinationName())
                .userCreate(debitTransactionDTO.getUserCreate())
                .status(true)
                .merchantId(debitTransactionDTO.getMerchantId())
                .terminalId(debitTransactionDTO.getTerminalId())
                .branch(branch)
                .provinceId(debitTransactionDTO.getProvinceId())
                .districtId(debitTransactionDTO.getDistrictId())
                .wardId(debitTransactionDTO.getWardId())
                .acquireBank(debitTransactionDTO.getAccquireBank())
                .build();
        transactionRepository.save(transaction);
        account.setAvlBal(account.getAvlBal().subtract(debitTransactionDTO.getTranAmount()
                .add(debitTransactionDTO.getFeeAmount())
                .add(debitTransactionDTO.getVatAmount())));
        account.setAvlLimit(account.getAvlLimit().subtract(debitTransactionDTO.getTranAmount()));
        return TransactionDTO.fromEntityRefIdOnly(transaction);
    }

    @Override
    @Transactional
    public List<TransactionDTO> internalTransaction(InternalTransactionDTO internalTransactionDTO) {
        //compare currency code
        if (!"VND".equals(internalTransactionDTO.getCurrCode())) {
            throw new ServiceException("VND only");
        }
        //check account id is existed
        Account account = accountRepository.findById(internalTransactionDTO.getAccountId())
                .orElseThrow(() -> new ServiceException("Account not found"));
        if (!account.getAuthStat().equals("A")) {
            throw new ServiceException("Account not checked");
        }
        if (!account.getCurrCode().equals("VND")) {
            throw new ServiceException("Account with currency VND only");
        }
        //check account status is available to debit
        AccountStatus accountStatus = account.getAccountStatus();
        if (!accountStatus.getAccountStatusId().equals("N")) {
            throw new ServiceException("Account status is not available to debit");
        }
        Trancode trancodeDebit = trancodeRepository.findById("01")
                .orElseThrow(() -> new ServiceException("Trancode not found"));

        //add lock row account
        accountRepository.lockRecordAccount(internalTransactionDTO.getAccountId());

        //check amount limit
        if(account.getAvlLimit()
                .compareTo(internalTransactionDTO.getTranAmount()) < 0) {
            throw new ServiceException("Amount limit not enough");
        }
        //check available balance
        if (account.getAvlBal().compareTo(internalTransactionDTO.getTranAmount()
                .add(internalTransactionDTO.getFeeAmount())
                .add(internalTransactionDTO.getVatAmount())) < 0) {
            throw new ServiceException("Amount available not enough");
        }

        //check account id is existed
        Account destAccount = accountRepository.findById(internalTransactionDTO.getDestinationAcct())
                .orElseThrow(() -> new ServiceException("Account not found"));
        if (!destAccount.getCurrCode().equals("VND")) {
            throw new ServiceException("Account with currency VND only");
        }
        //check account status is available to credit
        AccountStatus destAccountStatus = destAccount.getAccountStatus();
        if (destAccountStatus.getAccountStatusId().equals("C")) {
            throw new ServiceException("Account is closed");
        }
        Trancode trancodeCredit = trancodeRepository.findById("02")
                .orElseThrow(() -> new ServiceException("Trancode not found"));

        Transaction debitTransaction = Transaction.builder()
                .tranId(String.format("%020d", transactionRepository.getTranIdSeq()))
                .account(account)
                .traceNo(String.format("%06d", transactionRepository.getTranNumSeq()))
                .tranTime(new Date())
                .trancode(trancodeDebit)
                .refNo("CK" + String.format("%020d", transactionRepository.getTranIdSeq()))
                .dbcr(trancodeDebit.getDbcr())
                .tranAmount(internalTransactionDTO.getTranAmount())
                .feeAmount(internalTransactionDTO.getFeeAmount())
                .vatAmout(internalTransactionDTO.getVatAmount())
                .description(internalTransactionDTO.getDescription())
                .currCode(internalTransactionDTO.getCurrCode())
                .destinationAcct(destAccount.getAccountId())
                .destinationName(destAccount.getCustomer().getFullName())
                .userCreate(internalTransactionDTO.getUserCreate())
                .status(true)
                .branch(account.getBranch())
                .provinceId(internalTransactionDTO.getProvinceId())
                .districtId(internalTransactionDTO.getDistrictId())
                .wardId(internalTransactionDTO.getWardId())
                .build();
        transactionRepository.save(debitTransaction);
        account.setAvlBal(account.getAvlBal().subtract(internalTransactionDTO.getTranAmount()
                .add(internalTransactionDTO.getFeeAmount())
                .add(internalTransactionDTO.getVatAmount())));
        account.setAvlLimit(account.getAvlLimit().subtract(internalTransactionDTO.getTranAmount()));

        Transaction creditTransaction = Transaction.builder()
                .tranId(String.format("%020d", transactionRepository.getTranIdSeq()))
                .account(destAccount)
                .traceNo(String.format("%06d", transactionRepository.getTranNumSeq()))
                .tranTime(new Date())
                .trancode(trancodeCredit)
                .refNo("CK" + String.format("%020d", transactionRepository.getTranIdSeq()))
                .dbcr(trancodeCredit.getDbcr())
                .tranAmount(internalTransactionDTO.getTranAmount())
                .feeAmount(BigDecimal.ZERO)
                .vatAmout(BigDecimal.ZERO)
                .description(internalTransactionDTO.getDescription())
                .currCode(internalTransactionDTO.getCurrCode())
                .destinationAcct(account.getAccountId())
                .destinationName(account.getCustomer().getFullName())
                .userCreate(internalTransactionDTO.getUserCreate())
                .status(true)
                .branch(account.getBranch())
                .provinceId(internalTransactionDTO.getProvinceId())
                .districtId(internalTransactionDTO.getDistrictId())
                .wardId(internalTransactionDTO.getWardId())
                .build();
        transactionRepository.save(creditTransaction);
        destAccount.setAvlBal(account.getAvlBal().add(internalTransactionDTO.getTranAmount()));

        List<TransactionDTO> transactions = new ArrayList<>();
        transactions.add(TransactionDTO.fromEntityRefIdOnly(debitTransaction));
        transactions.add(TransactionDTO.fromEntityRefIdOnly(creditTransaction));
        return transactions;
    }

    @Override
    public ListWrapper<TransactionDTO> search(ParameterSearchTransaction parameterSearchTransaction) {
        ListWrapper<Transaction> transactions = transactionRepository.searchTransaction(parameterSearchTransaction);
        return ListWrapper.<TransactionDTO>builder()
                .total(transactions.getTotal())
                .totalPage(transactions.getTotalPage())
                .pageSize(transactions.getPageSize())
                .page(transactions.getPage())
                .data(transactions.getData().stream().map(TransactionDTO::fromEntity).collect(Collectors.toList()))
                .build();
    }
}
