package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.Country;
import com.minhtn.bankservice.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province, String> {
    Optional<Province> findByProvinceIdAndCountry_CountryCode(String provinceId, String countryCountryCode);
}
