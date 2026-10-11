package cl.duoc.biblioteca.dao;

import cl.duoc.biblioteca.config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrestamoDAO {

    // Método sincronizado para garantizar consistencia de stock en acceso concurrente
    public synchronized boolean procesarPrestamoSincronizado(int idEstudiante, int idLibro) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try {
            conn.setAutoCommit(false); // Transacción atómica

            // 1. Descontar stock (si stock > 0)
            String sqlUpdateStock = "UPDATE libros SET stock = stock - 1 WHERE id = ? AND stock > 0";
            try (PreparedStatement stmtStock = conn.prepareStatement(sqlUpdateStock)) {
                stmtStock.setInt(1, idLibro);
                int filasAfectadas = stmtStock.executeUpdate();

                if (filasAfectadas == 0) {
                    conn.rollback();
                    return false; // Sin stock suficiente
                }
            }

            // 2. Registrar el préstamo
            String sqlInsertPrestamo = "INSERT INTO prestamos (id_estudiante, id_libro, fecha_prestamo, devuelto) VALUES (?, ?, CURDATE(), false)";
            try (PreparedStatement stmtPrestamo = conn.prepareStatement(sqlInsertPrestamo)) {
                stmtPrestamo.setInt(1, idEstudiante);
                stmtPrestamo.setInt(2, idLibro);
                stmtPrestamo.executeUpdate();
            }

            conn.commit(); // Confirmar cambios
            return true;
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}