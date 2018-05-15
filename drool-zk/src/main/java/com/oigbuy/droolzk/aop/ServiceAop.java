package com.oigbuy.droolzk.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author qiang.zhou
 */
@Aspect
@Component
public class ServiceAop {
    private static Logger logger = LoggerFactory.getLogger(ServiceAop.class);
    private ThreadLocal<Long> timeLocal = new ThreadLocal<>();

    /**
     * 监听com.oigbuy.droolzk.controller 包(包括子目录)下所有类
     * @return --
     */

    @Pointcut("execution(public * com.oigbuy.droolzk.controller.*.*(..))")
    public void webRequestLog() {
    }

    @Before("webRequestLog()")
    public void doBefore(JoinPoint joinPoint) {
        timeLocal.set(System.currentTimeMillis());
    }

    @AfterReturning(returning = "result", pointcut = "webRequestLog()")
    public void doAfterReturning(Object result) {
        long startTime = timeLocal.get();
        logger.info("花费的时间为:" + (System.currentTimeMillis() - startTime) + "毫秒");
        //调用结束 为该线程本地变量移除当前线程的值
        timeLocal.remove();
    }

    @Around("webRequestLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object o;
        o = pjp.proceed();
        return o;
    }
}
