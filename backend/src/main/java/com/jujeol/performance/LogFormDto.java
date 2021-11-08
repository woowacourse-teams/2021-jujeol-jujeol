package com.jujeol.performance;

public class LogFormDto {

    private String targetApi;
    private String targetMethod;
    private Long transactionTime;
    private Long queryCounts;
    private Long queryTime;

    public static LogFormDto from(PerformanceLoggingForm logForm) {
        final LogFormDto logFormDto = new LogFormDto();
        logFormDto.targetApi = logForm.getTargetApi();
        logFormDto.targetMethod = logForm.getTargetMethod();
        logFormDto.transactionTime = logForm.getTransactionTime();
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

    public Long getTransactionTime() {
        return transactionTime;
    }

    public Long getQueryCounts() {
        return queryCounts;
    }

    public Long getQueryTime() {
        return queryTime;
    }
}
