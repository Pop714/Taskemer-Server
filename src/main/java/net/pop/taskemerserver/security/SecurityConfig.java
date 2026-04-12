package net.pop.taskemerserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TaskemerUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(TaskemerUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Encrypt the password
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // 3. Configure the HTTP Security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                // Set up route permissions
                .authorizeHttpRequests(auth -> auth
                        // Allow anyone to hit the users endpoint (so they can register an account!)
                        .requestMatchers("/api/v1/users/**").permitAll()
                        // Every other endpoint (tasks, works, attachments) will require a login
                        .anyRequest().authenticated()
                )
                // Tell Spring to use our Database Provider
                .authenticationProvider(authenticationProvider())
                // Enable Basic Authentication
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
