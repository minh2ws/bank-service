package com.minhtn.bankservice.controller;

import com.minhtn.bankservice.model.search.ParameterReportCustByLocation;
import com.minhtn.bankservice.model.wrapper.ObjectResponseWrapper;
import com.minhtn.bankservice.ultility.Constant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(Constant.REPORT_SERVICE_URL)
@Tag(name = "Report API")
@Slf4j
public class ReportController extends BaseController {
    @Operation(summary = "Lấy số lượng khách hàng theo địa điểm", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = ObjectResponseWrapper.class)))
    })
    @GetMapping("/report-cust-by-location")
    public ObjectResponseWrapper reportCustByLocation(@RequestParam(value = "countryCode", required = false) String countryCode,
                                            @RequestParam(value = "provinceId", required = false) String provinceId,
                                            @RequestParam(value = "wardId", required = false) String wardId,
                                            @RequestParam(value = "districtId", required = false) String districtId) {
        ParameterReportCustByLocation parameterReportCustByLocation = ParameterReportCustByLocation.builder()
                .countryCode(countryCode)
                .provinceId(provinceId)
                .wardId(wardId)
                .districtId(districtId)
                .build();

        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(reportService.reportCustByLocation(parameterReportCustByLocation))
                .build();
    }

    @Operation(summary = "Thống kê số lượng tài khoản và số lượng giao dịch", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = ObjectResponseWrapper.class)))
    })
    @GetMapping("/report-act-trans-by-range")
    public ObjectResponseWrapper reportActTransByRange(@RequestParam(value = "minRange", required = false) BigDecimal minRange,
                                            @RequestParam(value = "maxRange", required = false) BigDecimal maxRange) {
        return ObjectResponseWrapper.builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(reportService.reportAcctTransByRange(maxRange, minRange))
                .build();
    }
}
