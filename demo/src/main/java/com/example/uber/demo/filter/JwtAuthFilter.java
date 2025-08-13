package com.example.uber.demo.filter;


import com.example.uber.demo.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Make this a Spring-managed
// filter runs for every request
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService; // Your UserDetailsServiceImplementation

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 1. Get the Authorization header from the request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 2. Check if the header exists and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // If not, pass the request to the next filter in the chain and exit
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extract the JWT from the header
        jwt = authHeader.substring(7); // "Bearer ".length() is 7

        // 4. Extract the user's email from the token
        userEmail = jwtService.extractEmail(jwt);

        // 5. Check if the user is not already authenticated
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 6. Load the UserDetails from the database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // 7. Validate the token
            if (jwtService.validateToken(jwt, userDetails.getUsername())) {
                // 8. If the token is valid, create an Authentication object
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Credentials are not needed as we are using JWT
                        userDetails.getAuthorities()
                );

                // 9. Set extra details for the authentication object
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 10. Set the Authentication object in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // 11. Pass the request to the next filter
        filterChain.doFilter(request, response);
    }
}