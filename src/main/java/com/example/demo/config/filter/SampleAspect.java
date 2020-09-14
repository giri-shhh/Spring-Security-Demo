package com.example.demo.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class SampleAspect {

    @SuppressWarnings("unchecked")
    @Around("@annotation(filterResponse)")
    public Object hello(ProceedingJoinPoint joinPoint, FilterResponse filterResponse) throws Throwable {
        Object result = joinPoint.proceed();
        if (!(result instanceof List)) return result;

        Predicate<FilterValue<?>> filter = (Predicate<FilterValue<?>>) filterResponse.filter().getDeclaredConstructor().newInstance();
        List<FilterValue<?>> resultList = (List<FilterValue<?>>) result;

        return resultList.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }
}
