package br.com.warrick.biblioteca.persistence.dao;

import br.com.warrick.biblioteca.persistence.model.Livro;
import br.com.warrick.biblioteca.persistence.config.ConnectionFactory;
import br.com.warrick.biblioteca.persistence.exception.PersistenceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação de LivroDAO que utiliza JDBC para persistência em banco de dados SQLite.
 *
 * @author Warrick
 * @since 11/11/2025
 */
public class LivroDAOImpl implements LivroDAO {
    
    private final ConnectionFactory connectionFactory;
    
    public LivroDAOImpl() {
        this.connectionFactory = ConnectionFactory.getInstance();
        criarTabelaSeNaoExistir();
    }
    
    private void criarTabelaSeNaoExistir() {
        String sql = """
            CREATE TABLE IF NOT EXISTS livros (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo TEXT NOT NULL,
                autor TEXT NOT NULL,
                editora TEXT,
                isbn TEXT UNIQUE,
                colecao TEXT,
                genero TEXT,
                idioma TEXT,
                formato TEXT,
                ano_publicacao INTEGER,
                numero_paginas INTEGER,
                numero_colecao INTEGER,
                disponivel BOOLEAN DEFAULT 1
            )
            """;
            
        try (Connection conn = connectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new PersistenceException("Erro ao criar tabela de livros", e);
        }
    }
    
