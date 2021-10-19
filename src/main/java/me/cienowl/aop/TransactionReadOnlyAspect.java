package me.cienowl.aop;

import lombok.extern.slf4j.Slf4j;
import me.cienowl.context.DatabaseContextHolder;
import me.cienowl.context.DatabaseEnvironment;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Aspect
@Component
@Order(0)
public class TransactionReadOnlyAspect {
    @Around("@annotation(transactional)")
    public Object proceed(ProceedingJoinPoint proceedingJoinPoint, Transactional transactional) throws Throwable {
        log.info("-== Aspect executed ==-");
        try {
            if (transactional.readOnly()) {
                DatabaseContextHolder.set(DatabaseEnvironment.READONLY);
                log.info("!-> Connection Read-Only <-!");
            }
            return proceedingJoinPoint.proceed();
        } finally {
            DatabaseContextHolder.reset();
            log.info("-== Reset Database Context Holder ==-");
        }
    }
}
