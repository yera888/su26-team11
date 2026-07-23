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
                .csrf(AbstractHttpConfigurer::disable)
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(
                                DispatcherType.FORWARD,
                                DispatcherType.ERROR)
                        .permitAll()
                        .requestMatchers(
                                PathRequest.toStaticResources()
                                        .atCommonLocations())
                        .permitAll()
                        .requestMatchers(
                                "/css/**",
                                "/images/**",
                                "/js/**",
                                "/*.jpg",
                                "/*.png",
                                "/*.gif")
                        .permitAll()
                        .requestMatchers(
                                "/",
                                "/403",
                                "/error")
                        .permitAll()

                        // Public Login & Registration routes
                        .requestMatchers(
                                "/account/customer-login",
                                "/account/provider-login",
                                "/customer/signup",
                                "/providers/new",
                                "/providers/save")
                        .permitAll()

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/provider-profiles",
                                "/api/customer-profiles")
                        .permitAll()

                        // Bridge endpoints: allow existing accounts to switch or enable secondary profiles
                        .requestMatchers("/providers/enable-provider", "/customer/enable-customer")
                        .hasAnyRole("CUSTOMER", "PROVIDER")

                        // Customer browser pages and swap UI routes
                        .requestMatchers("/customer/**", "/swap/**")
                        .hasRole("CUSTOMER")

                        // Provider browser pages (all other provider endpoints require PROVIDER role)
                        .requestMatchers(
                                "/providers/**",
                                "/listings/**")
                        .hasRole("PROVIDER")

                        // Provider REST resources
                        .requestMatchers("/api/provider-profiles/**").hasRole("PROVIDER")
                        .requestMatchers("/api/book-listings/**").hasRole("PROVIDER")
                        .requestMatchers(HttpMethod.GET, "/api/swap-requests/provider/**").hasRole("PROVIDER")
                        .requestMatchers(HttpMethod.PUT, "/api/swap-requests/**").hasRole("PROVIDER")

                        // Customer REST resources
                        .requestMatchers("/api/customer-profiles/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.POST, "/api/swap-requests/listing/**").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/swap-requests/customer/**").hasRole("CUSTOMER")

                        // Shared authenticated routes
                        .requestMatchers("/api/accounts/me").authenticated()

                        .anyRequest().authenticated())
                .formLogin(form -> form
                        // DEFAULT TO PROVIDER LOGIN
                        .loginPage("/account/provider-login")
                        .loginProcessingUrl("/login")
                        .failureUrl("/account/provider-login?error=true")
                        .successHandler((request, response, authentication) -> {
                            String loginType = request.getParameter("loginType");
                            String referer = request.getHeader("Referer");

                            // Redirect according to explicit form parameter or referrer
                            if ("customer".equalsIgnoreCase(loginType) || (referer != null && referer.contains("customer-login"))) {
                                response.sendRedirect("/customer/profile");
                            } else if ("provider".equalsIgnoreCase(loginType) || (referer != null && referer.contains("provider-login"))) {
                                response.sendRedirect("/providers/me");
                            } else {
                                // Fallback role check
                                boolean isProvider = authentication.getAuthorities().stream()
                                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_PROVIDER"));
                                response.sendRedirect(isProvider ? "/providers/me" : "/customer/profile");
                            }
                        })
                        .permitAll())
                .exceptionHandling(exception ->
                        exception.accessDeniedPage("/403"))
                .logout(logout -> logout
                        // LOGOUT REDIRECTS TO PROVIDER LOGIN
                        .logoutSuccessUrl("/account/provider-login?logout=true")
                        .permitAll())
                .requestCache(cache ->
                        cache.requestCache(requestCache));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}