// Paquete del servicio
package com.techlab.coffee_api.service;

// Importa las clases Coffee
import com.techlab.coffee_api.model.Coffee;

// Importa las clases List y Optional
import java.util.List;
import java.util.Optional;

// Interfaz que define el contrato del servicio
public interface CoffeeService {

    List<Coffee> listarCafes();

    Optional<Coffee> buscarCafePorId(Long id);

    Coffee guardarCafe(Coffee cafe);

    Optional<Coffee> actualizarCafe(Long id, Coffee cafeActualizado);

    boolean eliminarCafe(Long id);

}