package me.suren.springconcepts.app.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ContextWrapper {

    @Getter
    private ThreadLocal<Map<String, Object>> context = ThreadLocal.withInitial(HashMap::new);
}
