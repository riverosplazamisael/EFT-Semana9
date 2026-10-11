package cl.duoc.biblioteca.view;

import cl.duoc.biblioteca.controller.BibliotecaController;
import cl.duoc.biblioteca.controller.PrestamoController;
import cl.duoc.biblioteca.model.Estudiante;
import cl.duoc.biblioteca.model.Libro;

import javax.swing.*;
import java.awt.*;

public class PrestamosPanel extends JPanel {
    private final BibliotecaController biblioController = new BibliotecaController();
    private final PrestamoController prestamoController = new PrestamoController();

    private JComboBox<Estudiante> comboEstudiantes;
    private JComboBox<Libro> comboLibros;
    private JButton btnSolicitar;

    public PrestamosPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Módulo de Préstamos"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        comboEstudiantes = new JComboBox<>();
        comboLibros = new JComboBox<>();
        btnSolicitar = new JButton("Procesar Préstamo (Hilo Asíncrono)");

        cargarCombos();

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Seleccionar Estudiante:"), gbc);
        gbc.gridx = 1;
        add(comboEstudiantes, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Seleccionar Libro:"), gbc);
        gbc.gridx = 1;
        add(comboLibros, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        btnSolicitar.addActionListener(e -> procesarPrestamo());
        add(btnSolicitar, gbc);
    }

    private void cargarCombos() {
        comboEstudiantes.removeAllItems();
        for (Estudiante est : biblioController.obtenerEstudiantes()) {
            comboEstudiantes.addItem(est);
        }

        comboLibros.removeAllItems();
        for (Libro lib : biblioController.obtenerLibros()) {
            comboLibros.addItem(lib);
        }
    }

    private void procesarPrestamo() {
        Estudiante estSel = (Estudiante) comboEstudiantes.getSelectedItem();
        Libro libSel = (Libro) comboLibros.getSelectedItem();

        if (estSel == null || libSel == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un estudiante y un libro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        btnSolicitar.setEnabled(false); // Bloquear botón mientras procesa el hilo

        // Llama al controlador asíncrono con hilos y synchronized
        prestamoController.registrarPrestamoAsync(
                estSel.getId(),
                libSel.getId(),
                () -> { // Callback Éxito
                    cargarCombos(); // Actualizar stock visible
                    btnSolicitar.setEnabled(true);
                },
                () -> { // Callback Error
                    btnSolicitar.setEnabled(true);
                }
        );
    }
}