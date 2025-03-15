package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatusRepository extends JpaRepository<AccountStatus, String> {
}
