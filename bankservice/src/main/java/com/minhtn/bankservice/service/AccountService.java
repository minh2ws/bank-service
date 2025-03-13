package com.minhtn.bankservice.service;

import com.minhtn.bankservice.model.customer.*;
import com.minhtn.bankservice.model.search.ParameterSearchCustomer;
import com.minhtn.bankservice.model.wrapper.ListWrapper;

public interface AccountService {
    CustomerDTO createCustomer(CreateCustomerDTO createCustomerDTO);
    CustomerDTO updateCustomer(UpdateCustomerDTO updateCustomerDTO);
    CustomerDTO deleteCustomer(DeleteCustomerDTO deleteCustomerDTO);
    Boolean checkCustomer(CheckedCustomerDTO checkedCustomerDTO);
    ListWrapper<CustomerDTO> search(ParameterSearchCustomer parameterSeachCustomer);
}
