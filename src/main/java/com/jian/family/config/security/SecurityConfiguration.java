package com.jian.family.config.security;

import com.jian.family.config.security.handle.AuthenticationHandler;
import com.jian.family.config.security.handle.AuthenticationLoginConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security,
                                                   LogoutSuccessHandler logoutSuccessHandler) throws Exception {
        return security
                .csrf(CsrfConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/api/authentication/**",
                                    "/actuator")
                            .permitAll()
                            .anyRequest()
                            .authenticated();
                })
                .logout(out -> {
                    out.logoutUrl("/api/authentication/logout")
                            .logoutSuccessHandler(logoutSuccessHandler);
                })
                .build();
    }

    @Bean
    public AuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager,
                                                     AuthenticationConverter authenticationConverter,
                                                     AuthenticationSuccessHandler authenticationSuccessHandler,
                                                     AuthenticationFailureHandler authenticationFailureHandler) {

        AuthenticationFilter filter = new AuthenticationFilter(authenticationManager, authenticationConverter);

        filter.setRequestMatcher(new AntPathRequestMatcher("/api/authentication/login", "POST"));
        filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
        filter.setSuccessHandler(authenticationSuccessHandler);
        filter.setFailureHandler(authenticationFailureHandler);

        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder,
                                                       UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return new ProviderManager(provider);
    }

    @Bean
    public AuthenticationHandler authenticationHandler() {
        return new AuthenticationHandler();
    }

    @Bean
    public AuthenticationLoginConverter authenticationConverter() {
        return new AuthenticationLoginConverter();
    }
}
