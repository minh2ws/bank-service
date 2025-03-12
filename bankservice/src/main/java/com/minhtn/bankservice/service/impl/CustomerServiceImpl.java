package com.minhtn.bankservice.service.impl;

import com.minhtn.bankservice.entity.*;
import com.minhtn.bankservice.handler.ServiceException;
import com.minhtn.bankservice.model.customer.CheckedCustomerDTO;
import com.minhtn.bankservice.model.customer.CreateCustomerDTO;
import com.minhtn.bankservice.model.customer.CustomerDTO;
import com.minhtn.bankservice.model.customer.UpdateCustomerDTO;
import com.minhtn.bankservice.model.search.ParameterSearchCustomer;
import com.minhtn.bankservice.model.wrapper.ListWrapper;
import com.minhtn.bankservice.service.CustomerService;
import com.minhtn.bankservice.ultility.Extension;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Check;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl extends BaseService implements CustomerService {
    @Override
    public CustomerDTO createCustomer(CreateCustomerDTO createCustomerDTO) {

        if (createCustomerDTO.getIdExpireDate().before(new Date())) {
            throw new ServiceException("Expire date must be greater than current date");
        }

        if (createCustomerDTO.getIdIssueDate().after(new Date())) {
            throw new ServiceException("Issue date must be less than current date");
        }

        //validate
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

        User user = userRepository.findById(createCustomerDTO.getCreateBy())
                .orElseThrow(() -> new ServiceException("User not found"));

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
                .createBy(user.getUsername())
                .idExpireDate(createCustomerDTO.getIdExpireDate())
                .country(country)
                .customerType(customerType)
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

        User user = userRepository.findById(updateCustomerDTO.getUpdateBy())
                .orElseThrow(() -> new ServiceException("User not found"));

        customer.setFullName(updateCustomerDTO.getFullName());
        customer.setEngName(updateCustomerDTO.getEngName());
        customer.setAddress(updateCustomerDTO.getAddress());
        customer.setIdType(updateCustomerDTO.getIdType());
        customer.setIdIssueDate(updateCustomerDTO.getIdIssueDate());
        customer.setIdIssuePlace(updateCustomerDTO.getIdIssuePlace());
        customer.setIdExpireDate(updateCustomerDTO.getIdExpireDate());
        customer.setUpdateBy(user.getUsername());
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
    public CustomerDTO deleteCustomer(String customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ServiceException("Customer type not found"));

        List<String> accounts = accountRepository.findAccountByCustomerIdAndRecordStatNotEquals(customerId, "C");
        if (!accounts.isEmpty()) {
            throw new ServiceException("Customer has account. Please close all account before close customer");
        }
        customer.setRecordStat("C");
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
        User user = userRepository.findById(checkedCustomerDTO.getAuthBy())
                .orElseThrow(() -> new ServiceException("User not found"));
        customer.setAuthBy(user.getUsername());
        customer.setAuthAt(new Date());
        customer.setAuthStat("A");
        customerRepository.save(customer);
        return true;
    }

    @Override
    public CustomerDTO getCustomerById(String customerId) {
        return null;
    }

    @Override
    public ListWrapper<CustomerDTO> search(ParameterSearchCustomer parameterSeachCustomer) {
        return null;
    }
}
