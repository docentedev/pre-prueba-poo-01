package com.duoc.cine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Cine {

    private static final List<Pelicula> cartelera = new ArrayList<>();

    public static void main(String[] args) {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            try {
                int opcion = UtilidadesCine.solicitarNumeroEntero("Seleccione una opción:");
                switch (opcion) {
                    case 1 -> ingresarPelicula();
                    case 2 -> eliminarPelicula();
                    case 3 -> buscarPelicula();
                    case 4 -> listarPeliculas();
                    case 5 -> modificarPelicula();
                    case 6 -> salir = true;
                    default -> UtilidadesCine.mostrarMensaje("Opción no válida.");
                }
            } catch (Exception e) {
                UtilidadesCine.mostrarMensaje("Error: " + e.getMessage());
            }
        }
        UtilidadesCine.mostrarMensaje("Fin del programa.");
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("=== SISTEMA DE GESTIÓN SUPERCINES ===");
        System.out.println("1. Ingresar película (Validar duplicados y usar el diccionario de géneros)");
        System.out.println("2. Eliminar película por nombre");
        System.out.println("3. Buscar película por nombre");
        System.out.println("4. Listar todas las películas (mostrar resumen: minutos totales en minutos, la película más larga y la cantidad de estrenos)");
        System.out.println("5. Cambiar título, duración y propiedad booleana de la película (BONIFICACIÓN, opcional)");
        System.out.println("6. Salir");
    }

    private static void ingresarPelicula() throws Exception {
        String titulo = UtilidadesCine.solicitarTexto("Ingrese el título de la película:");
        if (existeTitulo(titulo)) {
            UtilidadesCine.mostrarMensaje("Error: Ya existe una película con ese título.");
            return;
        }

        UtilidadesCine.mostrarMensaje("Seleccione el tipo de película:");
        System.out.println("1. Estreno");
        System.out.println("2. Re-estreno");
        System.out.println("3. Normal");
        int tipo = UtilidadesCine.solicitarNumeroEntero("Ingrese el tipo de película:");

        int duracion = UtilidadesCine.solicitarNumeroEntero("Ingrese la duración en minutos:");
        String genero = solicitarGenero();
        int anioEstreno = UtilidadesCine.solicitarNumeroEntero("Ingrese el año de estreno:");
        boolean apto = UtilidadesCine.solicitarBooleano("¿Es apta para todo público?");
        double puntaje = UtilidadesCine.solicitarNumeroDecimal("Ingrese el puntaje de la película:");

        Pelicula nueva;
        switch (tipo) {
            case 1 -> {
                String calidad = solicitarCalidadProyeccion();
                nueva = new PeliculaEstreno(titulo, duracion, genero, anioEstreno, apto, puntaje, calidad);
            }
            case 2 -> {
                int anioOriginal = UtilidadesCine.solicitarNumeroEntero("Ingrese el año de estreno original:");
                nueva = new PeliculaReEstreno(titulo, duracion, genero, anioEstreno, apto, puntaje, anioOriginal);
            }
            case 3 -> nueva = new PeliculaNormal(titulo, duracion, genero, anioEstreno, apto, puntaje);
            default -> throw new Exception("Tipo de película inválido.");
        }

        cartelera.add(nueva);
        UtilidadesCine.mostrarMensaje("Película ingresada correctamente.");
        if (nueva instanceof FuncionEspecial funcionEspecial) {
            funcionEspecial.reproducirTrailerExclusivo();
        }
    }

    private static String solicitarGenero() throws Exception {
        Map<Integer, String> generos = UtilidadesCine.obtenerDiccionarioGeneros();
        UtilidadesCine.mostrarMensaje("Seleccione el género:");
        for (Map.Entry<Integer, String> entry : generos.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
        int opcionGenero = UtilidadesCine.solicitarNumeroEntero("Ingrese el número del género:");
        if (!generos.containsKey(opcionGenero)) {
            throw new Exception("Opción de género inválida.");
        }
        return generos.get(opcionGenero);
    }

    private static String solicitarCalidadProyeccion() throws Exception {
        UtilidadesCine.mostrarMensaje("Seleccione la calidad de proyección:");
        System.out.println("1. IMAX");
        System.out.println("2. 4DX");
        int opcion = UtilidadesCine.solicitarNumeroEntero("Ingrese la opción:");
        switch (opcion) {
            case 1 -> {
                return "IMAX";
            }
            case 2 -> {
                return "4DX";
            }
            default -> throw new Exception("Opción de calidad inválida.");
        }
    }

    private static void eliminarPelicula() throws Exception {
        String titulo = UtilidadesCine.solicitarTexto("Ingrese el título de la película a eliminar:");
        boolean removida = cartelera.removeIf(p -> p.getTitulo().equalsIgnoreCase(titulo));
        if (removida) {
            UtilidadesCine.mostrarMensaje("Película eliminada correctamente.");
        } else {
            UtilidadesCine.mostrarMensaje("No se encontró ninguna película con ese nombre.");
        }
    }

    private static void buscarPelicula() throws Exception {
        String titulo = UtilidadesCine.solicitarTexto("Ingrese el título de la película a buscar:");
        Pelicula encontrada = buscarPorNombre(titulo);
        if (encontrada == null) {
            UtilidadesCine.mostrarMensaje("No se encontró ninguna película con ese nombre.");
        } else {
            System.out.println(encontrada);
        }
    }

    private static void listarPeliculas() {
        if (cartelera.isEmpty()) {
            UtilidadesCine.mostrarMensaje("No hay películas en la cartelera.");
            return;
        }
        System.out.println("--- CARTELERA ---");
        int totalMinutos = 0;
        Pelicula masLarga = null;
        int cantidadEstrenos = 0;
        for (Pelicula p : cartelera) {
            System.out.println(p);
            totalMinutos += p.getDuracionEnMinutos();
            if (masLarga == null || p.getDuracionEnMinutos() > masLarga.getDuracionEnMinutos()) {
                masLarga = p;
            }
            if (p instanceof PeliculaEstreno) {
                cantidadEstrenos++;
            }
        }
        System.out.println("--- RESUMEN ---");
        UtilidadesCine.mostrarMensaje("Minutos totales: " + totalMinutos + " minutos.");
        UtilidadesCine.mostrarMensaje("Película más larga: " + masLarga.getTitulo() + ".");
        UtilidadesCine.mostrarMensaje("Cantidad de estrenos: " + cantidadEstrenos + ".");
    }

    private static void modificarPelicula() throws Exception {
        String titulo = UtilidadesCine.solicitarTexto("Ingrese el título de la película a modificar:");
        Pelicula pelicula = buscarPorNombre(titulo);
        if (pelicula == null) {
            UtilidadesCine.mostrarMensaje("No se encontró ninguna película con ese nombre.");
            return;
        }

        String nuevoTitulo = UtilidadesCine.solicitarTexto("Ingrese el nuevo título:");
        if (!nuevoTitulo.equalsIgnoreCase(pelicula.getTitulo()) && existeTitulo(nuevoTitulo)) {
            UtilidadesCine.mostrarMensaje("Error: Ya existe una película con ese título.");
            return;
        }
        pelicula.setTitulo(nuevoTitulo);
        pelicula.setDuracionEnMinutos(UtilidadesCine.solicitarNumeroEntero("Ingrese la nueva duración en minutos:"));
        pelicula.setAptoTodoPublico(UtilidadesCine.solicitarBooleano("¿Es apta para todo público?"));
        UtilidadesCine.mostrarMensaje("Película modificada correctamente.");
    }

    private static boolean existeTitulo(String titulo) {
        for (Pelicula p : cartelera) {
            if (p.getTitulo().equalsIgnoreCase(titulo)) {
                return true;
            }
        }
        return false;
    }

    private static Pelicula buscarPorNombre(String titulo) {
        for (Pelicula p : cartelera) {
            if (p.getTitulo().equalsIgnoreCase(titulo)) {
                return p;
            }
        }
        return null;
    }
}