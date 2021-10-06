package com.jujeol.performance;

import com.jujeol.performance.annotationDataExtractor.AnnotationDataExtractor;
import com.jujeol.performance.annotationDataExtractor.GetMappingDataExtractor;
import com.jujeol.performance.annotationDataExtractor.PostMappingDataExtractor;
import com.jujeol.performance.annotationDataExtractor.PutMappingDataExtractor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class RequestApiExtractor {

    private final List<AnnotationDataExtractor> dataExtractors = new ArrayList<>();

    RequestApiExtractor() {
        dataExtractors.add(new GetMappingDataExtractor());
        dataExtractors.add(new PostMappingDataExtractor());
        dataExtractors.add(new PutMappingDataExtractor());
    }

    public RequestApi extractRequestApi(JoinPoint joinPoint) {
        final String classUrl = getClassUrl(joinPoint);
        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        final Optional<AnnotationDataExtractor> extractor = dataExtractors.stream()
                .filter(annotationDataExtractor -> annotationDataExtractor
                        .isAssignable(methodSignature.getMethod()))
                .findAny();

        if (extractor.isEmpty()) {
            return new RequestApi();
        }
        return extractor.get().extractRequestApi(methodSignature.getMethod(), classUrl);
    }

    private String getClassUrl(JoinPoint joinPoint) {
        final Class<?> targetClass = joinPoint.getTarget().getClass();
        if (targetClass.isAnnotationPresent(RequestMapping.class)) {
            final RequestMapping requestMapping = targetClass.getAnnotation(RequestMapping.class);
            return Arrays.stream(requestMapping.value())
                    .findAny()
                    .orElseGet(() -> Arrays.stream(requestMapping.path()).findAny().orElse(""));
        }
        return "";
    }
}
