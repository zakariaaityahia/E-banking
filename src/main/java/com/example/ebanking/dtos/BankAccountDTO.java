package com.example.ebanking.dtos;

import com.example.ebanking.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BankAccountDTO {
    private String id;
    private double balance;
    private Date createDate;
    private AccountStatus status;
//    private CustomerDTO customer;
    private List<AccountOperationDTO> accountOperations;
}
