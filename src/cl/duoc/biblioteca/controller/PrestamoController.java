package cl.duoc.biblioteca.controller;

import cl.duoc.biblioteca.dao.PrestamoDAO;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class PrestamoController {
    private final PrestamoDAO prestamoDAO = new PrestamoDAO();

    public void registrarPrestamoAsync(int idEstudiante, int idLibro, Runnable onSuccess, Runnable onError) {
        // Ejecución en segundo plano para no congelar la UI
        new Thread(() -> {
            boolean exito = prestamoDAO.procesarPrestamoSincronizado(idEstudiante, idLibro);

            // Retorno al hilo de la interfaz gráfica (EDT)
            SwingUtilities.invokeLater(() -> {
                if (exito) {
                    JOptionPane.showMessageDialog(null, "Préstamo registrado exitosamente.");
                    if (onSuccess != null) onSuccess.run();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al registrar el préstamo (Stock insuficiente o falla).", "Error", JOptionPane.ERROR_MESSAGE);
                    if (onError != null) onError.run();
                }
            });
        }).start();
    }
}