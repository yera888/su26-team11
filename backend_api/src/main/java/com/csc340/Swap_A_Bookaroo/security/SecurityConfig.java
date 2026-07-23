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
                // This follows the course demo. A production deployment should
                // re-enable CSRF and include CSRF tokens in modifying forms.
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
                                "/*.jpg",
                                "/*.png",
                                "/*.gif")
                        .permitAll()
                        .requestMatchers(
                                "/",
                                "/login",
                                "/403",
                                "/error")
                        .permitAll()

                        // Public registration routes.
                        .requestMatchers(
                                "/providers/new",
                                "/providers/save")
                        .permitAll()
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/provider-profiles",
                                "/api/customer-profiles")
                        .permitAll()

                        // Provider browser pages.
                        .requestMatchers(
                                "/providers/**",
                                "/listings/**")
                        .hasRole("PROVIDER")

                        // Provider REST resources.
                        .requestMatchers("/api/provider-profiles/**")
                        .hasRole("PROVIDER")
                        .requestMatchers("/api/book-listings/**")
                        .hasRole("PROVIDER")
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/swap-requests/provider/**")
                        .hasRole("PROVIDER")
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/swap-requests/**")
                        .hasRole("PROVIDER")

                        // Customer REST resources.
                        .requestMatchers("/api/customer-profiles/**")
                        .hasRole("CUSTOMER")
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/swap-requests/listing/**")
                        .hasRole("CUSTOMER")
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/swap-requests/customer/**")
                        .hasRole("CUSTOMER")

                        // Account /me is available to either authenticated role.
                        .requestMatchers("/api/accounts/me")
                        .authenticated()

                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
                        .successHandler((request, response, authentication) -> {
                            boolean provider = authentication.getAuthorities()
                                    .stream()
                                    .anyMatch(authority ->
                                            authority.getAuthority()
                                                    .equals("ROLE_PROVIDER"));

                            response.sendRedirect(
                                    provider ? "/providers/me" : "/");
                        })
                        .permitAll())
                .exceptionHandling(exception ->
                        exception.accessDeniedPage("/403"))
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true")
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
