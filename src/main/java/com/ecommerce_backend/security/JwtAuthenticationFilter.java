package com.ecommerce_backend.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /*get token from header*/
        String requestToken = request.getHeader("Authorization");

        logger.info("message {}", requestToken);

        String username = null;
        String jwtToken = null;

        if (requestToken != null && requestToken.trim().startsWith("Bearer ")) {
            jwtToken = requestToken.substring(7);

            try {

                username = jwtHelper.getUserName(jwtToken);


            } catch (ExpiredJwtException e) {
                logger.info("Invalid token message", "Jwt Token Expired");
                e.printStackTrace();

            } catch (MalformedJwtException e) {
                logger.info("Invalid token message", "Invalid Jwt Token");
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                logger.info("Invalid token message", "Unable to get Jwt Token");
                e.printStackTrace();
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                /*Validate Token*/
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtHelper.validateToken(jwtToken, userDetails)) {

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                } else {
                    logger.info("Invalid message", "Invalid Jwt Token");

                }
            } else {
                logger.info("User message", " Username is null");

            }

        } else {
            logger.info("Token Message {} ", "Token does not start with Bearer ");
        }
        filterChain.doFilter(request, response);
    }
}
