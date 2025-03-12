package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AccountRepository extends JpaRepository<Account, String> {

    @Query(value = "select a.accountId from Account a " +
            "where a.customer.customerId = ?1 " +
            "and a.recordStat != ?2")
    List<String> findAccountByCustomerIdAndRecordStatNotEquals(String customerId, String recordStat);
}
