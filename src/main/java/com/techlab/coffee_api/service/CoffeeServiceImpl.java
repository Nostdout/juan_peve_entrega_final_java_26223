// Paquete de la implementación del servicio
package com.techlab.coffee_api.service;

// Importa las clases Autowired, Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Importa las clases Coffee y CoffeeRepository
import com.techlab.coffee_api.model.Coffee;
import com.techlab.coffee_api.repository.CoffeeRepository;

// Importa las clases List y Optional
import java.util.List;
import java.util.Optional;

// Anotación que indica que esta clase es un servicio de Spring
@Service
public class CoffeeServiceImpl implements CoffeeService {

    // Inyección del repositorio que maneja los datos
    private final CoffeeRepository coffeeRepository;

    @Autowired
    // Constructor con @Autowired para inyectar dependencias
    public CoffeeServiceImpl(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    // Listar todos los cafes
    public List<Coffee> listarCafes() {
        return coffeeRepository.findAll();
    }

    // Buscar café por Id
    // que es Optional?
    // Optional es una clase que representa un valor que puede o no estar presente.
    // Se usa para evitar NullPointerExceptions al trabajar con valores que pueden
    // ser nulos.
    // En este caso, devuelve un Optional<Coffee> que puede contener un café
    // si se encuentra, o estar vacío si no se encuentra.
    // ¿Por qué usar Optional?
    // Permite manejar de manera segura la ausencia de un valor sin usar null.
    // Esto es útil para evitar errores comunes de programación relacionados con
    // null.
    public Optional<Coffee> buscarCafePorId(Long id) {
        return coffeeRepository.findById(id);
    }

    // Guardar un nuevo café
    public Coffee guardarCafe(Coffee cafe) {
        return coffeeRepository.save(cafe);
    }

    // Actualizar un café
    public Optional<Coffee> actualizarCafe(Long id, Coffee cafeActualizado) {
        // Buscamos el café por su Id
        // Si existe, actualizamos sus atributos
        // Guardamos los cambios
        // Si no existe, el método devuelve vacio
        return coffeeRepository.findById(id).map(cafe -> {

            cafe.setNombre(cafeActualizado.getNombre());
            cafe.setTamano(cafeActualizado.getTamano());
            cafe.setPrecio(cafeActualizado.getPrecio());
            cafe.setStock(cafeActualizado.getStock());

            return coffeeRepository.save(cafe);
        });
    }

    // Eliminar un café
    public boolean eliminarCafe(Long id) {
        // verdadero: el café existía y fue eliminado
        // falso: no encontramos ningún café con ese Id
        if (!coffeeRepository.existsById(id)) {
            return false;
        }

        coffeeRepository.deleteById(id);

        return true;
    }

}
