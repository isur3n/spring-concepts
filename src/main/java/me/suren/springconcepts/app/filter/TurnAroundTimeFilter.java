package me.suren.springconcepts.app.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.suren.springconcepts.app.config.ContextWrapper;
import me.suren.springconcepts.app.exception.CustomAppException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class TurnAroundTimeFilter extends OncePerRequestFilter {

    private ContextWrapper contextWrapper;

    @Autowired
    public TurnAroundTimeFilter(ContextWrapper contextWrapper) {
        this.contextWrapper = contextWrapper;
    }

    private ThreadLocal<Map<String, Object>> ctx = ThreadLocal.withInitial(HashMap::new);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        boolean turnAroundTimeRequested = StringUtils.equalsIgnoreCase(request
                .getHeader("x-request-turn-around-time"), "true");
        if(turnAroundTimeRequested) {
            beforeRequest(request, response);
        }

        // Let the request proceed to controller
        filterChain.doFilter(request, response);

        if(turnAroundTimeRequested) {
            afterRequest(response);
        }

    }

    public void beforeRequest(HttpServletRequest request, HttpServletResponse response) {
        log.info("Turn around time is requested.");
        /*contextWrapper.getContext()
                .get()
                .put("start", LocalDateTime.now());*/
        ctx.get().put("start", LocalDateTime.now());
    }

    public void afterRequest(HttpServletResponse response) throws ServletException {

        if(ctx.get() == null) {
            log.error("Context has not been set up.");
            throw new CustomAppException("Context has not been set up.");
        }

        log.info("Can we add response headers? - {}", !response.isCommitted());

        LocalDateTime end = LocalDateTime.now();
        ctx.get().put("end", end);

        Duration duration = Duration.between((LocalDateTime) ctx.get().get("start"), end);
        ctx.get().put("duration", duration);

        response.setIntHeader("x-turn-around-time", duration.getNano());
        log.info("Turn around time is - {}", duration.getNano());
    }
}
