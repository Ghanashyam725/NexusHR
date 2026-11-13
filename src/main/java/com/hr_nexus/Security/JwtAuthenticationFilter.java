package com.hr_nexus.Security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtUtil;
    private final CustomUserDetailService customUserDetail;

    public JwtAuthenticationFilter(
            JwtTokenUtil jwtUtil,
            CustomUserDetailService customUserDetail) {

        this.jwtUtil = jwtUtil;
        this.customUserDetail = customUserDetail;
    }

    @Autowired
    private TokenBlockService tokenBlockService;

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws IOException, ServletException {

        String header = request.getHeader("Authorization");

        String token = jwtUtil.extractToken(header);
        String tokenBlock = token;

        if (StringUtils.hasText(token) && jwtUtil.validateToken(token)) {

            String userEmail = jwtUtil.gerUserEmail(token);

            try {

                UserDetails userDetails =
                        customUserDetail.loadUserByUserEmail(userEmail);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);

            } catch (Exception e) {

                SecurityContextHolder.clearContext();

                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "user not found"
                );

                return;
            }

            if (tokenBlock != null) {

                if (tokenBlockService.iskillingToken(tokenBlock)) {

                    SecurityContextHolder.clearContext();

                    response.setStatus(
                            HttpServletResponse.SC_UNAUTHORIZED
                    );

                    response.getWriter()
                            .write("Account loggedOut ");

                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
