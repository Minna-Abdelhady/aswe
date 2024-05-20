package com.example.aswe.project.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    // Defining a pointcut for any method in the controllers package
    @Pointcut("execution(* com.example.aswe.demo.controllers.*.*(..))")
    public void controllersPointcut(){}

    // Defining an advice to be executed before any method in the controllers package is called
    @Before("controllersPointcut()")
    public void beforeMethod(JoinPoint joinPoint){
        System.out.println("Before method execution: " + joinPoint.getSignature());
    }

}
