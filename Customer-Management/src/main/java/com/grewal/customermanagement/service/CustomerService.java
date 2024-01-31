package com.grewal.customermanagement.service;

import com.grewal.customermanagement.dto.CustomerDto;
import com.grewal.customermanagement.dto.CustomerResponse;
import com.grewal.customermanagement.model.Customer;
import com.grewal.customermanagement.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;


    public Customer createCustomer(CustomerDto customerDto) {
        Customer customer = Customer.builder()
                .firstName(customerDto.getFirstName())
                .lastName(customerDto.getLastName())
                .phone(customerDto.getPhone())
                .state(customerDto.getState())
                .city(customerDto.getCity())
                .email(customerDto.getEmail())
                .address(customerDto.getAddress())
                .street(customerDto.getStreet())
                .build();
      return   customerRepository.save(customer);
    }

    public Customer getCustomer(String CustomerId)
    {
        return customerRepository.findById(CustomerId).orElse(null);
    }
    public Customer updateCustomer(String customerId,CustomerDto customerDto)
    {
        Optional<Customer> existingCustomer =customerRepository.findById(customerId);
        if(existingCustomer.isPresent())
        {
            Customer customer = Customer.builder()
                    .id(customerId)
                    .firstName(customerDto.getFirstName())
                    .lastName(customerDto.getLastName())
                    .phone(customerDto.getPhone())
                    .state(customerDto.getState())
                    .city(customerDto.getCity())
                    .email(customerDto.getEmail())
                    .address(customerDto.getAddress())
                    .street(customerDto.getStreet())
                    .build();
            return customerRepository.save(customer);
        }
        else{
            return null;
        }

    }

    public boolean deleteCustomer(String customerId)
    {
        if(customerRepository.existsById(customerId))
        {
            customerRepository.deleteById(customerId);
            return true;
        }
        else{
            return false;
        }

    }


    public List<Customer> getAllCustomerList(int page, int size)
    {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Customer> page1=customerRepository.findAll(pageRequest);
        return page1.getContent();
    }

    public List<Customer> getAllCustomer(int page,int size,String searchCriteria, String searchValue)
    {
        PageRequest pageRequest = PageRequest.of(page, size);

        Customer exampleCustomer = new Customer();

        switch (searchCriteria.toLowerCase()) {
            case "id" -> exampleCustomer.setId(searchValue);
            case "firstname" -> exampleCustomer.setFirstName(searchValue);
            case "lastname" -> exampleCustomer.setLastName(searchValue);
            case "city" -> exampleCustomer.setCity(searchValue);
            case "email" -> exampleCustomer.setEmail(searchValue);
            case "address" -> exampleCustomer.setAddress(searchValue);
            case "phone" -> exampleCustomer.setPhone(searchValue);
            case "state" -> exampleCustomer.setState(searchValue);
            case "street" -> exampleCustomer.setStreet(searchValue);
            default -> {
                return null;
            }
        }



        Example<Customer> example = Example.of(exampleCustomer);

        Page<Customer> customerPage = customerRepository.findAll(example, pageRequest);

        return customerPage.getContent();
    }


    public void syncCustomerInData(List<CustomerResponse> list)
    {
        for(CustomerResponse a:list)
        {
            System.out.println(a);
            Optional<Customer> existing = customerRepository.findById(a.getUuid());
            if(existing.isPresent())
            {
                CustomerDto ex= CustomerDto.builder()
                        .firstName(a.getFirst_name())
                        .lastName(a.getLast_name())
                                .phone(a.getPhone()).email(a.getEmail())
                                .street(a.getStreet()).city(a.getCity())
                                .state(a.getState()).address(a.getAddress())
                                .build();
                this.updateCustomer(a.getUuid(),ex);
            }
            else{

                Customer nw = Customer.builder().id(a.getUuid())
                        .firstName(a.getFirst_name())
                        .lastName(a.getLast_name())
                        .phone(a.getPhone()).email(a.getEmail())
                        .street(a.getStreet()).city(a.getCity())
                        .state(a.getState()).address(a.getAddress())
                        .build();
                customerRepository.save(nw);

            }
        }
    }
}
