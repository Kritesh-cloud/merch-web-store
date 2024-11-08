package cm.ex.merch.security.filter;

import cm.ex.merch.controller.AuthenticationController;
import cm.ex.merch.security.authentication.UserAuth;
import cm.ex.merch.security.provider.BasicAuthenticationProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.util.Base64;

@Component
public class BasicFilter extends OncePerRequestFilter {

    Logger logger = LoggerFactory.getLogger(BasicFilter.class);

    private final HandlerExceptionResolver handlerExceptionResolver;

    public BasicFilter(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Autowired
    private BasicAuthenticationProvider basicAuthenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.info("BasicFilter");
        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if(authHeader.startsWith("Basic ")) {
            String email = extractUsernameAndPassword(authHeader)[0];
            String password = extractUsernameAndPassword(authHeader)[1];
            UserAuth ua = new UserAuth(false, email, password,null,null,null);

            UserAuth auth = (UserAuth) authenticationManager(ua);

            if(auth.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            else {
                handlerExceptionResolver.resolveException(request, response, null, new AccessDeniedException("Bad Credentials"));
                return;
            }

        }

        filterChain.doFilter(request, response);
    }

    @Bean
    AuthenticationManager authenticationManager(Authentication authentication) throws AuthenticationException {
        logger.info("BasicFilter Auth Manager");
        UserAuth userAuth = (UserAuth) authentication;

        if(userAuth.getToken() == null && basicAuthenticationProvider.supports(authentication.getClass())) {
            return (AuthenticationManager) basicAuthenticationProvider.authenticate(authentication);
        }

        throw new BadCredentialsException("Bad Credentials Exception");
    }

    private String[] extractUsernameAndPassword(String authorization) {
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);

        return credentials.split(":", 2); // values = [username, password]

    }
}
