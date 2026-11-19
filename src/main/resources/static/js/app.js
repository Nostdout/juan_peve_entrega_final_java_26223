const API_URL = "http://localhost:8080/api/coffees";

// Referencias a elementos del DOM
const tablaBody = document.getElementById("tablaCafesBody");
const formCafe = document.getElementById("formCafe");
const modalElement = document.getElementById("modalCafe");
const modalBootstrap = new bootstrap.Modal(modalElement);
const modalTitulo = document.getElementById("modalCafeLabel");
const btnNuevoCafe = document.getElementById("btnNuevoCafe");

// Inputs del formulario
const inputId = document.getElementById("cafeId");
const inputNombre = document.getElementById("cafeNombre");
const inputTamano = document.getElementById("cafeTamano");
const inputPrecio = document.getElementById("cafePrecio");
const inputStock = document.getElementById("cafeStock");

// Carga la lista de cafés apenas abre la página
document.addEventListener("DOMContentLoaded", cargarCafes);

// Prepara el modal cuando se hace clic en "+ Nuevo Café"
btnNuevoCafe.addEventListener("click", () => {
  formCafe.reset();
  inputId.value = "";
  modalTitulo.textContent = "Agregar Nuevo Café";
});

// Obtiene todos los cafés desde la base de datos
async function cargarCafes() {
  // Llamada GET a la API para obtener todos los artículos
  try {
    const respuesta = await fetch(API_URL);
    if (!respuesta.ok) {
      throw new Error(`Error al obtener cafés (HTTP ${respuesta.status})`);
    }

    const cafes = await respuesta.json(); // Convertimos la respuesta a JSON
    renderizarTabla(cafes); // Llamamos a la función para renderizar la tabla
  } catch (error) {
    console.error(error);
    tablaBody.innerHTML = `
      <tr>
        <td colspan="6" class="text-center text-danger py-4">
          Error al conectar con el servidor. Verificá que Spring Boot esté ejecutándose.
        </td>
      </tr>`;
  }
}

// Dibuja las filas en la tabla
function renderizarTabla(cafes) {
  if (!cafes || cafes.length === 0) {
    tablaBody.innerHTML = `
      <tr>
        <td colspan="6" class="text-center text-muted py-4">
          No hay cafés cargados todavía. Hacé clic en "Nuevo Café" para agregar el primero.
        </td>
      </tr>`;
    return;
  }

  tablaBody.innerHTML = cafes
    .map((cafe) => {
      // Badge dinámico según nivel de stock
      let badgeStock = `<span class="badge bg-success-subtle text-success border border-success-subtle">${cafe.stock} unid.</span>`;
      if (cafe.stock === 0) {
        badgeStock = `<span class="badge bg-danger-subtle text-danger border border-danger-subtle">Sin stock</span>`;
      } else if (cafe.stock <= 5) {
        badgeStock = `<span class="badge bg-warning-subtle text-warning-emphasis border border-warning-subtle">${cafe.stock} unid.</span>`;
      }

      // Formateo de precio en pesos argentinos
      const precioFormateado = new Intl.NumberFormat("es-AR", {
        style: "currency",
        currency: "ARS",
        minimumFractionDigits: 2,
      }).format(cafe.precio);

      return `
      <tr>
        <td class="ps-4 text-muted">#${cafe.id}</td>
        <td class="fw-semibold">${cafe.nombre}</td>
        <td><span class="badge bg-secondary">${cafe.tamano}</span></td>
        <td>${precioFormateado}</td>
        <td>${badgeStock}</td>
        <td class="d-flex flex-wrap justify-content-center gap-2 pe-4">
          <button class="btn btn-sm btn-outline-primary" title="Editar"><i class="bi bi-pencil" onclick="abrirModalEditar(${cafe.id})"></i></button>
          <button class="btn btn-sm btn-outline-danger" title="Eliminar"><i class="bi bi-trash" onclick="eliminarCafe(${cafe.id}, '${cafe.nombre}')"></i></button>
        </td>
      </tr>
    `;
    })
    .join("");
}

