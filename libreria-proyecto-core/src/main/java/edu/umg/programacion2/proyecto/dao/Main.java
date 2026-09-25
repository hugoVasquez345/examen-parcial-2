package edu.umg.programacion2.proyecto.dao;

import edu.umg.programacion2.proyecto.modelo.Libro;

/**
 *
 * @author vasqu
 */
public class Main {

    public static void main(String[] args) throws Exception {
        LibroDAO dao = new LibroDAO();

        Libro libro = new Libro("Cien años de soledad", "Gabriel García Márquez", "Novela", 145.00, 12, 1967);
        dao.insertar(libro);
        System.out.println("Libro insertado con id: " + libro.getId());

        System.out.println("Listado completo:");
        for (Libro l : dao.listarTodos()) {
            System.out.println(l);
        }
    }
}