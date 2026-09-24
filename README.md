# Taller Práctico: Sistema de Gestión para "SuperCines"

---

## 1. Contexto del Problema

La cadena de cines **SuperCines** necesita modernizar su sistema interno de control de cartelera para optimizar la gestión de sus películas, control de tiempos, manejo de colecciones y la administración de funciones especiales. Para ello, se te solicita desarrollar una aplicación en consola en Java que aplique todos los principios de la Programación Orientada a Objetos (POO).

---

## 2. Requerimientos Estructurales y de Clases

### A. Clase Abstracta `Pelicula` (Superclase)
Debe contener los siguientes atributos encapsulados (`private`):
*   `titulo` (String)
*   `duracionEnMinutos` (int)
*   `genero` (String)
*   `anioEstreno` (int)
*   `aptoTodoPublico` (boolean) *(Propiedad booleana requerida para indicar si la película es apta para todo espectador)*
*   `puntaje` (double) *(Calificación de la película entre 0 y 10, se ingresa con `solicitarNumeroDecimal`)*

**Requisitos de la superclase:**
*   **Diccionario de Géneros:** El género de la película **no** se ingresa de forma libre. Debe validarse obligatoriamente utilizando el diccionario provisto en la clase de utilidades. Las opciones permitidas son:
    *   `1: Ciencia Ficción`
    *   `2: Drama`
    *   `3: Terror`
    *   `4: Comedia`
    *   `5: Acción`
*   Constructores, getters y setters. En NetBeans se generan automáticamente con clic derecho → *Insert Code* (Alt+Insert). Las validaciones se agregan dentro de los setters o el constructor.
*   **Validaciones obligatorias:** la duración debe ser mayor a 0 y el género debe pertenecer al diccionario.
*   **Validaciones opcionales (no obligatorias para el taller):** año de estreno mayor a 0, puntaje entre 0 y 10.
*   Método abstracto: `public abstract double calcularCostoEntradaBase();`.

### B. Interfaz `FuncionEspecial`
*   Debe definir al menos un contrato, por ejemplo: `public void reproducirTrailerExclusivo();`.

### C. Clases Hijas (Herencia y Polimorfismo)
*   `PeliculaEstreno`: Extiende de `Pelicula` e **implementa** la interfaz `FuncionEspecial`. Agrega atributos propios (ej: `calidadProyeccion` como IMAX o 4DX). Costo base: `$6.000` (IMAX suma `$1.000`, 4DX suma `$1.500`).
*   `PeliculaReEstreno`: Extiende de `Pelicula`. Agrega un atributo como `anioOriginalEstreno`. Costo base: `$3.500`.
*   `PeliculaNormal`: Extiende de `Pelicula` para el catálogo estándar sin recargos. Costo base: `$4.000`.

### D. Estructura de Datos
*   Todas las películas ingresadas deben almacenarse en un `ArrayList<Pelicula>`.
*   **Regla de negocio:** No se permiten películas con el mismo título exacto (impedir duplicados por nombre ignorando mayúsculas/minúsculas).

---

## 3. Menú de Opciones Requerido

El sistema debe operar en un ciclo interactivo validando las opciones mediante la consola:

```text
=== SISTEMA DE GESTIÓN SUPERCINES ===
1. Ingresar película (Validar duplicados y usar el diccionario de géneros)
2. Eliminar película por nombre
3. Buscar película por nombre
4. Listar todas las películas (mostrar resumen: minutos totales en minutos, la película más larga y la cantidad de estrenos)
5. Cambiar título, duración y propiedad booleana de la película *(BONIFICACIÓN, opcional)*
6. Salir

```

**Detalle de la opción 1:** al ingresar una película se debe solicitar primero el tipo (1. Estreno, 2. Re-Estreno, 3. Normal) y luego los atributos propios de cada tipo: `calidadProyeccion` para estrenos (1. IMAX, 2. 4DX) y `anioOriginalEstreno` para re-estrenos.

**Detalle de la opción 5 (BONIFICACIÓN):** es opcional. Reutiliza la búsqueda por nombre (ejemplo §5C) y los setters generados por NetBeans (`setTitulo`, `setDuracionEnMinutos`, `setAptoTodoPublico`). No es necesaria para el taller.

---

## 4. Clase de Utilidades (`UtilidadesCine`)

Utiliza esta clase base provista para gestionar las entradas por consola de forma segura mediante `throws` y excepciones en tiempo de ejecución:

```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UtilidadesCine {
    private static final Scanner scanner = new Scanner(System.in);

    // Método para mostrar mensajes por pantalla de forma limpia
    public static void mostrarMensaje(String mensaje) {
        System.out.println("[SuperCines]: " + mensaje);
    }

    // Diccionario de géneros obligatorio
    public static Map<Integer, String> obtenerDiccionarioGeneros() {
        Map<Integer, String> generos = new HashMap<>();
        generos.put(1, "Ciencia Ficción");
        generos.put(2, "Drama");
        generos.put(3, "Terror");
        generos.put(4, "Comedia");
        generos.put(5, "Acción");
        return generos;
    }

    // Solicitar Texto (String) con throws
    public static String solicitarTexto(String etiqueta) throws Exception {
        mostrarMensaje(etiqueta);
        String texto = scanner.nextLine();
        if (texto == null || texto.trim().isEmpty()) {
            throw new Exception("El texto ingresado no puede estar vacío.");
        }
        return texto.trim();
    }

    // Solicitar Número Entero (int) con throws
    public static int solicitarNumeroEntero(String etiqueta) throws Exception {
        mostrarMensaje(etiqueta);
        try {
            int numero = Integer.parseInt(scanner.nextLine());
            return numero;
        } catch (NumberFormatException e) {
            throw new Exception("Error: Debe ingresar un número entero válido.");
        }
    }

    // Solicitar Número Decimal (double) con throws
    public static double solicitarNumeroDecimal(String etiqueta) throws Exception {
        mostrarMensaje(etiqueta);
        try {
            double decimal = Double.parseDouble(scanner.nextLine());
            return decimal;
        } catch (NumberFormatException e) {
            throw new Exception("Error: Debe ingresar un número decimal válido.");
        }
    }

    // Solicitar Booleano con throws
    public static boolean solicitarBooleano(String etiqueta) throws Exception {
        mostrarMensaje(etiqueta + " (true/false):");
        String entrada = scanner.nextLine().trim().toLowerCase();
        if (entrada.equals("true") || entrada.equals("1") || entrada.equals("sí") || entrada.equals("si")) {
            return true;
        } else if (entrada.equals("false") || entrada.equals("0") || entrada.equals("no")) {
            return false;
        } else {
            throw new Exception("Error: Valor booleano no válido. Ingrese true o false.");
        }
    }
}

```

