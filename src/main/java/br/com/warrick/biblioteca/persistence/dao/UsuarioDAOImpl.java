package br.com.warrick.biblioteca.persistence.dao;

import br.com.warrick.biblioteca.persistence.config.ConnectionFactory;
import br.com.warrick.biblioteca.persistence.exception.PersistenceException;
import br.com.warrick.biblioteca.persistence.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação de UsuarioDAO que utiliza JDBC para persistência.
 * 
 * @author Warrick
 * @since 11/11/2025
 */
public class UsuarioDAOImpl implements UsuarioDAO {
    
    private final ConnectionFactory connectionFactory;
    
    public UsuarioDAOImpl() {
        this.connectionFactory = ConnectionFactory.getInstance();
        criarTabelaSeNaoExistir();
    }
    
    private void criarTabelaSeNaoExistir() {
        String sql = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                username TEXT UNIQUE NOT NULL,
                senha TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                tema TEXT,
                admin BOOLEAN DEFAULT 0,
                ativo BOOLEAN DEFAULT 1,
                data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
            
        try (Connection conn = connectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new PersistenceException("Erro ao criar tabela de usuários", e);
        }
    }
    
    @Override
    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarUsuarioAPartirResultSet(rs);
                }
            }
            
            return null;
        }
    }
    
    @Override
    public Usuario buscarPorUsername(String username) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";
        
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarUsuarioAPartirResultSet(rs);
                }
            }
            
            return null;
        }
    }
    
    @Override
    public List<Usuario> listarTodos() throws SQLException {
        String sql = "SELECT * FROM usuarios ORDER BY nome";
        List<Usuario> usuarios = new ArrayList<>();
        
        try (Connection conn = connectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                usuarios.add(criarUsuarioAPartirResultSet(rs));
            }
        }
        
        return usuarios;
    }
    
    @Override
    public void inserir(Usuario usuario) throws SQLException {
        String sql = """
            INSERT INTO usuarios (nome, username, senha, email, tema, admin, ativo)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
            
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getUsername());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getEmail());
            stmt.setString(5, usuario.getTema());
            stmt.setBoolean(6, usuario.isAdmin());
            stmt.setBoolean(7, usuario.isAtivo());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Falha ao inserir usuário, nenhuma linha afetada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuario.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Falha ao obter o ID do usuário inserido.");
                }
            }
        }
    }
    
    @Override
    public void atualizar(Usuario usuario) throws SQLException {
        String sql = """
            UPDATE usuarios 
            SET nome = ?, username = ?, email = ?, tema = ?, admin = ?, ativo = ?
            WHERE id = ?
            """;
            
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getUsername());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getTema());
            stmt.setBoolean(5, usuario.isAdmin());
            stmt.setBoolean(6, usuario.isAtivo());
            stmt.setInt(7, usuario.getId());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Falha ao atualizar usuário, nenhuma linha afetada.");
            }
        }
    }
    
    @Override
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Falha ao deletar usuário, nenhuma linha afetada.");
            }
        }
    }
    
    @Override
    public boolean existeUsername(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE usuario = ?";
        
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    
    @Override
    public boolean existeEmail(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    
    private Usuario criarUsuarioAPartirResultSet(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setUsername(rs.getString("usuario"));
        usuario.setSenha(rs.getString("senha"));
        usuario.setEmail(rs.getString("email"));
        
        // Verificando se as colunas existem antes de acessá-las
        try {
            usuario.setEstiloPreferido(rs.getString("estilo_preferido"));
        } catch (SQLException e) {
            // Se a coluna não existir, define como null
            usuario.setEstiloPreferido(null);
        }
        
        // Verifica se a coluna admin existe
        try {
            usuario.setAdmin(rs.getBoolean("admin"));
        } catch (SQLException e) {
            // Se a coluna não existir, define como false
            usuario.setAdmin(false);
        }
        
        // Verifica se a coluna ativo existe
        try {
            usuario.setAtivo(rs.getBoolean("ativo"));
        } catch (SQLException e) {
            // Se a coluna não existir, define como true por padrão
            usuario.setAtivo(true);
        }
        
        return usuario;
    }
}
