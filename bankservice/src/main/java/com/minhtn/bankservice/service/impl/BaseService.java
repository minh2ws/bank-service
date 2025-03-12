package com.minhtn.bankservice.service.impl;

import com.minhtn.bankservice.repository.*;
import com.minhtn.bankservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService {

    // repository
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected ProvinceRepository provinceRepository;
    @Autowired
    protected CountryRepository countryRepository;
    @Autowired
    protected WardRepository wardRepository;
    @Autowired
    protected DistrictRepository districtRepository;
    @Autowired
    protected CustomerTypeRepository customerTypeRepository;
    @Autowired
    protected AccountRepository accountRepository;

}
