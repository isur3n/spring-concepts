package me.suren.springconcepts.app.config;

import me.suren.springconcepts.app.interceptor.TurnAroundTimeLogging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private TurnAroundTimeLogging turnAroundTimeLogging;

    @Autowired
    public WebConfig(TurnAroundTimeLogging turnAroundTimeLogging) {
        this.turnAroundTimeLogging = turnAroundTimeLogging;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(turnAroundTimeLogging)
                .addPathPatterns("/**"); // Apply to all paths
    }
}
