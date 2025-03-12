package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, String> {
}
