package com.talkingPotatoes.potatoesProject.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Aspect
@Component
public class RestControllerLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLoggingAspect.class);

    @Pointcut("within(com.talkingPotatoes.potatoesProject.*.controller.*)")
    public void onRequest() {
    }

    @Around("com.talkingPotatoes.potatoesProject.common.aspect.RestControllerLoggingAspect.onRequest()")
    public Object controllerLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        Class clazz = joinPoint.getTarget().getClass();

        logger.info(getRequestUrl(joinPoint, clazz));
        logger.info(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + " ::: " + JSONValue.toJSONString(params(joinPoint)));

        Object result = joinPoint.proceed(joinPoint.getArgs());
        return result;
    }

    private String getRequestUrl(JoinPoint joinPoint, Class clazz) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
        String baseUrl = requestMapping.value()[0];

        String url = Stream.of(GetMapping.class, PostMapping.class, PutMapping.class, PatchMapping.class, DeleteMapping.class, RequestMapping.class)
                .filter(mappingClass -> method.isAnnotationPresent(mappingClass))
                .map(mappingClass -> {
                    try {
                        return getUrl(method, mappingClass, baseUrl);
                    } catch (NoSuchMethodException e) {
                        return null;
                    }
                })
                .findFirst().orElse(null);

        return url;
    }

    /* httpMethod + requestURI 변환 */
    private String getUrl(Method method, Class<? extends Annotation> annotationClass, String baseUrl) throws NoSuchMethodException {
        Annotation annotation = method.getAnnotation(annotationClass);
        String[] value;
        String httpMethod = null;

        try {
            value = (String[]) annotationClass.getMethod("value").invoke(annotation);
            httpMethod = (annotationClass.getSimpleName().replace("Mapping", "")).toUpperCase();
        } catch (IllegalAccessException | InvocationTargetException e) {
            return null;
        }

        return String.format("%s %s%s", httpMethod, baseUrl, value.length > 0 ? value[0] : "");
    }

    private Map params(JoinPoint joinPoint) {
        CodeSignature codeSignature = (CodeSignature) joinPoint.getSignature();
        String[] parameterNames = codeSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            params.put(parameterNames[i], args[i]);
        }

        return params;
    }
}
