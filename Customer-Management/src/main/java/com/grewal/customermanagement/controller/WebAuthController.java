package com.grewal.customermanagement.controller;

import com.grewal.customermanagement.dto.CustomerResponse;
import com.grewal.customermanagement.model.Customer;
import com.grewal.customermanagement.service.CustomerService;
import com.grewal.customermanagement.service.SyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/web")
@RequiredArgsConstructor
public class WebAuthController {

    private final CustomerService customerService;

    private final SyncService syncService;


    @GetMapping("/login")
    public String getLogin()

    {
        return "login";
    }

    @GetMapping("/home")
    public String home()
    {
        return "/home";
    }
    @GetMapping("/add")
    public String add()
    {
        return "/CustomerForm";
    }

    @GetMapping("/update/{customerId}")
    public String update(Model model, @PathVariable String customerId
                         )
    {

        Customer updatedCustomer =customerService.getCustomer(customerId);
        model.addAttribute("CustomerDetails",updatedCustomer);

        return "/updateForm";
    }

    @GetMapping("/sync")
    public String SyncCustomers()
    {
        String token = syncService.authenticateAndGetToken();
        System.out.println(token);

        List<CustomerResponse> customerResponses=syncService.syncCustomers(token);
        customerService.syncCustomerInData(customerResponses);
        return "redirect:/web/home";
    }

}
