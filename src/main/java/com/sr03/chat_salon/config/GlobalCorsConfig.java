package com.sr03.chat_salon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class GlobalCorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        // Ajouter les informations de configuration CORS
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // Autoriser l'envoi des cookies
        corsConfiguration.setAllowCredentials(true);
        // Autoriser toutes les origines
        corsConfiguration.addAllowedOrigin("*");
        // Autoriser tous les en-têtes de requête d'origine
        corsConfiguration.addAllowedHeader("*");
        // Autoriser toutes les méthodes de requête
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedMethod("OPTIONS");
        // Ajouter la source de configuration basée sur les URL
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Enregistrer la configuration CORS pour tous les chemins d'accès
        source.registerCorsConfiguration("/**", corsConfiguration);
        // Retourner un nouveau CorsFilter
        return new CorsFilter(source);
    }
}
