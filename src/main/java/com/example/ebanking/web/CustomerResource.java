package com.example.ebanking.web;

import com.example.ebanking.dtos.CustomerDTO;
import com.example.ebanking.exception.CustomerNotFoundException;
import com.example.ebanking.services.BankAccountServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers/")
@AllArgsConstructor
@Slf4j
public class CustomerResource {
    private BankAccountServiceImpl bankAccountService;

    @GetMapping("index")
    public List<CustomerDTO> index() {
        return this.bankAccountService.getAllCustomer();
    }

    @GetMapping("get/{id}")
    public CustomerDTO getCustomer(@PathVariable("id") Long id) throws CustomerNotFoundException {
        return this.bankAccountService.getCustomerById(id);
    }

    @PostMapping("save")
    public CustomerDTO save(@RequestBody CustomerDTO customerDTO) {
        return this.bankAccountService.saveCustomer(customerDTO);
    }
}
