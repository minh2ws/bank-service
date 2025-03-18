package com.minhtn.bankservice.controller;

import com.minhtn.bankservice.service.*;
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
    @Autowired
    protected ReportService reportService;
}
