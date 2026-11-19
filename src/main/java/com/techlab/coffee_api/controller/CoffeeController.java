// Paquete del controlador
package com.techlab.coffee_api.controller;

// Importa la anotación @Configuration
import org.springframework.beans.factory.annotation.Autowired;
// Importa las clases HttpStatus, ResponseEntity
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Importa las clases Coffee y CoffeeService
import com.techlab.coffee_api.model.Coffee;
import com.techlab.coffee_api.service.CoffeeService;

// Importa la clase validar los datos
import jakarta.validation.Valid;

// Importa la clase List
import java.util.List;

// Controlador REST que maneja las peticiones HTTP- GET,get(id) ,POST, PUT, DELETE- PATCH- OPTIONS
@RestController
// @RequestMapping define la ruta base para todas las peticiones de este
// controlador
// En este caso, todas las peticiones a este controlador comenzarán con
// /api/articulos
// http://localhost:8080/api/coffees
@RequestMapping("/api/coffees")
public class CoffeeController {

    // Inyección del servicio
    private final CoffeeService coffeeService;

    // Constructor con inyección para que Spring pueda inyectar el servicio
    // automáticamente cuando se crea una instancia de este controlador
    // Esto permite seguir el principio de inversión de dependencias
    // el coffeeService se inyecta en el controlador y no se crea una instancia
    // directamente dentro del controlador.
    // Qué significa que el controlador no cree una instancia del servicio?
    // Esto permite que el servicio pueda ser fácilmente reemplazado por otro
    // (por ejemplo, un servicio de prueba) sin modificar el controlador.

    // El constructor del controlador recibe un CoffeeService y lo asigna a la
    // variable coffeeService.
    @Autowired
    // "Necesito que me inyectes una instancia del objeto coffeeService
    // automaticamente"
    // En lugar de nosotros instanciar el servicio con new, spring se encarga de
    // crear una instancia del servicio e inyectarla en el controlador.
    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    // GET /api/articulos -> Listar todos los cafés
    @GetMapping
    public ResponseEntity<List<Coffee>> listarCafes() {
        return ResponseEntity.ok(coffeeService.listarCafes());
    }

    // GET /api/coffees/{id} -> Buscar café por Id
    @GetMapping("/{id}")
    // Qué es responseEntity?
    // ResponseEntity es una clase que representa una respuesta HTTP completa,
    // incluyendo el cuerpo, los encabezados y el código de estado.
    // Permite personalizar la respuesta que se envía al cliente.
    // En este caso, devuelve un artículo si lo encuentra o un 404 si no.
    // ¿Por qué usar ResponseEntity?
    // Permite devolver diferentes códigos de estado HTTP y personalizar la
    // respuesta.
    // Por ejemplo, si el artículo no se encuentra, se puede devolver un 404 Not
    // Found.
    // Si se encuentra, se devuelve un 200 OK con el artículo.
    // PathVariable se usa para extraer el ID del artículo de la URL
    public ResponseEntity<Coffee> buscarCafePorId(@PathVariable Long id) {
        // .map(ResponseEntity::ok) convierte el artículo encontrado en una respuesta
        // 200 OK
        // .orElse(ResponseEntity.notFound().build()) devuelve 404 Not Found si no se
        // encuentra el café
        // coffeeService.buscarCafePorId(id) busca el café por Id y devuelve un
        // Optional<Coffee>.
        // Si el café existe, devuelve un ResponseEntity con el café y un código
        // 200 OK.
        // Si no existe, devuelve un ResponseEntity con un código 404 Not Found.
        // Esto permite manejar de manera elegante los casos en que el recurso no se
        // encuentra, sin lanzar excepciones innecesarias.
        // Esto es útil para APIs REST, donde es común que los recursos no existan.
        return coffeeService.buscarCafePorId(id)
                .map(ResponseEntity::ok) // Devuelve 200 si lo encuentra
                .orElse(ResponseEntity.notFound().build()); // Devuelve 404 si no
    }

    // POST /api/coffees -> crear un nuevo café
    @PostMapping
    // @RequestBody indica que el cuerpo de la petición contiene los datos del café
    // que se van a crear. Spring convierte automáticamente el JSON del cuerpo
    // en un objeto Coffee.
    // El método crear recibe un objeto Coffee y lo guarda usando el servicio.
    public ResponseEntity<Coffee> guardarCafe(@Valid @RequestBody Coffee cafe) {
        Coffee nuevoCafe = coffeeService.guardarCafe(cafe);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCafe);
    }

    // PUT /api/coffees/{id} -> actualizar un café existente
    @PutMapping("/{id}")
    public ResponseEntity<Coffee> actualizarCafe(
            @PathVariable Long id,
            @Valid @RequestBody Coffee cafe) {

        return coffeeService.actualizarCafe(id, cafe)
                .map(ResponseEntity::ok) // Si no existe, 404
                .orElse(ResponseEntity.notFound().build()); // Si existe, actualiza y 200
    }

    // DELETE /api/coffees/{id} -> eliminar un café
    @DeleteMapping("/{id}")
    // Elimina un café por Id.
    // Si el café no existe, devuelve 404 Not Found.
    // Si se elimina correctamente, devuelve 204 No Content.
    // El método eliminar recibe el Id del café a eliminar.
    // Si el café no existe, devuelve un ResponseEntity con un código 404 Not Found.
    // Si el café existe, lo elimina y devuelve un ResponseEntity con un código
    // 204 No Content.
    // Esto es útil para APIs REST, donde es común eliminar recursos.
    // porque un 204 No Content?
    // El código 204 indica que la solicitud se ha procesado correctamente,
    // pero no hay contenido que devolver en la respuesta.
    // Es una forma de indicar que la operación se completó con éxito.
    public ResponseEntity<Void> eliminarCafe(@PathVariable Long id) {

        if (!coffeeService.eliminarCafe(id)) {
            return ResponseEntity.notFound().build(); // Si no existe, 404
        }

        return ResponseEntity.noContent().build(); // 204 No Content
    }

}
