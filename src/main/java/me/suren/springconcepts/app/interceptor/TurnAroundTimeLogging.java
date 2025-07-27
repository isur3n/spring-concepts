package me.suren.springconcepts.app.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.suren.springconcepts.app.config.ContextWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.Map;

@Slf4j
@Component
public class TurnAroundTimeLogging implements HandlerInterceptor {

    private final ContextWrapper contextWrapper;

    @Autowired
    public TurnAroundTimeLogging(ContextWrapper contextWrapper) {
        this.contextWrapper = contextWrapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        log.info("Checking if intended HTTP header exists in request.");
        if(StringUtils.equalsIgnoreCase(request.getHeader("x-request-turn-around-time"), "true")) {
            log.info("Turn around time is requested.");
            contextWrapper.getContext()
                    .get()
                    .put("start", LocalDateTime.now());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
           ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info("Clean up the context.");
        contextWrapper.getContext()
                .remove();
    }
}
