package com.minhtn.bankservice.model.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParameterReportCustByLocation {
    private String countryCode;
    private String provinceId;
    private String wardId;
    private String districtId;
}
