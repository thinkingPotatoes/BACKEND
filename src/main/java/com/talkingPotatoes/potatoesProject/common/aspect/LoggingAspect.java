package com.talkingPotatoes.potatoesProject.common.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(com.talkingPotatoes.potatoesProject.*.controller.*)")
    public void onRequest() {
    }

    @Around("com.talkingPotatoes.potatoesProject.common.aspect.LoggingAspect.onRequest()")
    public Object controllerLogging(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Map<String, String[]> paramMap = request.getParameterMap();
        String params = "";
        if (paramMap.isEmpty() == false) {
            params = " [" + paramMapToString(paramMap) + "]";
        }

        try {
            return pjp.proceed(pjp.getArgs()); // 6
        } finally {
            logger.debug("Request: {} {}{} < {}", request.getMethod(), request.getRequestURI(),
                    params, request.getRemoteHost());
        }
    }

    /* 서비스 패키지 밑에 파라미터가 0개 이상인 모든 메서드가 호출될 때 */
    @Around("execution(* com.talkingPotatoes.potatoesProject.*.service.*.*(..))")
    public Object serviceLogging(ProceedingJoinPoint pjp) throws Throwable {
        logger.info(pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName() + " ::: " + Arrays.toString(pjp.getArgs()));
        Object result = pjp.proceed();
        logger.info(pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName() + "::: finished");
        return result;
    }

    private String paramMapToString(Map<String, String[]> paraStringMap) {
        return paraStringMap.entrySet().stream()
                .map(entry -> String.format("%s : %s",
                        entry.getKey(), Arrays.toString(entry.getValue())))
                .collect(Collectors.joining(", "));
    }
}
