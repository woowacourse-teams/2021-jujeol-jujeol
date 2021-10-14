package com.jujeol.performance.annotationDataExtractor;

import com.jujeol.performance.RequestApi;
import java.lang.reflect.Method;

public interface AnnotationDataExtractor {

    boolean isAssignable(Method method);

    RequestApi extractRequestApi(Method method, String classUrl);
}
