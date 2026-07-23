package com.csc340.Swap_A_Bookaroo.uicontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountUiController {

    @GetMapping("/provider-login")
    public String providerLogin() {
        return "provider-login";
    }

    @GetMapping("/customer-login")
    public String customerLogin() {
        return "customer-login";
    }
}