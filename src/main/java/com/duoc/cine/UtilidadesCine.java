package com.duoc.cine;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UtilidadesCine {
    private static final Scanner scanner = new Scanner(System.in);

    public static void mostrarMensaje(String mensaje) {
        System.out.println("[SuperCines]: " + mensaje);
    }

    public static Map<Integer, String> obtenerDiccionarioGeneros() {
        Map<Integer, String> generos = new HashMap<>();
        generos.put(1, "Ciencia Ficción");
        generos.put(2, "Drama");
        generos.put(3, "Terror");
        generos.put(4, "Comedia");
        generos.put(5, "Acción");
        return generos;
    }

    public static String solicitarTexto(String etiqueta) throws Exception {
        mostrarMensaje(etiqueta);
        String texto = scanner.nextLine();
        if (texto == null || texto.trim().isEmpty()) {
            throw new Exception("El texto ingresado no puede estar vacío.");
        }
        return texto.trim();
    }

    public static int solicitarNumeroEntero(String etiqueta) throws Exception {
        mostrarMensaje(etiqueta);
        try {
            int numero = Integer.parseInt(scanner.nextLine());
            return numero;
        } catch (NumberFormatException e) {
            throw new Exception("Error: Debe ingresar un número entero válido.");
        }
    }

    public static double solicitarNumeroDecimal(String etiqueta) throws Exception {
        mostrarMensaje(etiqueta);
        try {
            double decimal = Double.parseDouble(scanner.nextLine());
            return decimal;
        } catch (NumberFormatException e) {
            throw new Exception("Error: Debe ingresar un número decimal válido.");
        }
    }

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