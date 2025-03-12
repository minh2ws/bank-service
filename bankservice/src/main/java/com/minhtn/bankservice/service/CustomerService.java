package com.minhtn.bankservice.service;

import com.minhtn.bankservice.entity.Customer;
import com.minhtn.bankservice.model.customer.*;
import com.minhtn.bankservice.model.search.ParameterSearchCustomer;
import com.minhtn.bankservice.model.wrapper.ListWrapper;
import com.minhtn.bankservice.model.wrapper.ObjectResponseWrapper;

public interface CustomerService {
    CustomerDTO createCustomer(CreateCustomerDTO createCustomerDTO);
    CustomerDTO updateCustomer(UpdateCustomerDTO updateCustomerDTO);
    CustomerDTO deleteCustomer(DeleteCustomerDTO deleteCustomerDTO);
    Boolean checkCustomer(CheckedCustomerDTO checkedCustomerDTO);
    ListWrapper<CustomerDTO> search(ParameterSearchCustomer parameterSeachCustomer);
}
