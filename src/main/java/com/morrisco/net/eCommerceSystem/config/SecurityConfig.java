package com.morrisco.net.eCommerceSystem.config;

import com.morrisco.net.eCommerceSystem.entities.Role;
import com.morrisco.net.eCommerceSystem.filter.JwtAuthenticationFilter;
import com.morrisco.net.eCommerceSystem.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    private final AuthService authService;
    private JwtAuthenticationFilter filter;
    //security Filter chain defines how HTTP Request are Secured
    @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /**
         * Three Steps To Follow
         * 1.Stateless Session -> Token Based Auth
         * 2.Disable CSRF (Cross Site Request Forgery) attack by tricking browser to send the request without client knowing it
         * 3.Authorize Http Request
         */
        http.sessionManagement(c->
                c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(c->c
                        .requestMatchers("/carts/**").permitAll()
                        .requestMatchers(HttpMethod.POST,"/admin/**").hasRole(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST,"/users").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.POST,"/checkout/webhook").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(c->{
                        c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                        c.accessDeniedHandler((request, response, accessDeniedException) ->
                                response.setStatus(HttpStatus.FORBIDDEN.value()));

                        });


        return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
  }

  @Bean
    public AuthenticationProvider authenticationProvider (){
        var provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(authService);
       return provider;
  }

  @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
   return configuration.getAuthenticationManager();
  }
}
