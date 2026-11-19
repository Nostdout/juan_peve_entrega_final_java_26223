// Paquete de la configuración de CORS
package com.techlab.coffee_api.config;

// Importa la anotación @Configuration
import org.springframework.context.annotation.Configuration;
// Importa la anotación @Bean
import org.springframework.context.annotation.Bean;
// Importa las clases WebMvcConfigurer y CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

// Indica que esta clase contiene configuraciones de Spring
@Configuration
public class CorsConfig {

    // Registra este método como un bean gestionado por el contenedor de Spring
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Define a qué rutas de tu API aplica la regla
                        .allowedOriginPatterns("*") // Define qué orígenes (frontends) tienen permitido conectarse
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Qué verbos HTTP están permitidos
                        .allowedHeaders("*"); // Qué encabezados (headers) se aceptan en las peticiones
            }
        };
    }

}
