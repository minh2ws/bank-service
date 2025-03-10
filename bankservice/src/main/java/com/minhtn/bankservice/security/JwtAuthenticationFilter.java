package com.minhtn.bankservice.security;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.minhtn.bankservice.entity.User;
import com.minhtn.bankservice.model.wrapper.ObjectResponseWrapper;
import com.minhtn.bankservice.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("[Request] " + request.getRemoteAddr() + " - " + request.getMethod() + " - " + request.getRequestURI());

        String authHeader = request.getHeader("Authorization");
        String username;
        String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        try {
            username = jwtService.extractUsername(jwt);
        } catch (JwtException e) {
            ObjectResponseWrapper responseMsg = ObjectResponseWrapper.builder()
                    .status(HttpServletResponse.SC_UNAUTHORIZED)
                    .message(e.getMessage())
                    .build();
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            JsonMapper jsonMapper = new JsonMapper();
            response.getOutputStream()
                    .println(jsonMapper.writeValueAsString(responseMsg));
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userRepository.findById(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            if (jwtService.isTokenValid(jwt, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        List.of(new SimpleGrantedAuthority("ADMIN"))
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
