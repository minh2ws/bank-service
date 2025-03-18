package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AccountRepository extends JpaRepository<Account, String>, AccountRepositoryCustom {

    @Query(value = "select a.accountId from Account a " +
            "where a.customer.customerId = ?1 " +
            "and a.accountStatus.accountStatusId != ?2")
    List<String> findAccountByCustomerIdAndRecordStatNotEquals(String customerId, String acctStat);

    @Query(value = "select max(a.accountId) from Account a where a.customer.customerId = ?1")
    String findMaxAccountIdByCustomerId(String customerId);

    @Query(value = "select a.account_id from public.account a where a.account_id = ?1 for update", nativeQuery = true)
    void lockRecordAccount(String accountId);
}
