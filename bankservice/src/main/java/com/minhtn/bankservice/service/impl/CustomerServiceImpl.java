package com.minhtn.bankservice.service.impl;

import com.minhtn.bankservice.entity.*;
import com.minhtn.bankservice.handler.ServiceException;
import com.minhtn.bankservice.model.customer.*;
import com.minhtn.bankservice.model.search.ParameterSearchCustomer;
import com.minhtn.bankservice.model.wrapper.ListWrapper;
import com.minhtn.bankservice.service.CustomerService;
import com.minhtn.bankservice.ultility.Extension;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends BaseService implements CustomerService {
    private final UserServiceImpl userServiceImpl;

    @Override
    public CustomerDTO createCustomer(CreateCustomerDTO createCustomerDTO) {

        if (createCustomerDTO.getIdExpireDate().before(new Date())) {
            throw new ServiceException("Expire date must be greater than current date");
        }

        if (createCustomerDTO.getIdIssueDate().after(new Date())) {
            throw new ServiceException("Issue date must be less than current date");
        }

        //validate
        Branch branch = branchRepository.findById(createCustomerDTO.getBranchId())
                .orElseThrow(() -> new ServiceException("Branch not found"));

        Optional<Customer> custIdNum = customerRepository.findByIdNumber(createCustomerDTO.getIdNumber());
        if (custIdNum.isPresent()) {
            throw new ServiceException("Id number already exists");
        }

        Optional<Customer> custPhone = customerRepository.findByPhone(createCustomerDTO.getPhone());
        if (custPhone.isPresent()) {
            throw new ServiceException("Phone already exists");
        }

        Optional<Customer> custEmail = customerRepository.findByEmail(createCustomerDTO.getEmail());
        if (custEmail.isPresent()) {
            throw new ServiceException("Email already exists");
        }

        Country country = countryRepository.findById(createCustomerDTO.getCountryCode())
                .orElseThrow(() -> new ServiceException("Country not found"));

        CustomerType customerType = customerTypeRepository.findById(createCustomerDTO.getCustypeId())
                .orElseThrow(() -> new ServiceException("Customer type not found"));

        //create customer
        Customer customer = Customer.builder()
                .customerId(String.format("%08d", customerRepository.getCustomerIdSeq()))
                .fullName(createCustomerDTO.getFullName())
                .engName(createCustomerDTO.getEngName())
                .email(createCustomerDTO.getEmail())
                .phone(createCustomerDTO.getPhone())
                .address(createCustomerDTO.getAddress())
                .idType(createCustomerDTO.getIdType())
                .idNumber(createCustomerDTO.getIdNumber())
                .idIssueDate(createCustomerDTO.getIdIssueDate())
                .idIssuePlace(createCustomerDTO.getIdIssuePlace())
                .idExpireDate(createCustomerDTO.getIdExpireDate())
                .recordStat("O")
                .authStat("N")
                .authBy(null)
                .authAt(null)
                .createAt(new Date())
                .createBy(userServiceImpl.getUsernameLogin())
                .idExpireDate(createCustomerDTO.getIdExpireDate())
                .country(country)
                .customerType(customerType)
                .branch(branch)
                .build();

        //validate address
        if (!Extension.isBlankOrNull(createCustomerDTO.getProvinceId())) {
            Optional<Province> province = provinceRepository.findByProvinceIdAndCountry_CountryCode(createCustomerDTO.getProvinceId(), country.getCountryCode());
            if (province.isEmpty()) {
                throw new ServiceException("Province not found or not belong to country");
            }
            customer.setProvince(province.get());
        }
        if (!Extension.isBlankOrNull(createCustomerDTO.getDistrictId()) && !Extension.isBlankOrNull(createCustomerDTO.getProvinceId())) {
            Optional<District> district = districtRepository.findByDistrictIdAndProvince_ProvinceId(createCustomerDTO.getDistrictId(), createCustomerDTO.getProvinceId());
            if (district.isEmpty()) {
                throw new ServiceException("District not found or not belong to province");
            }
            customer.setDistrict(district.get());
        }
        if (!Extension.isBlankOrNull(createCustomerDTO.getWardId()) && !Extension.isBlankOrNull(createCustomerDTO.getDistrictId())) {
            Optional<Ward> ward = wardRepository.findByWardIdAndDistrict_DistrictId(createCustomerDTO.getWardId(), createCustomerDTO.getDistrictId());
            if (ward.isEmpty()) {
                throw new ServiceException("Ward not found or not belong to district");
            }
            customer.setWard(ward.get());
        }

        customerRepository.save(customer);

        return CustomerDTO.fromEntityRefIdOnly(customer);
    }

    @Override
    public CustomerDTO updateCustomer(UpdateCustomerDTO updateCustomerDTO) {
        Customer customer = customerRepository.findById(updateCustomerDTO.getCustomerId())
                .orElseThrow(() -> new ServiceException("Customer type not found"));

        if (updateCustomerDTO.getIdExpireDate().before(new Date())) {
            throw new ServiceException("Expire date must be greater than current date");
        }

        if (updateCustomerDTO.getIdIssueDate().after(new Date())) {
            throw new ServiceException("Issue date must be less than current date");
        }

        //validate
        if (!Extension.isBlankOrNull(updateCustomerDTO.getIdNumber()) && !customer.getIdNumber().equals(updateCustomerDTO.getIdNumber())) {
            Optional<Customer> custIdNum = customerRepository.findByIdNumber(updateCustomerDTO.getIdNumber());
            if (custIdNum.isPresent()) {
                throw new ServiceException("Id number already exists");
            }
            customer.setIdNumber(updateCustomerDTO.getIdNumber());
        }

        if (!Extension.isBlankOrNull(updateCustomerDTO.getPhone()) && !customer.getPhone().equals(updateCustomerDTO.getPhone())) {
            Optional<Customer> custPhone = customerRepository.findByPhone(updateCustomerDTO.getPhone());
            if (custPhone.isPresent()) {
                throw new ServiceException("Phone already exists");
            }
            customer.setPhone(updateCustomerDTO.getPhone());
        }

        if (!Extension.isBlankOrNull(updateCustomerDTO.getEmail()) && !customer.getEmail().equals(updateCustomerDTO.getEmail())) {
            Optional<Customer> custEmail = customerRepository.findByEmail(updateCustomerDTO.getEmail());
            if (custEmail.isPresent()) {
                throw new ServiceException("Email already exists");
            }
            customer.setEmail(updateCustomerDTO.getEmail());
        }

        customer.setFullName(updateCustomerDTO.getFullName());
        customer.setEngName(updateCustomerDTO.getEngName());
        customer.setAddress(updateCustomerDTO.getAddress());
        customer.setIdType(updateCustomerDTO.getIdType());
        customer.setIdIssueDate(updateCustomerDTO.getIdIssueDate());
        customer.setIdIssuePlace(updateCustomerDTO.getIdIssuePlace());
        customer.setIdExpireDate(updateCustomerDTO.getIdExpireDate());
        customer.setUpdateBy(userServiceImpl.getUsernameLogin());
        customer.setUpdateAt(new Date());
        customer.setAuthStat("N");

        //validate address
        if (!Extension.isBlankOrNull(updateCustomerDTO.getCountryCode()) && !updateCustomerDTO.getCountryCode().equals(customer.getCountry().getCountryCode())) {
            Country country = countryRepository.findById(updateCustomerDTO.getCountryCode())
                    .orElseThrow(() -> new ServiceException("Country not found"));
            customer.setCountry(country);
        }
        if (!Extension.isBlankOrNull(updateCustomerDTO.getProvinceId()) && !updateCustomerDTO.getProvinceId().equals(customer.getProvince() == null ? null : customer.getProvince().getProvinceId())) {
            Optional<Province> province = provinceRepository.findByProvinceIdAndCountry_CountryCode(updateCustomerDTO.getProvinceId(), customer.getCountry().getCountryCode());
            if (province.isEmpty()) {
                throw new ServiceException("Province not found or not belong to country");
            }
            customer.setProvince(province.get());
        }
        if (!Extension.isBlankOrNull(updateCustomerDTO.getDistrictId()) && customer.getProvince() != null && !updateCustomerDTO.getDistrictId().equals(customer.getDistrict() == null ? null : customer.getDistrict().getDistrictId())) {
            Optional<District> district = districtRepository.findByDistrictIdAndProvince_ProvinceId(updateCustomerDTO.getDistrictId(), customer.getProvince().getProvinceId());
            if (district.isEmpty()) {
                throw new ServiceException("District not found or not belong to province");
            }
            customer.setDistrict(district.get());
        }
        if (!Extension.isBlankOrNull(updateCustomerDTO.getWardId()) && customer.getDistrict() != null && !updateCustomerDTO.getWardId().equals(customer.getWard() == null ? null : customer.getWard().getWardId())) {
            Optional<Ward> ward = wardRepository.findByWardIdAndDistrict_DistrictId(updateCustomerDTO.getWardId(), updateCustomerDTO.getDistrictId());
            if (ward.isEmpty()) {
                throw new ServiceException("Ward not found or not belong to district");
            }
            customer.setWard(ward.get());
        }

        customerRepository.save(customer);

        return CustomerDTO.fromEntityRefIdOnly(customer);
    }

    @Override
    public CustomerDTO deleteCustomer(DeleteCustomerDTO deleteCustomerDTO) {
        Customer customer = customerRepository.findById(deleteCustomerDTO.getCustomerId())
                .orElseThrow(() -> new ServiceException("Customer type not found"));

        List<String> accounts = accountRepository.findAccountByCustomerIdAndRecordStatNotEquals(deleteCustomerDTO.getCustomerId(), "C");
        if (!accounts.isEmpty()) {
            throw new ServiceException("Customer has account. Please close all account before close customer");
        }
        customer.setRecordStat("C");
        customer.setUpdateAt(new Date());
        customer.setUpdateBy(userServiceImpl.getUsernameLogin());
        customer.setAuthStat("N");
        customerRepository.save(customer);
        return CustomerDTO.fromEntityRefIdOnly(customer);
    }

    @Override
    public Boolean checkCustomer(CheckedCustomerDTO checkedCustomerDTO) {
        Customer customer = customerRepository.findById(checkedCustomerDTO.getCustomerId())
                .orElseThrow(() -> new ServiceException("Customer id not found"));
        if (customer.getAuthStat().equals("A")) {
            throw new ServiceException("Customer already checked");
        }
        customer.setAuthBy(userServiceImpl.getUsernameLogin());
        customer.setAuthAt(new Date());
        customer.setAuthStat("A");
        customerRepository.save(customer);
        return true;
    }

    @Override
    public ListWrapper<CustomerDTO> search(ParameterSearchCustomer parameterSeachCustomer) {
        ListWrapper<Customer> customers = customerRepository.searchCustomer(parameterSeachCustomer);
        return ListWrapper.<CustomerDTO>builder()
                .total(customers.getTotal())
                .totalPage(customers.getTotalPage())
                .pageSize(customers.getPageSize())
                .page(customers.getPage())
                .data(customers.getData().stream().map(CustomerDTO::fromSearchCustomer).collect(Collectors.toList()))
                .build();
    }
}
