package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String>, CustomerRepositoryCustom {

    Optional<Customer> findByIdNumber(String idNumber);

    Optional<Customer> findByPhone(String phone);

    Optional<Customer> findByEmail(String email);

    @Query(value = "SELECT NEXTVAL('customer_id_seq')", nativeQuery = true)
    Long getCustomerIdSeq();
}
