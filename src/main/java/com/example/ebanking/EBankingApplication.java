package com.example.ebanking;

import com.example.ebanking.entities.*;
import com.example.ebanking.enums.AccountStatus;
import com.example.ebanking.enums.OperationType;
import com.example.ebanking.exception.BalanceNotSufficientException;
import com.example.ebanking.exception.BankAccountNotFoundException;
import com.example.ebanking.exception.CustomerNotFoundException;
import com.example.ebanking.repositories.AccountOperationRepository;
import com.example.ebanking.repositories.BankAccountRepository;
import com.example.ebanking.repositories.CustomerRepository;
import com.example.ebanking.services.BankAccountServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingApplication.class, args);
    }

    // @Bean
//    CommandLineRunner commandLineRunner(BankAccountServiceImpl bankAccountService) {
//        return args -> {
//            Stream.of("Hassan", "Imane", "Mohamed").forEach(name -> {
//                Customer customer = new Customer();
//                customer.setName(name);
//                customer.setEmail(name + "@gmail.com");
//                bankAccountService.saveCustomer(customer);
//            });
//
//            bankAccountService.getAllCustomer().forEach(customer -> {
//                try {
//                    bankAccountService.saveCurrentBankAccount(Math.random() * 5000, 9000, customer.getId());
//                    bankAccountService.saveSavingBankAccount(Math.random() * 7000, 5.5, customer.getId());
//
//                    List<BankAccount> bankAccounts = bankAccountService.getAllBankAccount();
//                    for (BankAccount bankAccount : bankAccounts) {
//                        for (int i = 0; i < 10; i++) {
//                            bankAccountService.credit(bankAccount.getId(), 10000 + Math.random(), "Credit");
//                            bankAccountService.debit(bankAccount.getId(), 10000 + Math.random(), "Debit");
//                        }
//                    }
//                }
//                catch (CustomerNotFoundException e) {
//                    e.printStackTrace();
//                }
//                catch (BankAccountNotFoundException | BalanceNotSufficientException e) {
//                    e.printStackTrace();
//                }
//            });
//        };
//    }

    // @Bean
//    CommandLineRunner start(
//            CustomerRepository customerRepository,
//            BankAccountRepository bankAccountRepository,
//            AccountOperationRepository accountOperationRepository
//    ) {
//        return args -> {
//            // list of person we will edit person one by one
//            Stream.of("Yassine", "Ahmed", "Imad").forEach(name -> {
//                // create customer
//                Customer customer = new Customer();
//                customer.setName(name);
//                customer.setEmail(name + "@gmail.com");
//                // save customer in database
//                customerRepository.save(customer);
//
//                // create current account for customer
//                CurrentAccount currentAccount = new CurrentAccount();
//                currentAccount.setId(UUID.randomUUID().toString());
//                currentAccount.setBalance(Math.random() * 50000);
//                currentAccount.setCreateDate(new Date());
//                currentAccount.setCustomer(customer);
//                currentAccount.setStatus(AccountStatus.CREATED);
//                currentAccount.setOverDraft(8500);
//                // save current account in database
//                bankAccountRepository.save(currentAccount);
//
//                // create saving account for customer
//                SavingAccount savingAccount = new SavingAccount();
//                savingAccount.setId(UUID.randomUUID().toString());
//                savingAccount.setBalance(Math.random() * 50000);
//                savingAccount.setCreateDate(new Date());
//                savingAccount.setCustomer(customer);
//                savingAccount.setStatus(AccountStatus.CREATED);
//                savingAccount.setInterestRate(5.5);
//                // save current account in database
//                bankAccountRepository.save(savingAccount);
//            });
//
//            // get all bank account from database
//            bankAccountRepository.findAll().forEach(bankAccount -> {
//                for (int i = 0; i < 10; i++) {
//                    // create operation
//                    AccountOperation accountOperation = new AccountOperation();
//                    accountOperation.setOperationDate(new Date());
//                    accountOperation.setAmount(Math.random() * 10000);
//                    accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
//                    accountOperation.setBankAccount(bankAccount);
//                    // save operation in database
//                    accountOperationRepository.save(accountOperation);
//                }
//            });
//        };
//    }
}
