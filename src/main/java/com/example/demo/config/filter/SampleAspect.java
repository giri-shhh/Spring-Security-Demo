package com.example.demo.config.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.function.Predicate;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SampleAspect {

    private final ApplicationContext applicationContext;

    @SuppressWarnings("unchecked")
    @Around("@annotation(filterResponse)")
    public Object hello(ProceedingJoinPoint joinPoint, FilterResponse filterResponse) throws Throwable {
        Object result = joinPoint.proceed();
        if (!(result instanceof Collection)) return result;

        Class<? extends Predicate<?>> filter = filterResponse.filter();
        Predicate<? super FilterValue<?>> filterRule = (Predicate<? super FilterValue<?>>) applicationContext.getBean(filter);
        Collection<FilterValue<?>> serviceResult = (Collection<FilterValue<?>>) result;

        serviceResult.removeIf(filterRule.negate());
        return serviceResult;
    }
}
