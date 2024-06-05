package stevenlan.bookstore.jwt.cofig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import stevenlan.bookstore.jwt.filter.JwtAuthenticationFilter;
import stevenlan.bookstore.serviceImpl.EmployeesDetailsService;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final EmployeesDetailsService empServiceImp;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomLogoutHandler customLogoutHandler;
    
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    

    private final static String[] WHITE_LISTS_URL = {"/v1/login/**","/v1/employeesregister/**"
    ,"/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html","/swagger-ui.html/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req->req.requestMatchers(WHITE_LISTS_URL)
                                .permitAll()
                                .requestMatchers(
                                    "/admin_only/**",
                                                "/v1/adminregister/**")
                                                .hasAuthority("ADMIN")
                                .anyRequest()
                                .authenticated()
                )
                .userDetailsService(empServiceImp)
                .exceptionHandling(e -> e.accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setCharacterEncoding("UTF-8");
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("無效的驗證碼,請重新輸入或登入");
                        }))
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(l->l.logoutUrl("/v1/logout")
                        .addLogoutHandler(customLogoutHandler)
                        .logoutSuccessHandler(
                            (requset, response, authentication) -> SecurityContextHolder.clearContext()
                        )
                )
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}



