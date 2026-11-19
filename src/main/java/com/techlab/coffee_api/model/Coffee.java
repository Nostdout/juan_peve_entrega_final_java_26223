// Paquete que contiene la clase de modelo
package com.techlab.coffee_api.model;

// Importa la clases para definir la tabla y validar los datos
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

// Indica que la clase representa una entidad
@Entity
// establece el nombre de la tabla en MySQL
@Table(name = "coffees")
// Clase que representa un café
public class Coffee {

    // Identifica la clave primaria
    @Id
    // Permite generar automáticamente el Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Impide nombres vacíos o compuestos solamente por espacios
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
    // Define las características en la base de datos
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank(message = "El tamaño es obligatorio")
    @Size(max = 50, message = "El tamaño no puede superar los 50 caracteres")
    @Column(nullable = false, length = 50)
    private String tamano;

    @NotNull(message = "El precio es obligatorio")
    // Exige que el precio sea mayor a cero
    @Positive(message = "El precio debe ser mayor a cero")
    @Column(nullable = false)
    private Double precio;

    @NotNull(message = "El stock es obligatorio")
    // Exige que el stock sea mayor o igual a cero
    @PositiveOrZero(message = "El stock debe ser mayor o igual a cero")
    @Column(nullable = false)
    private Integer stock;

    // Constructor vacío requerido por JPA
    public Coffee() {
    }

    // Constructor con parámetros
    public Coffee(String nombre, String tamano, Double precio, Integer stock) {
        this.nombre = nombre;
        this.tamano = tamano;
        this.precio = precio;
        this.stock = stock;
    }

    // Métodos getter y setter para acceder y modificar los atributos
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

}
