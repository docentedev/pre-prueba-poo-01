package com.duoc.cine;

public class PeliculaNormal extends Pelicula {

    public PeliculaNormal(String titulo, int duracionEnMinutos, String genero, int anioEstreno, boolean aptoTodoPublico, double puntaje) {
        super(titulo, duracionEnMinutos, genero, anioEstreno, aptoTodoPublico, puntaje);
    }

    @Override
    public double calcularCostoEntradaBase() {
        return 4000;
    }

    @Override
    public String toString() {
        return super.toString() + " | Normal";
    }
}