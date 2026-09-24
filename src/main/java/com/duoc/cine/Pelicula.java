package com.duoc.cine;

import java.util.Map;

public abstract class Pelicula {

    private String titulo;
    private int duracionEnMinutos;
    private String genero;
    private int anioEstreno;
    private boolean aptoTodoPublico;
    private double puntaje;

    public Pelicula(String titulo, int duracionEnMinutos, String genero, int anioEstreno, boolean aptoTodoPublico, double puntaje) {
        this.titulo = titulo;
        setDuracionEnMinutos(duracionEnMinutos);
        setGenero(genero);
        setAnioEstreno(anioEstreno);
        this.aptoTodoPublico = aptoTodoPublico;
        setPuntaje(puntaje);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public void setDuracionEnMinutos(int duracionEnMinutos) {
        if (duracionEnMinutos <= 0) {
            throw new IllegalArgumentException("La duración debe ser mayor a 0.");
        }
        this.duracionEnMinutos = duracionEnMinutos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        Map<Integer, String> diccionario = UtilidadesCine.obtenerDiccionarioGeneros();
        if (!diccionario.containsValue(genero)) {
            throw new IllegalArgumentException("Género inválido. Debe usar el diccionario de géneros.");
        }
        this.genero = genero;
    }

    public int getAnioEstreno() {
        return anioEstreno;
    }

    public void setAnioEstreno(int anioEstreno) {
        if (anioEstreno <= 0) {
            throw new IllegalArgumentException("El año de estreno debe ser mayor a 0.");
        }
        this.anioEstreno = anioEstreno;
    }

    public boolean isAptoTodoPublico() {
        return aptoTodoPublico;
    }

    public void setAptoTodoPublico(boolean aptoTodoPublico) {
        this.aptoTodoPublico = aptoTodoPublico;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        if (puntaje < 0 || puntaje > 10) {
            throw new IllegalArgumentException("El puntaje debe estar entre 0 y 10.");
        }
        this.puntaje = puntaje;
    }

    public abstract double calcularCostoEntradaBase();

    @Override
    public String toString() {
        return titulo + " | " + genero + " | " + duracionEnMinutos + " min | " + anioEstreno + " | "
                + (aptoTodoPublico ? "ATP" : "+18") + " | Puntaje: " + puntaje
                + " | Costo: $" + Math.round(calcularCostoEntradaBase());
    }
}