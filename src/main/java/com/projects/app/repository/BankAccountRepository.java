package com.projects.app.repository;

import com.projects.app.models.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    @Query(value = "select temp from BankAccount temp where temp.accountNumber = ?1")
    List<BankAccount> findBankAccountByAccountNumber(String bankAccountNumber);
}
