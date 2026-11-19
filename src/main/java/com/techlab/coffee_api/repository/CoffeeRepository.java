// Paquete del repositorio
package com.techlab.coffee_api.repository;

// Importa la clase JpaRepository y Repository
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Importa el modelo de datos Coffee
import com.techlab.coffee_api.model.Coffee;

// Anotación que marca esta clase como componente de repositorio
// Esta interfaz se conecta con la base de datos y maneja operaciones sobre la entidad Coffee
@Repository
// Indica a Spring Data JPA dos cosas:
// Coffee es la entidad con la que vamos a trabajar
// Long es el tipo de dato de su identificador (id)
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    // Se pueen agregar mátodos personalizados quí si son necesarios
    // Al extender JpaRepository, heredamos automáticamente distintos métodos:
    // save(entity), findAll(), findById(id), existsById(id), deleteById(id) y
    // count()
    // Spring Data JPA genera automáticamente la implementación de la interfaz
    // cuando inicia la aplicación.

}