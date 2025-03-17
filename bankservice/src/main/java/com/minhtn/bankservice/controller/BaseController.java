package com.minhtn.bankservice.controller;

import com.minhtn.bankservice.service.AccountService;
import com.minhtn.bankservice.service.CustomerService;
import com.minhtn.bankservice.service.TransactionService;
import com.minhtn.bankservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

    //service
    @Autowired
    protected UserService userService;
    @Autowired
    protected CustomerService customerService;
    @Autowired
    protected AccountService accountService;
    @Autowired
    protected TransactionService transactionService;
}
