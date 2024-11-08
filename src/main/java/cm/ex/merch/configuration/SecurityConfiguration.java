package cm.ex.merch.configuration;

import cm.ex.merch.security.filter.BasicFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    private BasicFilter basicFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("SecurityConfiguration");
        return http
//                .csrf(AbstractHttpConfigurer::disable)
                .csrf(csrf -> csrf.disable())
                .addFilterBefore(basicFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/test").permitAll()
                        .requestMatchers("/signIn").permitAll()
                        .requestMatchers("/signUp").permitAll()

                        .anyRequest().authenticated()
                )
                .build();
    }
}
