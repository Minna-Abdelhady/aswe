package com.example.aswe.project.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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

    // Defining a pointcut for the getUsers method in the controllers package
    @Pointcut("execution(* com.example.aswe.demo.controllers.*.getUsers(..))")
    public void getUsersPointcut(){}

    // Defining an advice (el howa action) to be executed before any method in the controllers package is called
    @Before("controllersPointcut()")
    public void beforeMethod(JoinPoint joinPoint){
        System.out.println("Before method execution: " + joinPoint.getSignature());



    }

        // This method measures the time taken for the execution of any method in the controllers package and prints it
    @Around("within(com.example.aswe.demo.controllers.*)")
    public Object timeTracker(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long timeTakeninMs = endTime - startTime;
        System.out.println("Time taken by "+joinPoint.getSignature()+" is "+timeTakeninMs+"ms");
        return result;
    }
    

}
