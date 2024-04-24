package com.agrusi.backendapi.security;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    private final JwtAuthenticationConverter jwtAuthenticationConverter;

    public SecurityFilterChainConfig(
            JwtAuthenticationConverter jwtAuthenticationConverter
    ) {
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/register/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/login/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/auth/secured").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/v1/auth/user").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
                .anyRequest().permitAll());

        http.oauth2ResourceServer(
                oauth2 -> oauth2.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)
                )
        );

        http.exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
        );

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        return http.build();
    }

    /**
     * See info about the successful authentication in the terminal...
     */

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> successListener() {

        return event -> {
            System.out.println(String.format(
                    "SUCCESS [%s] %s",
                    event.getAuthentication().getClass().getName(),
                    event.getAuthentication().getName())
            );
        };
    }
}
