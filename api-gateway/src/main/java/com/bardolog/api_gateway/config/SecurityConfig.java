package com.bardolog.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        http    .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(authorizeExchangeSpec -> {
                    authorizeExchangeSpec.pathMatchers("/actuator/**").permitAll();
                    authorizeExchangeSpec.anyExchange().authenticated();
                })
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }

}
