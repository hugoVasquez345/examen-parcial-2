package edu.umg.programacion2.proyecto.modelo;

import java.time.LocalDate;

/**
 *
 * @author vasqu
 */
public class Libro {

    private int id;
    private String titulo;
    private String autor;
    private String categoria;
    private double precio;
    private int existencias;
    private int anioPublicacion;
    private LocalDate fechaIngreso;

    public Libro() {
    }

    public Libro(String titulo, String autor, String categoria, double precio, int existencias, int anioPublicacion) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.precio = precio;
        this.existencias = existencias;
        this.anioPublicacion = anioPublicacion;
        this.fechaIngreso = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getExistencias() {
        return existencias;
    }

    public void setExistencias(int existencias) {
        this.existencias = existencias;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public String toString() {
        return "Libro{" + "id=" + id + ", titulo=" + titulo + ", autor=" + autor
                + ", categoria=" + categoria + ", precio=" + precio
                + ", existencias=" + existencias + ", anioPublicacion=" + anioPublicacion
                + ", fechaIngreso=" + fechaIngreso + '}';
    }
}