package com.minhtn.bankservice.model.customer;

import com.minhtn.bankservice.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private String customerId;
    private String fullName;
    private String engName;
    private String email;
    private String phone;
    private String address;
    private String idType;
    private String idNumber;
    private Date idIssueDate;
    private String idIssuePlace;
    private Date idExpireDate;
    private String createBy;
    private Date createAt;
    private String recordStat;
    private String authStat;
    private String authBy;
    private Date authAt;
    private Date updateAt;
    private String updateBy;
    private String countryCode;
    private String countryName;
    private String provinceId;
    private String provinceName;
    private String districtId;
    private String districtName;
    private String wardId;
    private String wardName;
    private String custypeId;
    private String custypeName;

    public static CustomerDTO fromEntity(Customer customer) {
        return CustomerDTO.builder()
                .customerId(customer.getCustomerId())
                .fullName(customer.getFullName())
                .engName(customer.getEngName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .idType(customer.getIdType())
                .idNumber(customer.getIdNumber().trim())
                .idIssueDate(customer.getIdIssueDate())
                .idIssuePlace(customer.getIdIssuePlace())
                .idExpireDate(customer.getIdExpireDate())
                .createBy(customer.getCreateBy())
                .createAt(customer.getCreateAt())
                .recordStat(customer.getRecordStat())
                .authStat(customer.getAuthStat())
                .authBy(customer.getAuthBy())
                .authAt(customer.getAuthAt())
                .updateAt(customer.getUpdateAt())
                .updateBy(customer.getUpdateBy())
                .countryCode(customer.getCountry().getCountryCode().trim())
                .countryName(customer.getCountry().getName())
                .provinceId(customer.getProvince().getProvinceId().trim())
                .provinceName(customer.getProvince().getName())
                .districtId(customer.getDistrict().getDistrictId().trim())
                .districtName(customer.getDistrict().getName())
                .wardId(customer.getWard().getWardId().trim())
                .wardName(customer.getWard().getName())
                .custypeId(customer.getCustomerType().getCustomerTypeId().trim())
                .custypeName(customer.getCustomerType().getName())
                .build();
    }

    public static CustomerDTO fromEntityRefIdOnly(Customer customer) {
        return CustomerDTO.builder()
                .customerId(customer.getCustomerId())
                .fullName(customer.getFullName())
                .engName(customer.getEngName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .idType(customer.getIdType())
                .idNumber(customer.getIdNumber().trim())
                .idIssueDate(customer.getIdIssueDate())
                .idIssuePlace(customer.getIdIssuePlace())
                .idExpireDate(customer.getIdExpireDate())
                .createBy(customer.getCreateBy().trim())
                .createAt(customer.getCreateAt())
                .recordStat(customer.getRecordStat())
                .authStat(customer.getAuthStat())
                .authBy(customer.getAuthBy())
                .authAt(customer.getAuthAt())
                .updateAt(customer.getUpdateAt())
                .updateBy(customer.getUpdateBy())
                .countryCode(customer.getCountry().getCountryCode())
                .provinceId(customer.getProvince() == null ? null : customer.getProvince().getProvinceId().trim())
                .districtId(customer.getDistrict() == null ? null : customer.getDistrict().getDistrictId().trim())
                .wardId(customer.getWard() == null ? null : customer.getWard().getWardId().trim())
                .custypeId(customer.getCustomerType().getCustomerTypeId().trim())
                .build();
    }
}
