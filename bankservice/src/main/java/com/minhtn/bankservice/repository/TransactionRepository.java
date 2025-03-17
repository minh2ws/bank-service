package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, String>, TransactionRepositoryCustom
{
    @Query(value = "SELECT NEXTVAL('tran_id_seq')", nativeQuery = true)
    Long getTranIdSeq();

    @Query(value = "SELECT NEXTVAL('trannum_seq')", nativeQuery = true)
    Long getTranNumSeq();
}
