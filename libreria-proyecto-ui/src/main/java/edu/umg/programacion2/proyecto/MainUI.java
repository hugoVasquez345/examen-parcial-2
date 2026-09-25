package edu.umg.programacion2.proyecto;

import edu.umg.programacion2.proyecto.ui.VentanaPrincipal;
import javax.swing.SwingUtilities;

/**
 * Punto de entrada de la aplicación.
 * Patrón estándar de arranque Swing: todo lo relacionado a UI debe
 * ejecutarse en el Event Dispatch Thread (EDT), por eso se usa
 * SwingUtilities.invokeLater.
 */
public class MainUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
