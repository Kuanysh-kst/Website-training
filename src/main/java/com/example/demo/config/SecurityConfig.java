package com.example.demo.config;

import com.example.demo.services.MyUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /////////////////////////////////////////////////////////////////
    // example code
    //.requestMatchers(HttpMethod.GET, "/permit-all").permitAll()
    //.requestMatchers("/deny-all").denyAll()
    //.requestMatchers("/anonymous").anonymous()
    //.requestMatchers("/authenticated").authenticated()
    //.requestMatchers("/remember-me").rememberMe()
    //.requestMatchers("/fully-authenticated").fullyAuthenticated()
    //.requestMatchers("/has-view-authority").hasAnyAuthority("view")
    //.requestMatchers("has-update-or-delete-authority").hasAnyAuthority("update", "delete")
    //.requestMatchers("/has-admin-role").hasRole("admin")
    //.requestMatchers("has-customer-or-manager-role").hasAnyRole("customer", "manager")
    //.requestMatchers("/has-access").access((authentication, object) ->
    //new AuthorizationDecision("c.norris".equals(authentication.get().getName())))
    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/welcome").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/new-user").permitAll()
                        .requestMatchers("/api/**").authenticated()
                )
                //.httpBasic(Customizer.withDefaults())
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }
}
