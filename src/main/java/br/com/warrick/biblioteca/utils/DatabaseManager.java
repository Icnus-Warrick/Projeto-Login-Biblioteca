package br.com.warrick.biblioteca.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Projeto: Biblioteca
 * @author Warrick
 * @since 28/10/2025
 */
public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:src/main/resources/db/Biblioteca.db";

    static {
        try {
            // Registrar o driver SQLite JDBC
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver SQLite JDBC não encontrado: " + e.getMessage());
        }
    }

    public static void init() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Criar tabela usuarios se não existir
            String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nome TEXT NOT NULL," +
                    "username TEXT NOT NULL UNIQUE," +
                    "senha TEXT NOT NULL," +
                    "email TEXT," +
                    "estilo_preferido TEXT" +
                    ")";
            stmt.execute(sql);

            System.out.println("Banco de dados inicializado com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao inicializar banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}
