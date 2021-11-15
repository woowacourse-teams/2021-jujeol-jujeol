package com.jujeol.performance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class LogFormDto {

    private String targetApi;
    private String targetMethod;
    @JsonProperty("requestTime")
    private Long requestTimeMils;
    private Long queryCounts;
    @JsonProperty("queryTime")
    private Long queryTimeMils;

    public static LogFormDto from(PerformanceLoggingForm logForm) {
        final LogFormDto logFormDto = new LogFormDto();
        logFormDto.targetApi = logForm.getTargetApi();
        logFormDto.targetMethod = logForm.getTargetMethod();
        logFormDto.requestTimeMils = logForm.getRequestTime();
        logFormDto.queryCounts = logForm.getQueryCounts();
        logFormDto.queryTimeMils = logForm.getQueryTime();
        return logFormDto;
    }
}
