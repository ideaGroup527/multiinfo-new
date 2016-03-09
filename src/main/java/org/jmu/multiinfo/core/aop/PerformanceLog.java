package org.jmu.multiinfo.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceLog {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	    @Pointcut("execution(* org.jmu.multiinfo.service.*.impl..*(..)) && !execution(* org.jmu.multiinfo.service.basestatistics.impl..*(..))")
	    public void pointcut() {

	    }

	    
	    //包含base统计
	    @Pointcut("execution(* org.jmu.multiinfo.service.*.impl..*(..))")
	    public void pointcut2() {

	    }
	    //声明环绕通知    
	    @Around("pointcut()")    
	    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {    
	        long begin = System.nanoTime();  
	        Object o = pjp.proceed();    
	        long end = System.nanoTime();  
            logger.warn(pjp.getSignature() + " take " + (end-begin)/1000000 + " ms");
	        return o;    
	    }    
}
