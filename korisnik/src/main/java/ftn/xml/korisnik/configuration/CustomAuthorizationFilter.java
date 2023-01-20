package ftn.xml.korisnik.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import ftn.xml.korisnik.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@AllArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/user/login") || (request.getServletPath().equals("/api/user") && request.getMethod().equals("POST")) || request.getServletPath().startsWith("/api/user/confirm/") || request.getServletPath().startsWith("/images") || request.getServletPath().equals("/api/email/test")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                authorizeFromHeader(request, response, filterChain, authorizationHeader);
            }
        }
    }

    private void authorizeFromHeader(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String authorizationHeader) throws IOException {
        try {
            String username = authService.getUsernameFromJWT(authorizationHeader);
            Collection<SimpleGrantedAuthority> authorities = authService.getAuthorityClaimsFromJWT(authorizationHeader);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            System.out.println("User " + username + " is authorized");
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            response.setHeader("error", exception.getMessage());
            response.setStatus(FORBIDDEN.value());
            Map<String, String> error = new HashMap<>();
            error.put("error_message", exception.getMessage());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }
}