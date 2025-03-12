package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerTypeRepository extends JpaRepository<CustomerType, String> {
}
