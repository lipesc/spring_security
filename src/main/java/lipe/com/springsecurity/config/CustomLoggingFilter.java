package lipe.com.springsecurity.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

public class CustomLoggingFilter extends HttpFilter {

    private static final Logger logger = LoggerFactory.getLogger(CustomLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Request URI: {}", request.getRequestURI());
            logger.error("Request Method: {}", request.getMethod());
            logger.error("Request Headers: {}", Collections.list(request.getHeaderNames())
                    .stream()
                    .map(headerName -> headerName + ": " + Collections.list(request.getHeaders(headerName)))
                    .collect(Collectors.joining(", ")));
            logger.error("Error: ", e);
            throw e;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}