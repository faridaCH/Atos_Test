package com.atos.atosproject.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

@Aspect
@Component
public class loggingAspect {

    long startTime = 0;
    long endTime = 0;
   Logger logger = Logger.getLogger(loggingAspect.class.getName());


    @Around("execution(* com.atos.atosproject.services.UserService.edit*(..))")
    public Object callEditUser( ProceedingJoinPoint proceedingJoinPoint ) throws Throwable {

       // logger.info("************* Beford  execution editUser    *************");
       System.out.println("************* Beford  execution editUser    *************");
        startTime = System.currentTimeMillis();
        Object value = null;
        try {
            value = proceedingJoinPoint.proceed(); // call edit
        } catch (Throwable e) {

            logger.info("*************  editUser  problem   *************");
            logger.info(e.getMessage());
            throw e;
        }
        endTime = System.currentTimeMillis();

        logger.info("************* After  execution editUser , duration = "+(endTime-startTime) +" *************");
        return value;
    }



    @Around("execution(* com.atos.atosproject.services.UserService.add*(..))")
    public Object callAddUser( ProceedingJoinPoint proceedingJoinPoint ) throws Throwable {

        logger.info("************* Beford  execution addUser    *************");

        startTime = System.currentTimeMillis();
        Object value = null;
        try {
            value = proceedingJoinPoint.proceed(); // call edit
        } catch (Throwable e) {

            logger.info("*************  addUser  problem   *************");
            logger.info(e.getMessage());
            throw e;

        }
        endTime = System.currentTimeMillis();

        logger.info("************* After  execution addUser , duration = "+(endTime-startTime) +" *************");
        return value;
    }


    @Before("execution(* com.atos.atosproject.services.UserService.delete(..))")
    public void beforeDeleteUser() {
        logger.info("************* Before  execution delete    *************");
        startTime = System.currentTimeMillis();
    }

    @After("execution(* com.atos.atosproject.services.UserService.delete(..))")
    public void afterDeleteUser() {
        endTime = System.currentTimeMillis();
        logger.info("************* After execution delete  , duration = "+(endTime-startTime) +" *************");
    }


    @Before("execution(* com.atos.atosproject.services.UserService.find*(..))")
    public void BeforeCallGetUser() {
        logger.info("************* Before  execution find    *************");
        startTime = System.currentTimeMillis();
    }
    @After("execution(* com.atos.atosproject.services.UserService.find*(..))")
    public void AfterCallGetUser() {
        endTime = System.currentTimeMillis();
        logger.info("************* Before  execution find  , duration = "+(endTime-startTime) +" *************");

    }
}
