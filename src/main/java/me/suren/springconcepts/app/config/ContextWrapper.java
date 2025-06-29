package me.suren.springconcepts.app.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ContextWrapper {

    @Getter
    private final ThreadLocal<Map<String, Object>> context = new ThreadLocal<>();
}
