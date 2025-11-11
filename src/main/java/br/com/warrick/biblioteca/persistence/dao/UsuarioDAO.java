package br.com.warrick.biblioteca.persistence.dao;

import br.com.warrick.biblioteca.persistence.model.Usuario;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface de Acesso a Dados (DAO) para a entidade Usuário.
 * Define as operações de persistência no banco de dados.
 *
 * @author Warrick
 * @since 11/11/2025
 */
public interface UsuarioDAO {
    
    /**
     * Busca um usuário pelo seu ID.
     *
     * @param id ID do usuário a ser buscado
     * @return O usuário encontrado ou null se não existir
     * @throws SQLException Em caso de erro no banco de dados
     */
    Usuario buscarPorId(int id) throws SQLException;
    
    /**
     * Busca um usuário pelo nome de usuário.
     *
     * @param username Nome de usuário para busca
     * @return O usuário encontrado ou null se não existir
     * @throws SQLException Em caso de erro no banco de dados
     */
    Usuario buscarPorUsername(String username) throws SQLException;
    
    /**
     * Lista todos os usuários cadastrados.
     *
     * @return Lista de usuários
     * @throws SQLException Em caso de erro no banco de dados
     */
    List<Usuario> listarTodos() throws SQLException;
    
    /**
     * Insere um novo usuário no banco de dados.
     *
     * @param usuario Usuário a ser inserido
     * @throws SQLException Em caso de erro no banco de dados
     */
    void inserir(Usuario usuario) throws SQLException;
    
    /**
     * Atualiza um usuário existente no banco de dados.
     *
     * @param usuario Usuário com os dados atualizados
     * @throws SQLException Em caso de erro no banco de dados
     */
    void atualizar(Usuario usuario) throws SQLException;
    
    /**
     * Remove um usuário do banco de dados.
     *
     * @param id ID do usuário a ser removido
     * @throws SQLException Em caso de erro no banco de dados
     */
    void deletar(int id) throws SQLException;
    
    /**
     * Verifica se um nome de usuário já está em uso.
     *
     * @param username Nome de usuário a ser verificado
     * @return true se o nome de usuário já estiver em uso, false caso contrário
     * @throws SQLException Em caso de erro no banco de dados
     */
    boolean existeUsername(String username) throws SQLException;
    
    /**
     * Verifica se um email já está em uso.
     *
     * @param email Email a ser verificado
     * @return true se o email já estiver em uso, false caso contrário
     * @throws SQLException Em caso de erro no banco de dados
     */
    boolean existeEmail(String email) throws SQLException;
}
