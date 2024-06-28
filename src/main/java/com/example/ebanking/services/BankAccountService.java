package com.example.ebanking.services;

import com.example.ebanking.dtos.CustomerDTO;
import com.example.ebanking.entities.BankAccount;
import com.example.ebanking.entities.CurrentAccount;
import com.example.ebanking.entities.Customer;
import com.example.ebanking.entities.SavingAccount;
import com.example.ebanking.exception.BalanceNotSufficientException;
import com.example.ebanking.exception.BalanceNotSufficientException;
import com.example.ebanking.exception.BankAccountNotFoundException;
import com.example.ebanking.exception.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customer);
    List<CustomerDTO> getAllCustomer();
    CustomerDTO getCustomerById(Long id) throws CustomerNotFoundException;
    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    BankAccount getBankAccountById(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
    List<BankAccount> getAllBankAccount();
}
