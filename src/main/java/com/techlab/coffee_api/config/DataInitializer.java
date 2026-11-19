// Paquete de la configuración de pre-carga de datos
package com.techlab.coffee_api.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
// Importa la anotación @Bean
import org.springframework.context.annotation.Bean;
// Importa la anotación @Configuration
import org.springframework.context.annotation.Configuration;

// Importa las clases Coffee y CoffeeRepository
import com.techlab.coffee_api.model.Coffee;
import com.techlab.coffee_api.repository.CoffeeRepository;

// Indica que esta clase contiene configuraciones de Spring
@Configuration
public class DataInitializer {

    // Registra este método como un bean gestionado por el contenedor de Spring
    @Bean
    // Permite ejecutar código automáticamente cuando Spring Boot termina de iniciar
    // la aplicación
    CommandLineRunner initDatabase(CoffeeRepository coffeeRepository) {

        return args -> {

            // Evitamos cargar los datos si ya existen cafes
            if (coffeeRepository.count() > 0) {
                return;
            }

            Coffee coffee1 = new Coffee();
            coffee1.setNombre("Espresso");
            coffee1.setTamano("Chico (120 ml)");
            coffee1.setPrecio(2500.00);
            coffee1.setStock(4);

            Coffee coffee2 = new Coffee();
            coffee2.setNombre("Cappuccino");
            coffee2.setTamano("Mediano (240 ml)");
            coffee2.setPrecio(3500.00);
            coffee2.setStock(20);

            Coffee coffee3 = new Coffee();
            coffee3.setNombre("Latte");
            coffee3.setTamano("Mediano (240 ml)");
            coffee3.setPrecio(3200.00);
            coffee3.setStock(25);

            Coffee coffee4 = new Coffee();
            coffee4.setNombre("Americano");
            coffee4.setTamano("Grande (360 ml)");
            coffee4.setPrecio(2800.00);
            coffee4.setStock(10);

            Coffee coffee5 = new Coffee();
            coffee5.setNombre("Mocha");
            coffee5.setTamano("Mediano (240 ml)");
            coffee5.setPrecio(3800.00);
            coffee5.setStock(0);

            coffeeRepository.saveAll(
                    List.of(coffee1, coffee2, coffee3, coffee4, coffee5));

            System.out.println("Se cargaron 5 cafes iniciales.");
        };
    }

}
