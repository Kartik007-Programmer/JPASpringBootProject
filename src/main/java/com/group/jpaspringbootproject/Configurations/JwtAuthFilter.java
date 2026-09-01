package com.group.jpaspringbootproject.Configurations;

import com.group.jpaspringbootproject.Services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String authToken = null;
        String username = null;

        String uri = request.getRequestURI();
        if (uri.equals("/LoginForm.html") ||
                uri.equals("RegistrationForm.html") ||
                uri.startsWith("/auth/")){

            filterChain.doFilter(request, response);
            return;
        }

        if (request.getCookies() != null){
            for (Cookie cookie : request.getCookies()) {
                if ("JWT_TOKEN".equals(cookie.getName())) {
                    authToken = cookie.getValue();
                    break;
                }
            }
        }

        if (authToken == null) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                authToken = authHeader.substring(7);
            }
        }

        if (authToken != null) {
            try {
                username = jwtService.getUsernameByToken(authToken);
            } catch (Exception e) {
                authToken = null;
            }        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(authToken,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());

                    usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
                }
            } catch (Exception e) {}
        }

        // If not authenticated, redirect to login
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            response.sendRedirect("/LoginForm.html");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
