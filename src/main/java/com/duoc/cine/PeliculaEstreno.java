package com.duoc.cine;

public class PeliculaEstreno extends Pelicula implements FuncionEspecial {

    private String calidadProyeccion;

    public PeliculaEstreno(String titulo, int duracionEnMinutos, String genero, int anioEstreno, boolean aptoTodoPublico, double puntaje, String calidadProyeccion) {
        super(titulo, duracionEnMinutos, genero, anioEstreno, aptoTodoPublico, puntaje);
        setCalidadProyeccion(calidadProyeccion);
    }

    public String getCalidadProyeccion() {
        return calidadProyeccion;
    }

    public void setCalidadProyeccion(String calidadProyeccion) {
        if (!calidadProyeccion.equalsIgnoreCase("IMAX") && !calidadProyeccion.equalsIgnoreCase("4DX")) {
            throw new IllegalArgumentException("La calidad de proyección debe ser IMAX o 4DX.");
        }
        this.calidadProyeccion = calidadProyeccion;
    }

    @Override
    public void reproducirTrailerExclusivo() {
        UtilidadesCine.mostrarMensaje("Reproduciendo tráiler exclusivo de " + getTitulo() + ".");
    }

    @Override
    public double calcularCostoEntradaBase() {
        double costo = 6000;
        if (calidadProyeccion.equalsIgnoreCase("IMAX")) {
            costo += 1000;
        } else if (calidadProyeccion.equalsIgnoreCase("4DX")) {
            costo += 1500;
        }
        return costo;
    }

    @Override
    public String toString() {
        return super.toString() + " | Estreno | " + calidadProyeccion;
    }
}