---

## 5. Ejemplos de Código para las Operaciones con el `ArrayList`

Utiliza los siguientes fragmentos como guía para resolver las operaciones críticas del menú:

### A. Agregar genero de la pelicula

```java
// Mostrar diccionario de géneros
Map<Integer, String> generos = UtilidadesCine.obtenerDiccionarioGeneros();
UtilidadesCine.mostrarMensaje("Seleccione el género:");
for (Map.Entry<Integer, String> entry : generos.entrySet()) {
    System.out.println(entry.getKey() + ". " + entry.getValue());
}
int opcionGenero = UtilidadesCine.solicitarNumeroEntero("Ingrese el número del género:");

if (!generos.containsKey(opcionGenero)) {
    throw new Exception("Opción de género inválida.");
}
String generoSeleccionado = generos.get(opcionGenero);
```

### B. Eliminar por Nombre

```java
// OPCIÓN 2: Eliminar película por nombre
String tituloEliminar = UtilidadesCine.solicitarTexto("Ingrese el título de la película a eliminar:");
boolean removido = cartelera.removeIf(p -> p.getTitulo().equalsIgnoreCase(tituloEliminar));
if (removido) {
    UtilidadesCine.mostrarMensaje("Película eliminada correctamente.");
} else {
    UtilidadesCine.mostrarMensaje("No se encontró ninguna película con ese nombre.");
}
break;

```

### C. Buscar por nombre

```java
// OPCIÓN 3: Buscar película por nombre
String tituloOriginal = UtilidadesCine.solicitarTexto("Ingrese el título de la película a buscar:");
Pelicula peliculaEncontrada = null;
for (Pelicula p : cartelera) {
    if (p.getTitulo().equalsIgnoreCase(tituloOriginal)) {
        peliculaEncontrada = p;
        break;
    }
}
// Aquí ya se puede modificar la pelicula en la variable peliculaEncontrada si es que no es null

```

### D. Validar nombre repetido

```java
// Validar duplicados por nombre
boolean existe = false;
for (Pelicula p : cartelera) {
    if (p.getTitulo().equalsIgnoreCase(tituloNuevo)) {
        existe = true;
        break;
    }
}

if (existe) {
    UtilidadesCine.mostrarMensaje("Error: Ya existe una película con ese título.");
    break;
}
```

### E. Seleccionar tipo de película y calidad de proyección

```java
// OPCIÓN 1: Solicitar el tipo de película
System.out.println("1. Estreno");
System.out.println("2. Re-estreno");
System.out.println("3. Normal");
int tipo = UtilidadesCine.solicitarNumeroEntero("Ingrese el tipo de película:");

// Solo para estrenos: calidad de proyección
System.out.println("1. IMAX");
System.out.println("2. 4DX");
int opcion = UtilidadesCine.solicitarNumeroEntero("Ingrese la calidad de proyección:");
String calidad;
if (opcion == 1) {
    calidad = "IMAX";
} else if (opcion == 2) {
    calidad = "4DX";
} else {
    throw new Exception("Opción de calidad inválida.");
}
```

---

## 6. Tips NetBeans y Plan de Trabajo (máximo 2,5 horas)

**Tips NetBeans (te ahorran tiempo):**
*   Clic derecho sobre la clase → **Insert Code** (o `Alt+Insert`) → *Constructor*, *Getter and Setter*, *toString()*.
*   Con los atributos ya declarados, esos tres se generan en segundos; solo debes escribir las **validaciones** y la lógica de costo.
*   La clase `UtilidadesCine` se entrega lista: **no la modifiques**.

**Plan de trabajo sugerido:**

| Tiempo | Tarea |
|---|---|
| 0–10 min | Leer el enunciado, crear el proyecto y copiar `UtilidadesCine`. |
| 10–40 min | Clase `Pelicula`: declarar los 6 atributos, generar constructor/getters/setters, validar duración (>0) y género (diccionario), y declarar el método abstracto. |
| 40–75 min | Interfaz `FuncionEspecial` + 3 clases hijas: herencia (`extends`), una implementa la interfaz (`implements`), atributo extra por tipo y `calcularCostoEntradaBase()` con los costos de la sección §2C. |
| 75–140 min | Clase `Cine`: ciclo `while` + `switch` con el menú de la sección §3 y adaptar los ejemplos §5A–§5E para ingresar, eliminar, buscar y listar. |
| 140–150 min | Probar el programa con 3–4 películas (un estreno, un re-estreno y una normal) y corregir. |
