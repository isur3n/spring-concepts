package me.suren.springconcepts.app.aspect;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import me.suren.springconcepts.app.annotation.TimeIt;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Aspect
@Component
public class TimeItInterceptor {

    private Set<Method> methods = new HashSet<>();

    @PostConstruct
    public void init() {
        Reflections reflections = new Reflections("me.suren", new MethodAnnotationsScanner());
        methods.addAll(reflections.getMethodsAnnotatedWith(TimeIt.class));
    }

    @Around("@annotation(me.suren.springconcepts.app.annotation.TimeIt)")
    public Object executeAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object response = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        log.info(joinPoint.getSignature().toLongString() + " executed in " + executionTime + "ms");

        return response;
    }
}
