package com.talkingPotatoes.potatoesProject.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.CodeSignature;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ServiceLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLoggingAspect.class);

    /* 서비스 패키지 밑에 파라미터가 0개 이상인 모든 메서드가 호출될 때 */
    @Around("execution(* com.talkingPotatoes.potatoesProject.*.service.*.*(..))")
    public Object serviceLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + " ::: " + JSONValue.toJSONString(params(joinPoint)));
        Object result = joinPoint.proceed();
        logger.info(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName() + "::: finished");
        return result;
    }

    /* exception logging */

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
