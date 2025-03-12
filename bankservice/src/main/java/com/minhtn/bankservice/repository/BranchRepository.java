package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, String> {
}
