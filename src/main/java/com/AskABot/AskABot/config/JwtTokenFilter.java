package com.AskABot.AskABot.config;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.AskABot.AskABot.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${jwtSecret}")
    private String jwtSecret;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, 
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractTokenFromRequest(request);

        if (token != null) {
            try {
                @SuppressWarnings("deprecation")
                Claims claims = Jwts.parser()
                                    .setSigningKey(jwtSecret.getBytes())
                                    .parseClaimsJws(token)
                                    .getBody();
                String userId = claims.getSubject(); // Assuming the user ID is stored in the subject

                request.setAttribute("userId", userId);
            } catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {

        String headerValue = request.getHeader("Authorization");

        if (headerValue != null && headerValue.startsWith("Bearer ")) {

            return headerValue.substring(7);
        }

        return null;
    }
}
