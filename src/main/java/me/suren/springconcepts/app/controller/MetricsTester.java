package me.suren.springconcepts.app.controller;

import lombok.extern.slf4j.Slf4j;
import me.suren.springconcepts.app.annotation.TimeIt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/app/spring-concepts/metrics-tester")
public class MetricsTester {

    private static final Random RANDOM_NUMBER_GENERATOR = new Random();

    @GetMapping({"", "/"})
    @TimeIt
    public String getRandomNumber() {
        Long randomNumber = RANDOM_NUMBER_GENERATOR.nextLong();
        log.info("Random number generated for id - {}", randomNumber);
        return StringUtils.join("Random number generated - ", randomNumber);
    }
}
