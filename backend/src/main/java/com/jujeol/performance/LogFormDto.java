package com.jujeol.performance;

public class LogFormDto {

    private String targetApi;
    private String targetMethod;
    private Long requestTime;
    private Long queryCounts;
    private Long queryTime;

    public static LogFormDto from(PerformanceLoggingForm logForm) {
        final LogFormDto logFormDto = new LogFormDto();
        logFormDto.targetApi = logForm.getTargetApi();
        logFormDto.targetMethod = logForm.getTargetMethod();
        logFormDto.requestTime = logForm.getRequestTime();
        logFormDto.queryCounts = logForm.getQueryCounts();
        logFormDto.queryTime = logForm.getQueryTime();
        return logFormDto;
    }

    public String getTargetApi() {
        return targetApi;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public Long getRequestTime() {
        return requestTime;
    }

    public Long getQueryCounts() {
        return queryCounts;
    }

    public Long getQueryTime() {
        return queryTime;
    }
}
