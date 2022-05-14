package com.example.testtask.loging;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAspect {
    private static final Logger LOGGER = Logger.getLogger(LoggingAspect.class.getName());

    @Pointcut("within(com.example.testtask.services.*)")
    public void serviceMethods() {
    }

    @Pointcut("within(com.example.testtask.controllers.CustomerController)")
    public void controllerMethods() {
    }

    @Pointcut("within(com.example.testtask.controllers.ExceptionController)")
    public void controllerExceptions() {
    }

    @Around("controllerMethods()")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.debug("controller: method has been called - " + joinPoint.getSignature().getName());
        StringBuilder args = new StringBuilder();
        Arrays.stream(joinPoint.getArgs()).forEach(arg->{
            args.append("input args: ").append(arg).append(" | ");
        });
        if (!args.toString().isEmpty())
            LOGGER.debug(args.toString());
        Object proceed = joinPoint.proceed();
        LOGGER.debug("controller: method completed successfully - " + joinPoint.getSignature().getName());
        LOGGER.debug("controller: return value - " + proceed.toString());
        return proceed;
    }

    @Around("serviceMethods()")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.debug("service: method has been called - " + joinPoint.getSignature().getName());
        long start = System.currentTimeMillis();
        StringBuilder args = new StringBuilder();
        Arrays.stream(joinPoint.getArgs()).forEach(arg->{
            args.append("input args: ").append(arg).append(" | ");
        });
        if (!args.toString().isEmpty())
            LOGGER.debug(args.toString());
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        LOGGER.debug("service: method - " + joinPoint.getSignature().getName() +  " completed in " + executionTime + " ms");
        LOGGER.debug("service: return value - " + proceed.toString());
        return proceed;
    }

    @AfterThrowing(value = "serviceMethods()", throwing = "e")
    public void logServiceMethodsAfterTrowing(JoinPoint joinPoint, Throwable e) {
        LOGGER.debug("service: error in method - " + joinPoint.getSignature());
        LOGGER.error(e);
    }

    @Around("controllerExceptions()")
    public Object logControllerExMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.error("exception handler: error - " + joinPoint.getSignature().getName());
        Object proceed = joinPoint.proceed();
        LOGGER.error("exception handler: caused by: " + proceed.toString());
        return proceed;
    }
}
