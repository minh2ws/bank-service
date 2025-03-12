package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.Customer;
import com.minhtn.bankservice.model.search.ParameterSearchCustomer;
import com.minhtn.bankservice.model.wrapper.ListWrapper;

public interface CustomerRepositoryCustom {
    ListWrapper<Customer> findCustomer(ParameterSearchCustomer parameterSearchCustomer);
}
