package com.JWT_firstTest.securityCrud.filter;

import com.JWT_firstTest.securityCrud.entity.UserInfo;
import com.JWT_firstTest.securityCrud.repository.UserInfoRepository;
import com.JWT_firstTest.securityCrud.service.JwtService;
import com.JWT_firstTest.securityCrud.service.UserInfoService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


// This class helps us to validate the generated jwt token
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userDetailsService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ") ) {
            token = authHeader.substring(7);
            username = jwtService.extractUsername(token);
            username = username.split(" ")[1];
            //System.out.println(username);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if(username.contains(" "))
                username = username.split(" ")[1];

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Optional<UserInfo> user = userInfoRepository.findByName(userDetails.getUsername());

            if (jwtService.validateToken(token, userDetails)) {
                System.out.println("111111111");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
