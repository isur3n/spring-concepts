package me.suren.springconcepts.app.controller;

import lombok.extern.slf4j.Slf4j;
import me.suren.springconcepts.app.dto.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/app/spring-concepts/interceptor-tester")
public class InterceptorTester {

    private static final Map<String, Object> SUCCESS_RESPONSE = Map.of("status", "UP");

    @GetMapping({"", "/"})
    public Map<String, Object> getSuccessResponse() {
        return SUCCESS_RESPONSE;
    }
}
