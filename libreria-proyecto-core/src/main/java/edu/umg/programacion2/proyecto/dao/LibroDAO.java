package edu.umg.programacion2.proyecto.dao;

import edu.umg.programacion2.proyecto.modelo.Libro;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vasqu
 */
public class LibroDAO {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/catalogo_libros";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "admin123";

    private Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }

    public void insertar(Libro libro) throws SQLException {
        String sql = "INSERT INTO libro (titulo, autor, categoria, precio, existencias, anio_publicacion, fecha_ingreso) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getCategoria());
            stmt.setDouble(4, libro.getPrecio());
            stmt.setInt(5, libro.getExistencias());
            stmt.setInt(6, libro.getAnioPublicacion());
            stmt.setDate(7, Date.valueOf(libro.getFechaIngreso()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    libro.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<Libro> listarTodos() throws SQLException {
        String sql = "SELECT id, titulo, autor, categoria, precio, existencias, anio_publicacion, fecha_ingreso FROM libro";
        List<Libro> libros = new ArrayList<>();

        try (Connection con = obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setCategoria(rs.getString("categoria"));
                libro.setPrecio(rs.getDouble("precio"));
                libro.setExistencias(rs.getInt("existencias"));
                libro.setAnioPublicacion(rs.getInt("anio_publicacion"));

                Date fecha = rs.getDate("fecha_ingreso");
                if (fecha != null) {
                    libro.setFechaIngreso(fecha.toLocalDate());
                }

                libros.add(libro);
            }
        }

        return libros;
    }

    public void actualizar(Libro libro) throws SQLException {
        String sql = "UPDATE libro SET titulo = ?, autor = ?, categoria = ?, precio = ?, "
                + "existencias = ?, anio_publicacion = ?, fecha_ingreso = ? WHERE id = ?";

        try (Connection con = obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, libro.getTitulo());
            stmt.setString(2, libro.getAutor());
            stmt.setString(3, libro.getCategoria());
            stmt.setDouble(4, libro.getPrecio());
            stmt.setInt(5, libro.getExistencias());
            stmt.setInt(6, libro.getAnioPublicacion());
            stmt.setDate(7, Date.valueOf(libro.getFechaIngreso()));
            stmt.setInt(8, libro.getId());

            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM libro WHERE id = ?";

        try (Connection con = obtenerConexion();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}