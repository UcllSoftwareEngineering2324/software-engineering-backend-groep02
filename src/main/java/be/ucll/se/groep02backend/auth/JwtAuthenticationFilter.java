package be.ucll.se.groep02backend.auth;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtSevice jwtService;

    @Override
    protected void doFilterInternal(
        @NotNull HttpServletRequest request, 
        @NotNull HttpServletResponse response, 
        @NotNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        final String token;
        final String userEmail;
        if (authorizationHeader == null && ! authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        } 
        token = authorizationHeader.substring(7);
        userEmail = // TODO: get user email from token

    }
    
    
}
