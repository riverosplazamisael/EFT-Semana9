package cl.duoc.biblioteca.view;

import cl.duoc.biblioteca.controller.BibliotecaController;
import cl.duoc.biblioteca.model.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LibrosPanel extends JPanel {
    private final BibliotecaController controller = new BibliotecaController();
    private JTable tablaLibros;
    private DefaultTableModel modelTabla;

    private JTextField txtTitulo, txtAutor, txtIsbn, txtEditorial, txtStock, txtCategoria;

    public LibrosPanel() {
        setLayout(new BorderLayout(10, 10));

        // --- Formulario Superior ---
        JPanel panelForm = new JPanel(new GridLayout(3, 4, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Registrar Nuevo Libro"));

        txtTitulo = new JTextField();
        txtAutor = new JTextField();
        txtIsbn = new JTextField();
        txtEditorial = new JTextField();
        txtStock = new JTextField();
        txtCategoria = new JTextField("1"); // ID Categoría por defecto (ej. General)

        panelForm.add(new JLabel("Título:")); panelForm.add(txtTitulo);
        panelForm.add(new JLabel("Autor:")); panelForm.add(txtAutor);
        panelForm.add(new JLabel("ISBN:")); panelForm.add(txtIsbn);
        panelForm.add(new JLabel("Editorial:")); panelForm.add(txtEditorial);
        panelForm.add(new JLabel("Stock:")); panelForm.add(txtStock);
        panelForm.add(new JLabel("ID Cat:")); panelForm.add(txtCategoria);

        JButton btnGuardar = new JButton("Guardar Libro");
        btnGuardar.addActionListener(e -> guardarLibro());

        JButton btnEliminar = new JButton("Eliminar Seleccionado");
        btnEliminar.addActionListener(e -> eliminarLibro());

        JPanel panelAcciones = new JPanel();
        panelAcciones.add(btnGuardar);
        panelAcciones.add(btnEliminar);

        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelForm, BorderLayout.CENTER);
        panelNorte.add(panelAcciones, BorderLayout.SOUTH);
        add(panelNorte, BorderLayout.NORTH);

        // --- Tabla de Libros ---
        modelTabla = new DefaultTableModel(new String[]{"ID", "Título", "Autor", "ISBN", "Editorial", "Stock", "Categoría"}, 0);
        tablaLibros = new JTable(modelTabla);
        add(new JScrollPane(tablaLibros), BorderLayout.CENTER);

        cargarDatos();
    }

    private void cargarDatos() {
        modelTabla.setRowCount(0);
        for (Libro l : controller.obtenerLibros()) {
            modelTabla.addRow(new Object[]{l.getId(), l.getTitulo(), l.getAutor(), l.getIsbn(), l.getEditorial(), l.getStock(), l.getNombreCategoria()});
        }
    }

    private void guardarLibro() {
        try {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            String isbn = txtIsbn.getText();
            String editorial = txtEditorial.getText();
            int stock = Integer.parseInt(txtStock.getText());
            int cat = Integer.parseInt(txtCategoria.getText());

            if (controller.agregarLibro(titulo, autor, isbn, editorial, stock, cat)) {
                JOptionPane.showMessageDialog(this, "Libro guardado exitosamente.");
                cargarDatos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el libro.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Stock e ID Categoría deben ser numéricos.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarLibro() {
        int fila = tablaLibros.getSelectedRow();
        if (fila >= 0) {
            int id = (int) modelTabla.getValueAt(fila, 0);
            if (controller.eliminarLibro(id)) {
                JOptionPane.showMessageDialog(this, "Libro eliminado.");
                cargarDatos();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un libro de la tabla.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtTitulo.setText(""); txtAutor.setText(""); txtIsbn.setText("");
        txtEditorial.setText(""); txtStock.setText("");
    }
}