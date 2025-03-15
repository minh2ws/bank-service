package com.minhtn.bankservice.service;

import com.minhtn.bankservice.model.account.*;
import com.minhtn.bankservice.model.search.ParameterSearchAccount;
import com.minhtn.bankservice.model.wrapper.ListWrapper;

public interface AccountService {
    AccountDTO createAccount(CreateAccountDTO createAccountDTO);
    AccountDTO updateAccount(UpdateAccountDTO updateAccountDTO);
    Boolean checkAccount(CheckedAccountDTO checkedAccountDTO);
    AccountDTO updateStatusAccount(UpdateStatusAccountDTO updateStatusAccountDTO);
    ListWrapper<AccountDTO> search(ParameterSearchAccount parameterSeachAccount);
}
