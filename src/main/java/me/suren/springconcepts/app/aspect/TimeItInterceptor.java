package me.suren.springconcepts.app.aspect;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import me.suren.springconcepts.app.annotation.TimeIt;
import me.suren.springconcepts.app.exception.InvalidTimeItValue;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

@Slf4j
@Aspect
@Component
public class TimeItInterceptor {

    private final Map<Method, TimeIt> timeItMethods = new HashMap<>();

    @PostConstruct
    public void init() throws InvalidTimeItValue {

        Reflections reflections = new Reflections("me.suren", new MethodAnnotationsScanner());
        Set<Method> methods = new HashSet<>();
        methods.addAll(reflections.getMethodsAnnotatedWith(TimeIt.class));
        for(Method method : methods) {
            TimeIt timeIt = method.getDeclaredAnnotation(TimeIt.class);
            if(StringUtils.isBlank(timeIt.value())) {
                String message = StringUtils.join("Method ", method.toString(),
                        " has TimeIt annotation with invalid value.");
                throw new InvalidTimeItValue(message);
            }
            timeItMethods.put(method, timeIt);
        }
    }

    @Around("@annotation(me.suren.springconcepts.app.annotation.TimeIt)")
    public Object executeAround(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        Object response = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        TimeIt annotation = timeItMethods.get(method);
        log.info("Time taken for {} - {} ms.", annotation.value(), executionTime);

        return response;
    }
}
