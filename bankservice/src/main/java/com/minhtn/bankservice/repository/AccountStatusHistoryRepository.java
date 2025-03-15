package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.AccountStatusHistory;
import com.minhtn.bankservice.entity.AccountStatusHistoryPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStatusHistoryRepository extends JpaRepository<AccountStatusHistory, AccountStatusHistoryPK> {
}
