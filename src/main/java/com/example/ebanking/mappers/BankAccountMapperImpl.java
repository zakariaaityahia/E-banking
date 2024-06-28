package com.example.ebanking.mappers;

import com.example.ebanking.dtos.*;
import com.example.ebanking.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if (customer.getBankAccounts() != null) {
            List<BankAccountDTO> bankAccountDTOS = new ArrayList<>();
            customer.getBankAccounts().forEach(bankAccount -> {
                BankAccountDTO bankAccountDTO = this.fromBankAccount(bankAccount);
                bankAccountDTOS.add(bankAccountDTO);
            });
            customerDTO.setBankAccounts(bankAccountDTOS);
        }
        return customerDTO;
    }
    public Customer fromCustomerDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
//        if(customerDTO.getBankAccounts() != null) {
//            List<BankAccount> bankAccounts = new ArrayList<>();
//            customerDTO.getBankAccounts().forEach(bankAccountDTO -> {
//                BankAccount bankAccount = this.fromBankAccountDTO(bankAccountDTO);
//                bankAccounts.add(bankAccount);
//            });
//            customer.setBankAccounts(bankAccounts);
//        }
        return customer;
    }

    public BankAccountDTO fromBankAccount(BankAccount bankAccount) {
        BankAccountDTO bankAccountDTO;
        if(bankAccount instanceof CurrentAccount) {
            bankAccountDTO = new CurrentAccountDTO();
        }
        else {
            bankAccountDTO = new SavingAccountDTO();
        }
        BeanUtils.copyProperties(bankAccount, bankAccountDTO);
        if (bankAccount.getAccountOperations() != null) {
            List<AccountOperationDTO> accountOperationDTOS = new ArrayList<>();
            bankAccount.getAccountOperations().forEach(accountOperation -> {
                AccountOperationDTO accountOperationDTO = this.fromAccountOperation(accountOperation);
                accountOperationDTOS.add(accountOperationDTO);
            });
            bankAccountDTO.setAccountOperations(accountOperationDTOS);
        }
        return bankAccountDTO;
    }
    public BankAccount fromBankAccountDTO(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount;
        if(bankAccountDTO instanceof CurrentAccountDTO) {
            bankAccount = new CurrentAccount();
        }
        else {
            bankAccount = new SavingAccount();
        }
        BeanUtils.copyProperties(bankAccountDTO, bankAccount);
        if (bankAccountDTO.getAccountOperations() != null) {
            List<AccountOperation> accountOperations = new ArrayList<>();
            bankAccountDTO.getAccountOperations().forEach(accountOperationDTO -> {
                AccountOperation accountOperation = this.fromAccountOperationDTO(accountOperationDTO);
                accountOperations.add(accountOperation);
            });
            bankAccount.setAccountOperations(accountOperations);
        }
        return bankAccount;
    }

    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
        return accountOperationDTO;
    }
    public AccountOperation fromAccountOperationDTO(AccountOperationDTO accountOperationDTO) {
        AccountOperation accountOperation = new AccountOperation();
        BeanUtils.copyProperties(accountOperationDTO, accountOperation);
        return accountOperation;
    }

}
