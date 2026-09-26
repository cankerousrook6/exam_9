package kg.attractor.moneytransferapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers(
                                                "/",
                                                "/top-up",
                                                "/register",
                                                "/login",
                                                "/css/**",
                                                "/js/**",
                                                "/h2-console/**"
                                        )
                                        .permitAll()

                                        .requestMatchers(
                                                "/admin/**"
                                        )
                                        .hasRole("ADMIN")

                                        .anyRequest()
                                        .authenticated()
                )

                .formLogin(
                        form ->
                                form
                                        .loginPage("/login")
                                        .loginProcessingUrl("/login")
                                        .usernameParameter("username")
                                        .passwordParameter("password")
                                        .successHandler(
                                                (
                                                        request,
                                                        response,
                                                        authentication
                                                ) -> {

                                                    boolean admin =
                                                            authentication
                                                                    .getAuthorities()
                                                                    .stream()
                                                                    .anyMatch(
                                                                            authority ->
                                                                                    authority
                                                                                            .getAuthority()
                                                                                            .equals(
                                                                                                    "ROLE_ADMIN"
                                                                                            )
                                                                    );

                                                    if (admin) {
                                                        response.sendRedirect(
                                                                "/admin/transactions"
                                                        );
                                                    } else {
                                                        response.sendRedirect(
                                                                "/profile"
                                                        );
                                                    }
                                                }
                                        )
                                        .permitAll()
                )

                .logout(
                        logout ->
                                logout
                                        .logoutSuccessUrl(
                                                "/login?logout"
                                        )
                                        .permitAll()
                )

                .exceptionHandling(
                        exception ->
                                exception
                                        .accessDeniedPage(
                                                "/access-denied"
                                        )
                )

                .csrf(
                        csrf ->
                                csrf
                                        .ignoringRequestMatchers(
                                                "/h2-console/**"
                                        )
                )

                .headers(
                        headers ->
                                headers
                                        .frameOptions(
                                                frame ->
                                                        frame.sameOrigin()
                                        )
                );

        return http.build();
    }
}