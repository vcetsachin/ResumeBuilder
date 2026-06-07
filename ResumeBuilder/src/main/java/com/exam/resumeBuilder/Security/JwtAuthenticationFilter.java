package com.exam.resumeBuilder.Security;

import com.exam.resumeBuilder.Document.User;
import com.exam.resumeBuilder.Repository.UserRepository;
import com.exam.resumeBuilder.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String userId = null;
        if(authHeader != null &&  authHeader.startsWith("Bearer")){
            token = authHeader.substring(7);
            try{
                userId = jwtUtil.getUserIdFromToken(token);

            }
            catch (Exception e){
                log.error("Token is not valid/available");
            }
        }
        if (userId != null && SecurityContextHolder.getContext().getAuthentication()==null){
            try{
                if (jwtUtil.validToken(token) &&  ! jwtUtil.isExpiredToken(token)){
                  User user =  userRepository.findById(userId).orElseThrow( () -> new UsernameNotFoundException("User not found."));
                    UsernamePasswordAuthenticationToken authenticationFilter = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                    authenticationFilter.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationFilter);
                }
            }
             catch (Exception e){
                log.info("Exception occurred while validating the token");
             }
        }
        filterChain.doFilter(request, response);
    }
}
