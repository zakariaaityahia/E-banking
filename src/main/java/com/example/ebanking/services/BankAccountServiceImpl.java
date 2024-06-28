package com.example.ebanking.services;

import com.example.ebanking.dtos.CustomerDTO;
import com.example.ebanking.entities.*;
import com.example.ebanking.enums.OperationType;
import com.example.ebanking.exception.BalanceNotSufficientException;
import com.example.ebanking.exception.BankAccountNotFoundException;
import com.example.ebanking.exception.CustomerNotFoundException;
import com.example.ebanking.mappers.BankAccountMapperImpl;
import com.example.ebanking.repositories.AccountOperationRepository;
import com.example.ebanking.repositories.BankAccountRepository;
import com.example.ebanking.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl mapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = this.mapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = this.customerRepository.save(customer);
        CustomerDTO savedCustomerDTO = this.mapper.fromCustomer(savedCustomer);
        log.info("saving customer");
        return savedCustomerDTO;
    }

    @Override
    public List<CustomerDTO> getAllCustomer() {
        List<Customer> customers = this.customerRepository.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer customer: customers) {
            // convert customer from entity to dto
            CustomerDTO customerDTO = mapper.fromCustomer(customer);
            // add customer dto to list
            customerDTOS.add(customerDTO);
        }
        log.info("get list of customer");
        return customerDTOS;
    }

    @Override
    public CustomerDTO getCustomerById(Long id) throws CustomerNotFoundException {
        Customer customer = this.customerRepository.findById(id).orElse(null);
        // check if customer not found
        if(customer == null) {
            throw new CustomerNotFoundException("customer not found");
        }
        CustomerDTO customerDTO = this.mapper.fromCustomer(customer);
        log.info("get customer by id");
        return customerDTO;
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        // check if there are customer with id equal customerId
        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        if(customer == null) {
            log.error("customer not found in save current bank account");
            throw new CustomerNotFoundException("Customer not found");
        }

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreateDate(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        log.info("saving current bank account");
        return this.bankAccountRepository.save(currentAccount);
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        // check if there are customer with id equal customerId
        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        if(customer == null) {
            log.error("customer not found in save saving bank account");
            throw new CustomerNotFoundException("Customer not found");
        }

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreateDate(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        log.info("saving saving bank account");
        return this.bankAccountRepository.save(savingAccount);
    }


    @Override
    public BankAccount getBankAccountById(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = this.bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount == null) {
            log.error("bank account not found");
            throw new BankAccountNotFoundException("Bank Account not Found");
        }
        log.info("get bank account by id " + accountId);
        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        // check if there are bank account with id = accountId
        BankAccount bankAccount = this.getBankAccountById(accountId);
        if(bankAccount.getBalance() < amount) {
            log.error("balance not sufficient in debit method");
            throw new BalanceNotSufficientException("Balance not sufficient");
        }
        // create account operation
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);

        // save operation in database
        this.accountOperationRepository.save(accountOperation);

        // update balance of bank account
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        // save modification of bank account in database
        this.bankAccountRepository.save(bankAccount);
        log.info("saving operation in debit method");
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
        // check if there are bank account with id = accountId
        BankAccount bankAccount = this.getBankAccountById(accountId);
        // create account operation
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);

        // save operation in database
        this.accountOperationRepository.save(accountOperation);

        // update balance of bank account
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        // save modification of bank account in database
        this.bankAccountRepository.save(bankAccount);
        log.info("saving operation in debit method");
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.debit(accountIdSource, amount, "Transfer to " + accountIdDestination);
        this.credit(accountIdDestination, amount, "Transfer from " + accountIdSource);
    }

    @Override
    public List<BankAccount> getAllBankAccount() {
        return this.bankAccountRepository.findAll();
    }
}
