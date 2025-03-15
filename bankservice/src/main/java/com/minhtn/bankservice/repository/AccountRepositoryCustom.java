package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.Account;
import com.minhtn.bankservice.model.search.ParameterSearchAccount;
import com.minhtn.bankservice.model.wrapper.ListWrapper;

public interface AccountRepositoryCustom {
    ListWrapper<Account> searchAccount(ParameterSearchAccount parameterSearchAccount);
}
