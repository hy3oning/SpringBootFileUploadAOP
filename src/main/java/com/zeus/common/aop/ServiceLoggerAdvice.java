package com.zeus.common.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class ServiceLoggerAdvice {

	@Before("execution(* com.zeus.service.ItemService*.*(..))")
	public void beforeLog(JoinPoint jp) {
		log.info("Aspect beforeLog");
		log.info("Aspect beforeLog jp = "+ jp.getSignature());
		log.info("Aspect beforeLog jp = "+ Arrays.toString(jp.getArgs()));
	}
}
