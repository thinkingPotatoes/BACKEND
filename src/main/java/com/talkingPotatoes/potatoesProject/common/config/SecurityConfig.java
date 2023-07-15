package com.talkingPotatoes.potatoesProject.common.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.talkingPotatoes.potatoesProject.common.handler.CustomAccessDeniedHandler;
import com.talkingPotatoes.potatoesProject.common.handler.CustomAuthenticationEntryPoint;
import com.talkingPotatoes.potatoesProject.common.jwt.JwtAuthenticationFilter;
import com.talkingPotatoes.potatoesProject.common.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        CustomAuthenticationEntryPoint customAuthenticationEntryPoint = new CustomAuthenticationEntryPoint();
        CustomAccessDeniedHandler customAccessDeniedHandler = new CustomAccessDeniedHandler();

        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
                                .anyRequest().authenticated()
                )
                .logout((logout) -> {
                    logout.logoutUrl("/users/logout");
                    logout.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK));
                    logout.invalidateHttpSession(true);
                    logout.deleteCookies("refreshToken");
                })
                .exceptionHandling((exceptionHandlingConfigurer) -> {
                    exceptionHandlingConfigurer.authenticationEntryPoint(customAuthenticationEntryPoint);
                    exceptionHandlingConfigurer.accessDeniedHandler(customAccessDeniedHandler);
                })
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, objectMapper), UsernamePasswordAuthenticationFilter.class)
                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}