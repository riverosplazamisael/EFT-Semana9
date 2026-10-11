package cl.duoc.biblioteca.controller;

import cl.duoc.biblioteca.dao.EstudianteDAO;
import cl.duoc.biblioteca.dao.LibroDAO;
import cl.duoc.biblioteca.model.Estudiante;
import cl.duoc.biblioteca.model.Libro;

import java.util.List;

public class BibliotecaController {
    private final LibroDAO libroDAO = new LibroDAO();
    private final EstudianteDAO estudianteDAO = new EstudianteDAO();

    // --- Métodos de Libros ---
    public List<Libro> obtenerLibros() {
        return libroDAO.listarTodos();
    }

    public boolean agregarLibro(String titulo, String autor, String isbn, String editorial, int stock, int idCategoria) {
        Libro libro = new Libro(0, titulo, autor, isbn, editorial, stock, idCategoria);
        return libroDAO.guardar(libro);
    }

    public boolean eliminarLibro(int id) {
        return libroDAO.eliminar(id);
    }

    // --- Métodos de Estudiantes ---
    public List<Estudiante> obtenerEstudiantes() {
        return estudianteDAO.listarTodos();
    }

    public boolean agregarEstudiante(String nombre, String rut, String curso, String correo) {
        Estudiante est = new Estudiante(0, nombre, rut, curso, correo);
        return estudianteDAO.guardar(est);
    }
}