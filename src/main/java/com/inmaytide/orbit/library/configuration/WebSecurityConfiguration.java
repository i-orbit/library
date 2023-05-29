package com.inmaytide.orbit.library.configuration;

import com.inmaytide.exception.web.servlet.DefaultHandlerExceptionResolver;
import com.inmaytide.orbit.commons.security.CustomizedOpaqueTokenIntrospector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

/**
 * @author inmaytide
 * @since 2023/5/24
 */
@EnableWebSecurity
@EnableMethodSecurity
@DependsOn("exceptionResolver")
@Configuration
public class WebSecurityConfiguration {

    private final DefaultHandlerExceptionResolver exceptionResolver;

    private final RestTemplate restTemplate;

    public WebSecurityConfiguration(DefaultHandlerExceptionResolver exceptionResolver, RestTemplate restTemplate) {
        this.exceptionResolver = exceptionResolver;
        this.restTemplate = restTemplate;
    }

    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector() {
        return new CustomizedOpaqueTokenIntrospector(restTemplate);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.headers(c -> c.httpStrictTransportSecurity(HeadersConfigurer.HstsConfig::disable));
        http.exceptionHandling(c -> {
            c.accessDeniedHandler((req, res, ex) -> exceptionResolver.resolveException(req, res, null, ex));
            c.authenticationEntryPoint((req, res, ex) -> exceptionResolver.resolveException(req, res, null, ex));
            c.accessDeniedHandler((req, res, ex) -> exceptionResolver.resolveException(req, res, null, ex));
        });
        http.authorizeHttpRequests(c -> {
            // api 文档不需要登录
            c.requestMatchers("/v3/api-docs/**").permitAll();
            c.requestMatchers("/swagger-ui/**").permitAll();
            // 后端接口不需要的登录
            c.requestMatchers("/api/backend/**").permitAll();
            // 剩余所有接口需要登录
            c.anyRequest().authenticated();
        });
        http.oauth2ResourceServer(c -> {
            c.authenticationEntryPoint((req, res, ex) -> exceptionResolver.resolveException(req, res, null, ex));
            c.accessDeniedHandler((req, res, ex) -> exceptionResolver.resolveException(req, res, null, ex));
            c.opaqueToken(otc -> otc.introspector(opaqueTokenIntrospector()));
        });
        return http.build();
    }

}
