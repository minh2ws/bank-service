package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountType, String> {
}
