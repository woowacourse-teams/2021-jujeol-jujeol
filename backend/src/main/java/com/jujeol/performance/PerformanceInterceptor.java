package com.jujeol.performance;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
public class PerformanceInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    private final PerformanceLoggingForm logForm;
    private final ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        logForm.setTransactionTime(System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        if (request.getMethod().equals("OPTIONS") || logForm.getTargetApi() == null || logForm.getTargetApi().isEmpty()) {
            return;
        }
        final long transactionTime = System.currentTimeMillis() - logForm.getTransactionTime();
        logForm.setTransactionTime(transactionTime);

        log.info(objectMapper.writeValueAsString(LogFormDto.from(logForm)));
    }
}
