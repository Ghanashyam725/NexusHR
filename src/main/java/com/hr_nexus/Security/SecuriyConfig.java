package com.hr_nexus.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecuriyConfig {

    private JwtTokenUtil jwtUtil;
    private CustomUserDetailService customUserDetail;

    public SecuriyConfig(
            JwtTokenUtil jwtUtil,
            CustomUserDetailService customUserDetail) {

        this.jwtUtil = jwtUtil;
        this.customUserDetail = customUserDetail;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {

        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncode() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(
                                        "/api/user_auth/**",
                                        "/api/email/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated());

        // Mam has not activated the JWT filter at this stage.
        // http.addFilterBefore(
        //         jwtAuthenticationFilter(),
        //         UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Mam has kept this bean commented at this stage.
    //
    // @Bean
    // public JwtAuthenticationFilter jwtAuthenticationFilter() {
    //     return new JwtAuthenticationFilter(
    //             jwtUtil,
    //             customUserDetail);
    // }
}
