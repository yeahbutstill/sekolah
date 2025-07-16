package com.hendisantika.sekolah.config;

import com.hendisantika.sekolah.security.AuthenticationFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.PathPatternRequestMatcherBuilderFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.hendisantika.sekolah.enumeration.ALLCONSTANT.LOGIN;

/**
 * Created by IntelliJ IDEA.
 * Project : sekolah
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 17/03/20
 * Time: 21.22
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] PUBLIC_LINK = new String[]{
            "/**"
    };

    private static final String[] PRIVATE_LINK = new String[]{
            "/admin/**"
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationFailureHandler();
    }

    /***
     * Preparing for 7.0
     * This bean is used to create PathPatternRequestMatcher instances.
     * @return PathPatternRequestMatcherBuilderFactoryBean
     */
    @Bean
    PathPatternRequestMatcherBuilderFactoryBean requestMatcherBuilder() {
        return new PathPatternRequestMatcherBuilderFactoryBean();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //you can either disable this or
                //put <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                //inside the login form
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/ignore1", "/ignore2", "/assets/**", "/css/**", "/img/**",
                                "/js**", "/plugins/**", "/theme/**", "/templates/**").permitAll()
                        .requestMatchers(PUBLIC_LINK).permitAll()
                        .requestMatchers(PRIVATE_LINK).hasAnyAuthority("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage(LOGIN.getDescription()) //enable this to go to your own custom login page
                        .loginProcessingUrl(LOGIN.getDescription()) //enable this to use login page provided by spring security
                        .defaultSuccessUrl("/admin/dashboard", true)
                        .failureUrl("/login?error")
                )
                .logout(logout -> {
                            try {
                                logout
                                        .logoutRequestMatcher(requestMatcherBuilder().getObject().matcher("/logout"))
                                        .logoutSuccessUrl("/login?logout");
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                );
        return http.build();
    }

}