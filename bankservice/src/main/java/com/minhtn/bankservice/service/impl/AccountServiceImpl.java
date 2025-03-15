package com.minhtn.bankservice.service.impl;

import com.minhtn.bankservice.entity.*;
import com.minhtn.bankservice.handler.ServiceException;
import com.minhtn.bankservice.model.account.*;
import com.minhtn.bankservice.model.search.ParameterSearchAccount;
import com.minhtn.bankservice.model.wrapper.ListWrapper;
import com.minhtn.bankservice.service.AccountService;
import com.minhtn.bankservice.ultility.EnumConst;
import com.minhtn.bankservice.ultility.Extension;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl extends BaseService implements AccountService {
    private final UserServiceImpl userServiceImpl;

    @Override
    public AccountDTO createAccount(CreateAccountDTO createAccountDTO) {
        //validate
        Customer customer = customerRepository.findById(createAccountDTO.getCustomerId())
                .orElseThrow(() -> new ServiceException("Customer Id not found"));
        //compare currency code
        EnumConst.Currency currCode = EnumConst.Currency.fromString(createAccountDTO.getCurrCode());
        if (currCode == null) {
            throw new ServiceException("Currency code not found");
        }
        CustomerType customerType = customer.getCustomerType();
        if (Double.parseDouble(customerType.getMaxAmountLimit().trim()) < createAccountDTO.getMaxLimit().doubleValue()) {
            createAccountDTO.setMaxLimit(BigDecimal.valueOf(Double.parseDouble(customerType.getMaxAmountLimit().trim())));
        }
        Branch branch = branchRepository.findById(createAccountDTO.getBranchId())
                .orElseThrow(() -> new ServiceException("Branch Id not found"));
        AccountType accountType = accountTypeRepository.findById(createAccountDTO.getAccountTypeId())
                .orElseThrow(() -> new ServiceException("Account Type Id not found"));
        AccountStatus accountStatus = accountStatusRepository.findById("N")
                .orElseThrow(() -> new ServiceException("Account Status Id not found"));

        //generate account id
        String accountId = accountRepository.findMaxAccountIdByCustomerId(customer.getCustomerId());
        if (accountId == null) {
            accountId = customer.getCustomerId() + "0001";
        } else {
            accountId = customer.getCustomerId() + String.format("%04d", Long.parseLong(accountId.substring(8)) + 1);
        }

        //build entity and save to db
        Account account = Account.builder()
                .accountId(accountId)
                .customer(customer)
                .currCode(currCode.toString())
                .maxLimit(createAccountDTO.getMaxLimit())
                .avlLimit(createAccountDTO.getMaxLimit())
                .minBal(BigDecimal.valueOf(0))
                .avlBal(BigDecimal.valueOf(0))
                .notes(createAccountDTO.getNotes())
                .createBy(userServiceImpl.getUsernameLogin())
                .createAt(new Date())
                .authStat("N")
                .branch(branch)
                .accountType(accountType)
                .accountStatus(accountStatus)
                .build();
        accountRepository.save(account);
        return AccountDTO.fromEntityRefIdOnly(account, customer);
    }

    @Override
    public AccountDTO updateAccount(UpdateAccountDTO updateAccountDTO) {
        BigDecimal avlLimit;
        Account account = accountRepository.findById(updateAccountDTO.getAccountId())
                .orElseThrow(() -> new ServiceException("Account id not found"));
        CustomerType customerType = account.getCustomer().getCustomerType();
        if (Double.parseDouble(customerType.getMaxAmountLimit().trim()) < updateAccountDTO.getMaxLimit().doubleValue()) {
            throw new ServiceException("Max limit amount is " + customerType.getMaxAmountLimit().trim());
        }

        avlLimit = updateAccountDTO.getMaxLimit().subtract(account.getMaxLimit().subtract(account.getAvlLimit()));
        account.setAvlLimit(avlLimit);
        if(avlLimit.compareTo(BigDecimal.ZERO) < 0) {
            account.setAvlLimit(BigDecimal.valueOf(0));
        }
        account.setMaxLimit(updateAccountDTO.getMaxLimit());
        account.setNotes(updateAccountDTO.getNotes());
        account.setUpdateAt(new Date());
        account.setUpdateBy(userServiceImpl.getUsernameLogin());
        account.setAuthStat("N");
        accountRepository.save(account);
        return AccountDTO.fromEntityRefIdOnly(account, account.getCustomer());
    }

    @Override
    public AccountDTO updateStatusAccount(UpdateStatusAccountDTO updateStatusAccountDTO) {
        String username = userServiceImpl.getUsernameLogin();
        Account account = accountRepository.findById(updateStatusAccountDTO.getAccountId())
                .orElseThrow(() -> new ServiceException("Account id not found"));
        if (account.getAccountStatus().getAccountStatusId().equals(updateStatusAccountDTO.getAccountStatusId())) {
            return AccountDTO.fromEntityRefIdOnly(account, account.getCustomer());
        }
        AccountStatus accountStatus = accountStatusRepository.findById(updateStatusAccountDTO.getAccountStatusId())
                .orElseThrow(() -> new ServiceException("Account Status Id not found"));
        if (account.getAccountStatus().getAccountStatusId().equals(updateStatusAccountDTO.getAccountStatusId())) {
            return AccountDTO.fromEntityRefIdOnly(account, account.getCustomer());
        }
        account.setAuthStat("N");
        account.setUpdateAt(new Date());
        account.setUpdateBy(username);
        if (account.getAccountStatus().getAccountStatusId().equals("C") && updateStatusAccountDTO.getAccountStatusId().equals("N")) {
            account.setCloseDate(null);
        }
        if (accountStatus.getAccountStatusId().equals("C")) {
            if (account.getAvlBal().compareTo(BigDecimal.ZERO) > 0) {
                throw new ServiceException("Please settlement account balance before close account");
            }
            account.setCloseDate(new Date());
        }
        account.setAccountStatus(accountStatus);
        AccountStatusHistory accountStatusHistory = AccountStatusHistory.builder()
                .id(AccountStatusHistoryPK.builder()
                        .accountStatusId(account.getAccountStatus().getAccountStatusId())
                        .accountId(account.getAccountId())
                        .createdAt(new Date())
                        .build())
                .createdBy(username)
                .build();
        accountStatusHistoryRepository.save(accountStatusHistory);
        accountRepository.save(account);
        return AccountDTO.fromEntityRefIdOnly(account, account.getCustomer());
    }

    @Override
    public Boolean checkAccount(CheckedAccountDTO checkedAccountDTO) {
        Account account = accountRepository.findById(checkedAccountDTO.getAccountId())
                .orElseThrow(() -> new ServiceException("Account id not found"));
        if (account.getAuthStat().equals("A")) {
            throw new ServiceException("Account already checked");
        }

        account.setAuthBy(userServiceImpl.getUsernameLogin());
        if (!Extension.isBlankOrNull(checkedAccountDTO.getAuthBy())) {
            account.setAuthBy(checkedAccountDTO.getAuthBy());
        }
        account.setAuthAt(new Date());
        account.setAuthStat("A");
        accountRepository.save(account);
        return true;
    }

    @Override
    public ListWrapper<AccountDTO> search(ParameterSearchAccount parameterSeachAccount) {
        ListWrapper<Account> accounts = accountRepository.searchAccount(parameterSeachAccount);
        return ListWrapper.<AccountDTO>builder()
                .total(accounts.getTotal())
                .totalPage(accounts.getTotalPage())
                .page(accounts.getPage())
                .pageSize(accounts.getPageSize())
                .data(accounts.getData().stream().map(AccountDTO::fromEntity).collect(Collectors.toList()))
                .build();
    }
}
