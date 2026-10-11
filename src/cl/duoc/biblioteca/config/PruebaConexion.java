package cl.duoc.biblioteca.config;

import cl.duoc.biblioteca.config.DatabaseConnection;
import java.sql.Connection;

public class PruebaConexion {
    public static void main(String[] args) {
        System.out.println("Iniciando prueba de conexión...");

        try {
            Connection conn = DatabaseConnection.getInstance().getConnection();

            if (conn != null) {
                System.out.println("¡Felicidades! Java se conectó exitosamente a tu MySQL local");
            }
        } catch (RuntimeException e) {
            System.out.println("Falló la inicialización del Singleton:");
            System.out.println(e.getMessage());
        }
    }
}
