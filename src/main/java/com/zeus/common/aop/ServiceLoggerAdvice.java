package com.zeus.common.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class ServiceLoggerAdvice {

//	@Before("execution(* com.zeus.service.ItemService*.*(..))")
//	public void beforeLog(JoinPoint jp) {
//		log.info("Aspect beforeLog");
//		log.info("Aspect beforeLog jp = " + jp.getSignature());
//		log.info("Aspect beforeLog jp = " + Arrays.toString(jp.getArgs()));
//	}
//
//	@After("execution(* com.zeus.service.ItemService*.*(..))")
//	public void afterLog(JoinPoint jp) {
//		log.info("Aspect afterLog");
//		log.info("Aspect afterLog jp = " + jp.getSignature());
//		log.info("Aspect afterLog jp = " + Arrays.toString(jp.getArgs()));
//	}
//
//	@AfterReturning(pointcut = "execution(* com.zeus.service.ItemService*.*(..))", returning = "result")
//	public void afterReturning(JoinPoint jp, Object result) {
//		log.info("Aspect afterReturning");
//		log.info("Aspect afterReturning jp = " + jp.getSignature());
//		log.info("Aspect afterReturning jp = " + Arrays.toString(jp.getArgs()));
//		log.info("Aspect afterReturning result = " + result.toString());
//	}

	@Around("execution(* com.zeus.service.ItemService*.*(..))")
	public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable {
		log.info("=========================Around==============================");
		long startTime = System.currentTimeMillis(); //1초 1/1000
		log.info(Arrays.toString(pjp.getArgs()));
		// 여기서 비즈니스 모델을 실행함
		Object result = pjp.proceed();
		long endTime = System.currentTimeMillis();
		log.info(pjp.getSignature().getName() + " : " + (endTime - startTime));
		// ServiceImpl 에서 실행되는 결과값을 Controller 에 전달한다.
		return result;
	}
}