    @Override
    public void salvar(Livro livro) {
        String sql = """
            INSERT INTO livros (titulo, autor, editora, isbn, colecao, genero, 
                              idioma, formato, ano_publicacao, numero_paginas, 
                              numero_colecao, disponivel)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
            
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            preencherParametrosLivro(stmt, livro);
            stmt.setBoolean(12, livro.isDisponivel());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new PersistenceException("Falha ao salvar o livro, nenhuma linha afetada.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    livro.setId(generatedKeys.getInt(1));
                } else {
                    throw new PersistenceException("Falha ao obter o ID do livro salvo.");
                }
            }
            
        } catch (SQLException e) {
            throw new PersistenceException("Erro ao salvar livro", e);
        }
    }
    
    @Override
    public void atualizar(Livro livro) {
        String sql = """
            UPDATE livros 
            SET titulo = ?, autor = ?, editora = ?, isbn = ?, colecao = ?, 
                genero = ?, idioma = ?, formato = ?, ano_publicacao = ?, 
                numero_paginas = ?, numero_colecao = ?, disponivel = ?
            WHERE id = ?
            """;
            
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            preencherParametrosLivro(stmt, livro);
            stmt.setBoolean(12, livro.isDisponivel());
            stmt.setInt(13, livro.getId());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new PersistenceException("Nenhum livro encontrado com o ID: " + livro.getId());
            }
            
        } catch (SQLException e) {
            throw new PersistenceException("Erro ao atualizar livro", e);
        }
    }
    
    @Override
    public void deletar(Integer id) {
        String sql = "DELETE FROM livros WHERE id = ?";
        
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new PersistenceException("Nenhum livro encontrado com o ID: " + id);
            }
            
        } catch (SQLException e) {
            throw new PersistenceException("Erro ao deletar livro", e);
        }
    }
    
    @Override
    public Livro buscarPorId(Integer id) {
        String sql = "SELECT * FROM livros WHERE id = ?";
        
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return criarLivroAPartirResultSet(rs);
                }
            }
            
            return null;
            
        } catch (SQLException e) {
            throw new PersistenceException("Erro ao buscar livro por ID", e);
        }
    }
    
    @Override
    public List<Livro> listarTodos() {
        String sql = "SELECT * FROM livros ORDER BY titulo";
        return buscarLivrosComQuery(sql);
    }
    
    @Override
    public List<Livro> buscarPorTitulo(String titulo) {
        String sql = "SELECT * FROM livros WHERE titulo LIKE ? ORDER BY titulo";
        
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + titulo + "%");
            return extrairLivrosDoResultSet(stmt);
            
        } catch (SQLException e) {
            throw new PersistenceException("Erro ao buscar livros por título", e);
        }
    }
    
    @Override
    public List<Livro> buscarPorAutor(String autor) {
        String sql = "SELECT * FROM livros WHERE autor LIKE ? ORDER BY titulo";
        
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + autor + "%");
            return extrairLivrosDoResultSet(stmt);
            
        } catch (SQLException e) {
            throw new PersistenceException("Erro ao buscar livros por autor", e);
        }
    }
    
    @Override
    public List<Livro> buscarPorColecao(String colecao) {
        String sql = "SELECT * FROM livros WHERE colecao LIKE ? ORDER BY numero_colecao, titulo";
        
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + colecao + "%");
            return extrairLivrosDoResultSet(stmt);
            
        } catch (SQLException e) {
            throw new PersistenceException("Erro ao buscar livros por coleção", e);
        }
    }
    
    @Override
    public List<Livro> buscarDisponiveis() {
        String sql = "SELECT * FROM livros WHERE disponivel = 1 ORDER BY titulo";
        return buscarLivrosComQuery(sql);
    }
    
    @Override
    public List<Livro> buscarEmprestados() {
        String sql = "SELECT * FROM livros WHERE disponivel = 0 ORDER BY titulo";
        return buscarLivrosComQuery(sql);
    }
    
    // Métodos auxiliares
    
    private List<Livro> buscarLivrosComQuery(String sql) {
        try (Connection conn = connectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            List<Livro> livros = new ArrayList<>();
            while (rs.next()) {
                livros.add(criarLivroAPartirResultSet(rs));
            }
            return livros;
            
        } catch (SQLException e) {
            throw new PersistenceException("Erro ao buscar livros", e);
        }
    }
    
    private List<Livro> extrairLivrosDoResultSet(PreparedStatement stmt) throws SQLException {
        List<Livro> livros = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                livros.add(criarLivroAPartirResultSet(rs));
            }
        }
        return livros;
    }
    
    private Livro criarLivroAPartirResultSet(ResultSet rs) throws SQLException {
        Livro livro = new Livro();
        livro.setId(rs.getInt("id"));
        livro.setTitulo(rs.getString("titulo"));
        livro.setAutor(rs.getString("autor"));
        livro.setEditora(rs.getString("editora"));
        livro.setIsbn(rs.getString("isbn"));
        livro.setColecao(rs.getString("colecao"));
        livro.setGenero(rs.getString("genero"));
        livro.setIdioma(rs.getString("idioma"));
        livro.setFormato(rs.getString("formato"));
        livro.setAnoPublicacao(rs.getInt("ano_publicacao"));
        livro.setNumeroPaginas(rs.getInt("numero_paginas"));
        livro.setNumeroColecao(rs.getInt("numero_colecao"));
        livro.setDisponivel(rs.getBoolean("disponivel"));
        return livro;
    }
    
    private void preencherParametrosLivro(PreparedStatement stmt, Livro livro) throws SQLException {
        stmt.setString(1, livro.getTitulo());
        stmt.setString(2, livro.getAutor());
        stmt.setString(3, livro.getEditora());
        stmt.setString(4, livro.getIsbn());
        stmt.setString(5, livro.getColecao());
        stmt.setString(6, livro.getGenero());
        stmt.setString(7, livro.getIdioma());
        stmt.setString(8, livro.getFormato());
        
        if (livro.getAnoPublicacao() != null) {
            stmt.setInt(9, livro.getAnoPublicacao());
        } else {
            stmt.setNull(9, Types.INTEGER);
        }
        
        if (livro.getNumeroPaginas() != null) {
            stmt.setInt(10, livro.getNumeroPaginas());
        } else {
            stmt.setNull(10, Types.INTEGER);
        }
        
        if (livro.getNumeroColecao() != null) {
            stmt.setInt(11, livro.getNumeroColecao());
        } else {
            stmt.setNull(11, Types.INTEGER);
        }
    }
}
