package com.minhtn.bankservice.service;

import com.minhtn.bankservice.model.customer.CheckedCustomerDTO;
import com.minhtn.bankservice.model.customer.CreateCustomerDTO;
import com.minhtn.bankservice.model.customer.CustomerDTO;
import com.minhtn.bankservice.model.customer.UpdateCustomerDTO;
import com.minhtn.bankservice.model.search.ParameterSearchCustomer;
import com.minhtn.bankservice.model.wrapper.ListWrapper;
import com.minhtn.bankservice.model.wrapper.ObjectResponseWrapper;

public interface CustomerService {
    CustomerDTO createCustomer(CreateCustomerDTO createCustomerDTO);
    CustomerDTO updateCustomer(UpdateCustomerDTO updateCustomerDTO);
    CustomerDTO deleteCustomer(String customerId);
    Boolean checkCustomer(CheckedCustomerDTO checkedCustomerDTO);
    CustomerDTO getCustomerById(String customerId);
    ListWrapper<CustomerDTO> search(ParameterSearchCustomer parameterSeachCustomer);
}
