package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, String> {
    Optional<District> findByDistrictIdAndProvince_ProvinceId(String districtId, String provinceProvinceId);
}
