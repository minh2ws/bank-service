package com.minhtn.bankservice.controller;

import com.minhtn.bankservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    protected UserService userService;
}
