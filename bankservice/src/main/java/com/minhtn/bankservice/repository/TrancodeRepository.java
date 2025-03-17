package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.Trancode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrancodeRepository extends JpaRepository<Trancode, String> {
}
