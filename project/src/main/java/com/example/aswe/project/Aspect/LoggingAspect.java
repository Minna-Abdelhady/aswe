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

}
