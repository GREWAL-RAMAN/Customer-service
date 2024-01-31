package com.grewal.customermanagement.controller;


import com.grewal.customermanagement.dto.CustomerDto;
import com.grewal.customermanagement.model.Customer;
import com.grewal.customermanagement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class CustomerController {



    private final CustomerService customerService;


    Logger logger = LoggerFactory.getLogger(CustomerController.class);




    @PostMapping("api/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDto customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    // Update a customer
    @PutMapping("api/customers/{customerId}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable String customerId,
            @RequestBody CustomerDto updatedCustomer
    ) {
        Customer customer = customerService.updateCustomer(customerId, updatedCustomer);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Get a list of customers with pagination, sorting, and searching
    @GetMapping("api/customers")
    public ResponseEntity<List<Customer>> getCustomers(
            @RequestParam(name ="p" ,defaultValue = "0") int page,
            @RequestParam(name="s",defaultValue = "10") int size,
            @RequestParam(name = "c",defaultValue = "id") String sort,
            @RequestParam(name="v",defaultValue = "*") String search
    ) {
        if(search.equals("*"))
        {
            List<Customer> customers = customerService.getAllCustomerList(page, size);
            return new ResponseEntity<>(customers, HttpStatus.OK);
        }
        else{
        List<Customer> customers = customerService.getAllCustomer(page, size, sort, search);
        return new ResponseEntity<>(customers, HttpStatus.OK);}
    }

    // Get a single customer based on ID
    @GetMapping("api/customers/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable String customerId) {
        Customer customer = customerService.getCustomer(customerId);
        if (customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a customer
    @DeleteMapping("api/customers/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId) {
        if (customerService.deleteCustomer(customerId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }






}
