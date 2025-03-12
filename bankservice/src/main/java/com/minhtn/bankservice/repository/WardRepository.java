package com.minhtn.bankservice.repository;

import com.minhtn.bankservice.entity.Ward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WardRepository extends JpaRepository<Ward, String> {
    Optional<Ward> findByWardIdAndDistrict_DistrictId(String wardId, String districtDistrictId);
}