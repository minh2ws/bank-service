package com.minhtn.bankservice.service.impl;

import com.minhtn.bankservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService {

    // repository
    @Autowired
    protected UserRepository userRepository;
}
