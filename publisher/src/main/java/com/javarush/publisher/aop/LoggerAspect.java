package com.javarush.publisher.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
public class LoggerAspect {
    private final Logger log = LoggerFactory.getLogger(LoggerAspect.class);

    @Before("execution(* com.javarush.publisher.repository.*(*))")
    public void beforeMethodRepository(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("Before {} with args: {}", methodName, Arrays.toString(args));
    }
}
