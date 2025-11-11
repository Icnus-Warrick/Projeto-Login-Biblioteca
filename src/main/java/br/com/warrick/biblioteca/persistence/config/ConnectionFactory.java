package br.com.warrick.biblioteca.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Factory responsável por criar e gerenciar conexões com o banco de dados.
 * Implementa o padrão Singleton para garantir uma única instância.
 *
 * @author Warrick
 * @since 11/11/2025
 */
public class ConnectionFactory {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/db/Biblioteca.db";
    private static ConnectionFactory instance;

    private ConnectionFactory() {
        // Construtor privado para evitar instanciação direta
        try {
            // Garante que o driver SQLite está carregado
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Falha ao carregar o driver do SQLite", e);
        }
    }

    /**
     * Obtém a instância única da ConnectionFactory.
     * @return A instância de ConnectionFactory
     */
    public static synchronized ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    /**
     * Obtém uma nova conexão com o banco de dados.
     * @return Uma conexão com o banco de dados
     * @throws SQLException Se ocorrer um erro ao obter a conexão
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    /**
     * Fecha uma conexão com o banco de dados.
     * @param connection A conexão a ser fechada
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }
}
