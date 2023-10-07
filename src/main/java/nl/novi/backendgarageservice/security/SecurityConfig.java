package nl.novi.backendgarageservice.security;

import nl.novi.backendgarageservice.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepos;

    public SecurityConfig(JwtService jwtService, UserRepository userRepos) {
        this.jwtService = jwtService;
        this.userRepos = userRepos;
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, PasswordEncoder encoder, UserDetailsService udService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(udService)
                .passwordEncoder(encoder)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService(this.userRepos);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .authorizeHttpRequests()
                // --------------------------- USERS ---------------------------
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/{username}").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/users/{username}").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/{username}").hasRole("ADMIN")
                // --------------------------- CARS ---------------------------
                .requestMatchers(HttpMethod.POST, "/cars").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/cars").hasAnyRole("EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/cars/{licensePlate}").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/cars/{licensePlate}").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/cars/{licensePlate}").hasRole("ADMIN")
                // --------------------------- APPOINTMENTS ---------------------------
                .requestMatchers(HttpMethod.POST, "/appointments").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/appointments").hasAnyRole("EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/appointments/{id}").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/appointments/{id}").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/appointments/{id}").hasAnyRole("CUSTOMER", "EMPLOYEE", "ADMIN")
                // --------------------------- REGISTRATIONCARDS ---------------------------
                .requestMatchers("/registration/**").hasAnyRole("EMPLOYEE", "ADMIN")
                // --------------------------- FILES ---------------------------
                .requestMatchers("/files/**").hasAnyRole("EMPLOYEE", "ADMIN")
                // --------------------------- INVOICES ---------------------------
                .requestMatchers("/invoices").hasAnyRole("EMPLOYEE", "ADMIN")
                // --------------------------- REPAIRITEMS ---------------------------
                .requestMatchers("/repairitems/**").hasAnyRole("EMPLOYEE", "ADMIN")
                // --------------------------- ROLES ---------------------------
                .requestMatchers(HttpMethod.GET, "/roles").hasRole("ADMIN")
                // --------------------------- AUTH ---------------------------
                .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/{username}").authenticated()
                .requestMatchers("/**").authenticated()
                .anyRequest().denyAll()
                .and()
                .addFilterBefore(new JwtRequestFilter(jwtService, userDetailsService()), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}
