package org.hospital.modetravail.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(
                                        "/auth/**",
                                        "/v3/api-docs",
                                        "/v3/api-docs/**",
                                        "/swagger-resources",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**",
                                        "/swagger-ui.html",
                                        "/increment-modetravail",
                                        "/cree-equipe",
                                        "/norme_productivite",
                                "/maintheorique",
                                "/modes",
                                "/maintheorique/*",
                                "/cree-maintheorique",
                                "/cree-normeproductivite",
                                "/cree-shiftPlan",
                                "/employes/by-fonction/*",
                                "/equipes",
                                "/fonctions",
                                "/norme-productivite/*",
                                "/periode-Ramadan",
                                "/shifts",
                                "/shifts/*",
                                "/modedetravail",
                                        "/typetrafic",
                                "/typetrafic/trafic/*",
                                "/equipementfamille/equipement/*",
                                        "/equipementfamille/accessoir/{id}",
                                        "/equipementfamille",
                                        "equipes/{nom}",
                                "/shiftplan/{id}",
                                "/exist/*",
                                "/employes",
                                        "employes/delete/{id}",
                                        "/cree_employe",
                                        "/employes/{id}"
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .oauth2ResourceServer(auth ->
                        auth.jwt(token -> token.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())));


        return http.build();
    }
}
