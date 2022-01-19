package com.jujeol.performance;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Setter
@Getter
@Component
@RequestScope
@Profile("dev")
public class PerformanceLoggingForm {

    private String targetApi;
    private String targetMethod;
    private Long requestTimeMils;
    private Long queryCounts = 0L;
    private Long queryTimeMils = 0L;

    public void queryCountUp() {
        queryCounts++;
    }

    public void addQueryTime(Long queryTime) {
        this.queryTimeMils += queryTime;
    }
}
