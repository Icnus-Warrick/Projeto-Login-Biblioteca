package br.com.warrick.biblioteca.dao;

import br.com.warrick.biblioteca.model.Usuario;
import br.com.warrick.biblioteca.utils.DatabaseManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de Acesso a Dados (DAO) para a entidade Usuário
 * Gerencia as operações de persistência no banco de dados
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 28/10/2025
 */
public class UsuarioDAO {
    
    /* ============================================== ATRIBUTOS ============================================== */
    // Constantes removidas por não estarem sendo utilizadas
    // private static final String TABLE_NAME = "usuarios";
    // private static final String COL_ID = "id";
    // private static final String COL_NOME = "nome";
    // private static final String COL_USUARIO = "usuario";
    // private static final String COL_SENHA = "senha";
    // private static final String COL_EMAIL = "email";
    // private static final String COL_ESTILO = "estilo_preferido";

    /* ========================================= MÉTODOS PÚBLICOS ========================================= */
    
    /**
     * Salva um novo usuário no banco de dados
     * 
     * @param usuario Objeto Usuario a ser salvo
     * @throws SQLException Em caso de erro no banco de dados
     */
    public void salvar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, usuario, senha, email, estilo_preferido) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getUsername());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getEstiloPreferido());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }
        }
    }

    /**
     * Busca um usuário pelo seu ID
     * 
     * @param id ID do usuário a ser buscado
     * @return Usuario encontrado ou null se não existir
     * @throws SQLException Em caso de erro no banco de dados
     */
    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;
    }

    // Método para buscar um usuário pelo endereço de email
    public Usuario buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;
    }

    /**
     * Busca um usuário pelo nome de usuário
     * 
     * @param username Nome de usuário a ser buscado
     * @return Usuario encontrado ou null se não existir
     * @throws SQLException Em caso de erro no banco de dados
     */
    public Usuario buscarPorUsername(String username) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;
    }

    /**
     * Lista todos os usuários cadastrados no sistema
     * 
     * @return Lista de usuários
     * @throws SQLException Em caso de erro no banco de dados
     */
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        }
        return usuarios;
    }

    /**
     * Atualiza os dados de um usuário existente
     * 
     * @param usuario Usuário com os dados atualizados
     * @throws SQLException Em caso de erro no banco de dados
     */
    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, usuario = ?, senha = ?, email = ?, estilo_preferido = ? WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getUsername());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getEstiloPreferido());
            stmt.setInt(6, usuario.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Exclui um usuário do banco de dados
     * 
     * @param id ID do usuário a ser excluído
     * @throws SQLException Em caso de erro no banco de dados
     */
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    /* ========================================= MÉTODOS PRIVADOS ========================================= */
    
    /**
     * Converte um ResultSet em um objeto Usuario
     * 
     * @param rs ResultSet contendo os dados do usuário
     * @return Objeto Usuario preenchido
     * @throws SQLException Em caso de erro ao acessar o ResultSet
     */
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setUsername(rs.getString("usuario"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setEmail(rs.getString("email"));
        usuario.setEstiloPreferido(rs.getString("estilo_preferido"));
        return usuario;
    }
}
