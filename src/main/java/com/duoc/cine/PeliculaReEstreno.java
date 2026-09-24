package com.duoc.cine;

public class PeliculaReEstreno extends Pelicula {

    private int anioOriginalEstreno;

    public PeliculaReEstreno(String titulo, int duracionEnMinutos, String genero, int anioEstreno, boolean aptoTodoPublico, double puntaje, int anioOriginalEstreno) {
        super(titulo, duracionEnMinutos, genero, anioEstreno, aptoTodoPublico, puntaje);
        setAnioOriginalEstreno(anioOriginalEstreno);
    }

    public int getAnioOriginalEstreno() {
        return anioOriginalEstreno;
    }

    public void setAnioOriginalEstreno(int anioOriginalEstreno) {
        if (anioOriginalEstreno <= 0) {
            throw new IllegalArgumentException("El año original de estreno debe ser mayor a 0.");
        }
        this.anioOriginalEstreno = anioOriginalEstreno;
    }

    @Override
    public double calcularCostoEntradaBase() {
        return 3500;
    }

    @Override
    public String toString() {
        return super.toString() + " | Re-estreno | Estreno original: " + anioOriginalEstreno;
    }
}