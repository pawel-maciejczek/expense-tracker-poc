package pl.maciejczekp.expense.tracker.configuration.security;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CsrfHeaderFilter extends OncePerRequestFilter {

    private static final String COOKIE_NAME = "XSRF-TOKEN";
    private final String contextPath;

    public CsrfHeaderFilter(String contextPath) {
        this.contextPath = StringUtils.isEmpty(contextPath) ? "/" : contextPath;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
                .getName());
        if (csrf != null) {
            Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);
            String token = csrf.getToken();
            if (cookie == null || token != null && !token.equals(cookie.getValue())) {
                cookie = new Cookie(COOKIE_NAME, token);
                cookie.setPath(contextPath);
                response.addCookie(cookie);
            }
        }
        filterChain.doFilter(request, response);
    }
}