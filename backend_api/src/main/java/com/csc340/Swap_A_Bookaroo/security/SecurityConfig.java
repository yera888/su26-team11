package com.csc340.Swap_A_Bookaroo.security;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAccountDetailsService userDetailsService;

    public SecurityConfig(CustomAccountDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);

        http
                // This matches the course demo and keeps the existing API/forms easy to test.
                // For production, re-enable CSRF and add CSRF tokens to every modifying form.
                .csrf(AbstractHttpConfigurer::disable)
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/css/**", "/images/**", "/*.jpg", "/*.png", "/*.gif").permitAll()
                        .requestMatchers("/", "/login", "/403", "/error").permitAll()

                        // Public registration endpoints
                        .requestMatchers("/providers/new", "/providers/save").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/provider-profiles").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/customer-profiles").permitAll()

                        // Provider MVC
                        .requestMatchers("/providers/**", "/listings/**").hasAuthority("ROLE_PROVIDER")

                        // Provider APIs
                        .requestMatchers("/api/provider-profiles/**").hasAuthority("ROLE_PROVIDER")
                        .requestMatchers(HttpMethod.POST, "/api/book-listings/**").hasAuthority("ROLE_PROVIDER")
                        .requestMatchers(HttpMethod.PUT, "/api/book-listings/**").hasAuthority("ROLE_PROVIDER")
                        .requestMatchers(HttpMethod.DELETE, "/api/book-listings/**").hasAuthority("ROLE_PROVIDER")
                        .requestMatchers(HttpMethod.PUT, "/api/swap-requests/**").hasAuthority("ROLE_PROVIDER")

                        // Customer actions
                        .requestMatchers(HttpMethod.POST, "/api/swap-requests/listing/**")
                        .hasAuthority("ROLE_CUSTOMER")
                        .requestMatchers("/api/customer-profiles/**").hasAuthority("ROLE_CUSTOMER")

                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
                        .successHandler((request, response, authentication) -> {
                            boolean isProvider = authentication.getAuthorities().stream()
                                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_PROVIDER"));
                            response.sendRedirect(isProvider ? "/providers/me" : "/");
                        })
                        .permitAll())
                .exceptionHandling(exception -> exception.accessDeniedPage("/403"))
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll())
                .requestCache(cache -> cache.requestCache(requestCache));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}