// Carga los datos de un café en el modal para editarlo
async function abrirModalEditar(id) {
  // Llamada GET para obtener los datos del artículo por su Id
  try {
    const respuesta = await fetch(`${API_URL}/${id}`);
    if (!respuesta.ok) {
      throw new Error("No se encontró el café seleccionado");
    }

    const cafe = await respuesta.json(); // Convertimos la respuesta a JSON

    document.getElementById("cafeId").value = cafe.id;
    document.getElementById("cafeNombre").value = cafe.nombre;
    document.getElementById("cafeTamano").value = cafe.tamano;
    document.getElementById("cafePrecio").value = cafe.precio;
    document.getElementById("cafeStock").value = cafe.stock;

    modalTitulo.textContent = `Editar Café #${cafe.id}`;
    modalBootstrap.show();
  } catch (error) {
    console.error(error);
    alert("Error al cargar los datos del café para editar."); // Manejo de errores
  }
}

// Maneja el envío del formulario
formCafe.addEventListener("submit", async (e) => {
  // Evitamos el comportamiento por defecto del formulario
  e.preventDefault();
  // Obtenemos los valores de los campos del formulario
  const id = document.getElementById("cafeId").value;
  const nombre = document.getElementById("cafeNombre").value.trim();
  const tamano = document.getElementById("cafeTamano").value.trim();
  const precio = parseFloat(document.getElementById("cafePrecio").value);
  const stock = parseInt(document.getElementById("cafeStock", 10).value);
  // Validación de campos
  if (!nombre) {
    alert("Por favor ingrese el nombre del café.");
    return;
  }
  if (nombre.length >= 50) {
    alert("El nombre del café no puede ser mayor a 50 caracteres.");
    return;
  }
  if (!tamano) {
    alert("Por favor ingrese el tamaño del café.");
    return;
  }
  if (tamano.length >= 50) {
    alert("El tamaño del café no puede ser mayor a 50 caracteres.");
    return;
  }
  if (isNaN(precio) || precio <= 0) {
    alert("El precio debe ser mayor a 0.");
    return;
  }
  if (isNaN(stock) || precio < 0) {
    alert("El stock debe ser mayor o igual a 0.");
    return;
  }
  // Creamos un objeto artículo con los datos del formulario
  const datosCafe = { nombre, tamano, precio, stock };
  // Determinamos si es una edición (PUT) o creación (POST)
  try {
    const esEdicion = Boolean(id);
    const url = esEdicion ? `${API_URL}/${id}` : API_URL;
    const metodo = esEdicion ? "PUT" : "POST";
    // Enviamos el artículo al backend usando fetch
    const respuesta = await fetch(url, {
      method: metodo,
      headers: { "Content-Type": "application/json" }, // Indicamos que el cuerpo es JSON
      body: JSON.stringify(datosCafe), // Convertimos el objeto a JSON
    });
    if (!respuesta.ok) {
      throw new Error(`Error al guardar el café (HTTP ${respuesta.status})`); // Manejo de errores
    }
    modalBootstrap.hide();
    await cargarCafes(); // Refresca la tabla con los cambios
  } catch (error) {
    console.error(error);
    alert("No se pudo guardar el café. Revisá la consola para más detalles.");
  }
});

// Elimina un café
async function eliminarCafe(id, nombre) {
  // Confirmación antes de eliminar
  const confirmado = confirm(
    `¿Seguro que querés eliminar "${nombre}" del catálogo?`,
  );
  if (!confirmado) return;
  // Llamada DELETE al backend
  try {
    const respuesta = await fetch(`${API_URL}/${id}`, {
      method: "DELETE",
    });
    if (!respuesta.ok) {
      throw new Error(`Error al eliminar (HTTP ${respuesta.status})`); // Verificamos que la respuesta sea exitosa
    }
    await cargarCafes(); // Actualizamos la lista de artículos
  } catch (error) {
    console.error(error);
    alert("No se pudo eliminar el café."); // Manejo de errores
  }
}
