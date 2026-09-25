package edu.umg.programacion2.proyecto.ui;

import edu.umg.programacion2.proyecto.dao.LibroDAO;
import edu.umg.programacion2.proyecto.modelo.Libro;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana principal de la aplicación (Variante B - Catálogo de libros).
 *
 * @author vasqu
 */
public class VentanaPrincipal extends JFrame {

    private final LibroDAO dao = new LibroDAO();

    // Modelo y tabla
    private DefaultTableModel modeloTabla;
    private JTable tablaLibros;

    // Campos del formulario
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtCategoria;
    private JTextField txtPrecio;
    private JTextField txtExistencias;
    private JTextField txtAnio;
    private JLabel lblFechaIngreso; // solo informativo, no editable

    // Guarda el id y la fecha de ingreso del libro seleccionado en la tabla
    private int idSeleccionado = 0;
    private LocalDate fechaIngresoSeleccionado = null;

    // Botones
    private JButton btnRegistrar;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JButton btnVerResumen;

    public VentanaPrincipal() {
        setTitle("Catálogo de Libros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inicializarTabla();
        inicializarFormulario();
        cargarLibros();

        setVisible(true);
    }

    private void inicializarTabla() {
        String[] columnas = {"ID", "Título", "Autor", "Categoría", "Precio", "Existencias", "Año", "Fecha Ingreso"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaLibros = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaLibros);

        tablaLibros.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaLibros.getSelectedRow() != -1) {
                int fila = tablaLibros.getSelectedRow();
                idSeleccionado = (int) modeloTabla.getValueAt(fila, 0);
                txtTitulo.setText(String.valueOf(modeloTabla.getValueAt(fila, 1)));
                txtAutor.setText(String.valueOf(modeloTabla.getValueAt(fila, 2)));
                txtCategoria.setText(String.valueOf(modeloTabla.getValueAt(fila, 3)));
                txtPrecio.setText(String.valueOf(modeloTabla.getValueAt(fila, 4)));
                txtExistencias.setText(String.valueOf(modeloTabla.getValueAt(fila, 5)));
                txtAnio.setText(String.valueOf(modeloTabla.getValueAt(fila, 6)));

                fechaIngresoSeleccionado = (LocalDate) modeloTabla.getValueAt(fila, 7);
                lblFechaIngreso.setText(fechaIngresoSeleccionado.toString());
            }
        });

        add(scroll, BorderLayout.CENTER);
    }

    private void inicializarFormulario() {
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del libro"));

        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtCategoria = new JTextField();
        txtPrecio = new JTextField();
        txtExistencias = new JTextField();
        txtAnio = new JTextField();
        lblFechaIngreso = new JLabel("Se asigna automáticamente al registrar");
        lblFechaIngreso.setForeground(Color.GRAY);

        panelFormulario.add(new JLabel("Título:"));
        panelFormulario.add(txtTitulo);
        panelFormulario.add(new JLabel("Autor:"));
        panelFormulario.add(txtAutor);
        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(txtCategoria);
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(txtPrecio);
        panelFormulario.add(new JLabel("Existencias:"));
        panelFormulario.add(txtExistencias);
        panelFormulario.add(new JLabel("Año publicación:"));
        panelFormulario.add(txtAnio);
        panelFormulario.add(new JLabel("Fecha de ingreso:"));
        panelFormulario.add(lblFechaIngreso);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnRegistrar = new JButton("Registrar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        btnVerResumen = new JButton("Ver resumen");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnVerResumen);

        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(panelFormulario, BorderLayout.CENTER);
        panelInferior.add(panelBotones, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> registrarLibro());
        btnActualizar.addActionListener(e -> actualizarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnVerResumen.addActionListener(e -> mostrarResumen());
    }

    private void cargarLibros() {
        modeloTabla.setRowCount(0);

        try {
            List<Libro> libros = dao.listarTodos();
            for (Libro l : libros) {
                modeloTabla.addRow(new Object[]{
                    l.getId(), l.getTitulo(), l.getAutor(), l.getCategoria(),
                    l.getPrecio(), l.getExistencias(), l.getAnioPublicacion(),
                    l.getFechaIngreso()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los libros: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String validarFormulario() {
        if (txtTitulo.getText().trim().isEmpty()) {
            return "El título no puede quedar vacío.";
        }
        if (txtAutor.getText().trim().isEmpty()) {
            return "El autor no puede quedar vacío.";
        }
        if (txtCategoria.getText().trim().isEmpty()) {
            return "La categoría no puede quedar vacía.";
        }

        double precio;
        try {
            precio = Double.parseDouble(txtPrecio.getText().trim());
        } catch (NumberFormatException ex) {
            return "El precio debe ser un número válido.";
        }
        if (precio <= 0) {
            return "El precio debe ser mayor a cero.";
        }

        int existencias;
        try {
            existencias = Integer.parseInt(txtExistencias.getText().trim());
        } catch (NumberFormatException ex) {
            return "Las existencias deben ser un número entero válido.";
        }
        if (existencias < 0) {
            return "Las existencias no pueden ser negativas.";
        }

        int anio;
        try {
            anio = Integer.parseInt(txtAnio.getText().trim());
        } catch (NumberFormatException ex) {
            return "El año de publicación debe ser un número entero válido.";
        }
        int anioActual = LocalDate.now().getYear();
        if (anio > anioActual) {
            return "El año de publicación no puede ser mayor al año actual (" + anioActual + ").";
        }

        return null;
    }

    private Libro construirLibroDesdeFormulario() {
        Libro libro = new Libro();
        libro.setTitulo(txtTitulo.getText().trim());
        libro.setAutor(txtAutor.getText().trim());
        libro.setCategoria(txtCategoria.getText().trim());
        libro.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
        libro.setExistencias(Integer.parseInt(txtExistencias.getText().trim()));
        libro.setAnioPublicacion(Integer.parseInt(txtAnio.getText().trim()));
        return libro;
    }

    private void registrarLibro() {
        String error = validarFormulario();
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Datos inválidos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Libro libro = construirLibroDesdeFormulario();
            libro.setFechaIngreso(LocalDate.now()); // fecha de ingreso = hoy, al registrar
            dao.insertar(libro);
            JOptionPane.showMessageDialog(this, "Libro registrado con id: " + libro.getId());
            limpiarFormulario();
            cargarLibros();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al registrar el libro: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarLibro() {
        if (idSeleccionado == 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un libro de la tabla para actualizar.",
                    "Ningún libro seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String error = validarFormulario();
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Datos inválidos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Libro libro = construirLibroDesdeFormulario();
            libro.setId(idSeleccionado);
            // La fecha de ingreso NO cambia al actualizar: se conserva la original.
            libro.setFechaIngreso(fechaIngresoSeleccionado);
            dao.actualizar(libro);
            JOptionPane.showMessageDialog(this, "Libro actualizado correctamente.");
            limpiarFormulario();
            cargarLibros();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar el libro: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarLibro() {
        if (idSeleccionado == 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un libro de la tabla para eliminar.",
                    "Ningún libro seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar el libro \"" + txtTitulo.getText() + "\"?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            dao.eliminar(idSeleccionado);
            JOptionPane.showMessageDialog(this, "Libro eliminado correctamente.");
            limpiarFormulario();
            cargarLibros();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al eliminar el libro: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        idSeleccionado = 0;
        fechaIngresoSeleccionado = null;
        txtTitulo.setText("");
        txtAutor.setText("");
        txtCategoria.setText("");
        txtPrecio.setText("");
        txtExistencias.setText("");
        txtAnio.setText("");
        lblFechaIngreso.setText("Se asigna automáticamente al registrar");
        tablaLibros.clearSelection();
    }

    /**
     * Mejora #7: Conteo con condición.
     * Recorre en Java la lista devuelta por listarTodos() (sin usar COUNT(*) en SQL)
     * para calcular el total de libros y cuántos están agotados (existencias = 0).
     */
    private void mostrarResumen() {
        try {
            List<Libro> libros = dao.listarTodos();

            int total = 0;
            int agotados = 0;

            for (Libro l : libros) {
                total++; // contador manual del total
                if (l.getExistencias() == 0) {
                    agotados++; // contador manual de los que cumplen la condición
                }
            }

            String mensaje = "Total de libros en el catálogo: " + total + "\n"
                    + "Libros agotados (existencias = 0): " + agotados;

            JOptionPane.showMessageDialog(this, mensaje, "Resumen del catálogo", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al calcular el resumen: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}