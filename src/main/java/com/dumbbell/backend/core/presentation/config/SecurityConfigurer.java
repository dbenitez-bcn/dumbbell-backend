package com.dumbbell.backend.core.presentation.config;

import com.dumbbell.backend.core.presentation.filters.AuthenticationFilter;
import com.dumbbell.backend.core.presentation.filters.TokenValidationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    private final TokenValidationFilter tokenValidationFilter;
    private final AuthenticationFilter authenticationFilter;
    private final CorsConfigurer corsConfigurer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().configurationSource(corsConfigurer)
                .and()
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(tokenValidationFilter, AuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/login", "/register", "/admin/login").permitAll()
                .antMatchers(HttpMethod.GET, "/exercises", "/exercise/**").authenticated()
                .antMatchers(HttpMethod.POST, "/exercise").hasAnyAuthority("ADMIN", "OPERATOR")
                .antMatchers(HttpMethod.PUT, "/exercise/**").hasAnyAuthority("ADMIN", "OPERATOR")
                .antMatchers(HttpMethod.DELETE, "/exercise/**").hasAnyAuthority("ADMIN", "OPERATOR")
                .antMatchers("/toggle").hasAnyAuthority("ADMIN")
                .antMatchers("/").authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
