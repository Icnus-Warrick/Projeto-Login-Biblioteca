package br.com.warrick.biblioteca.service;

import br.com.warrick.biblioteca.persistence.dao.UsuarioDAO;
import br.com.warrick.biblioteca.persistence.dao.UsuarioDAOImpl;
import br.com.warrick.biblioteca.persistence.model.Usuario;

import java.sql.SQLException;
import java.util.List;

/**
 * Classe de serviço para operações relacionadas a usuários
 * Encapsula a lógica de negócios e validações antes de acessar a camada de dados
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 28/10/2025
 */
public class UsuarioService {

    /* ============================================== ATRIBUTOS ============================================== */
    /** DAO para acesso aos dados do usuário */
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();

    /* ========================================= MÉTODOS PÚBLICOS ========================================= */
    
    /**
     * Cadastra um novo usuário no sistema com validações de entrada
     * 
     * @param nome Nome completo do usuário
     * @param usuario Nome de usuário único
     * @param senha Senha do usuário (será armazenada sem criptografia neste exemplo)
     * @param email Email do usuário
     * @param estiloPreferido Estilo visual preferido
     * @throws SQLException Em caso de erro no banco de dados
     * @throws IllegalArgumentException Se os dados de entrada forem inválidos
     */
    public void cadastrarUsuario(String nome, String usuario, String senha, String email, String estiloPreferido) 
            throws SQLException, IllegalArgumentException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (usuario == null || usuario.trim().isEmpty()) {
            throw new IllegalArgumentException("Usuario não pode ser vazio");
        }
        if (senha == null || senha.length() < 6) {
            throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres");
        }

        Usuario existente = usuarioDAO.buscarPorUsername(usuario);
        if (existente != null) {
            throw new IllegalArgumentException("Usuario já cadastrado");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(nome);
        novoUsuario.setUsername(usuario);
        novoUsuario.setSenha(senha);
        novoUsuario.setEmail(email);
        novoUsuario.setTema(estiloPreferido);
        novoUsuario.setAdmin(false);
        novoUsuario.setAtivo(true);
        
        usuarioDAO.inserir(novoUsuario);
    }

    /**
     * Autentica um usuário com base no nome de usuário e senha
     * 
     * @param usuario Nome de usuário
     * @param senha Senha fornecida
     * @return Usuario autenticado ou null se a autenticação falhar
     * @throws SQLException Em caso de erro no banco de dados
     */
    public Usuario autenticar(String usuario, String senha) throws SQLException {
        Usuario user = usuarioDAO.buscarPorUsername(usuario);
        if (user != null && user.getSenha().equals(senha)) {
            return user;
        }
        return null;
    }

/**
     * Busca um usuário pelo seu ID
     * 
     * @param id ID do usuário a ser buscado
     * @return Usuario encontrado ou null se não existir
     * @throws SQLException Em caso de erro no banco de dados
     */
    public Usuario buscarPorId(int id) throws SQLException {
        return usuarioDAO.buscarPorId(id);
    }

/**
     * Lista todos os usuários cadastrados no sistema
     * 
     * @return Lista de usuários
     * @throws SQLException Em caso de erro no banco de dados
     */
    public List<Usuario> listarTodos() throws SQLException {
        return usuarioDAO.listarTodos();
    }

/**
     * Atualiza os dados de um usuário existente
     * 
     * @param usuario Usuário com os dados atualizados
     * @throws SQLException Em caso de erro no banco de dados
     * @throws IllegalArgumentException Se o usuário for nulo ou não existir
     */
    public void atualizarUsuario(Usuario usuario) throws SQLException, IllegalArgumentException {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
        usuarioDAO.atualizar(usuario);
    }

    // Método para buscar usuário pelo nome de usuário
    public Usuario buscarPorUsername(String username) throws SQLException {
        return usuarioDAO.buscarPorUsername(username);
    }

/**
     * Exclui um usuário do sistema
     * 
     * @param id ID do usuário a ser excluído
     * @throws SQLException Em caso de erro no banco de dados
     * @throws IllegalArgumentException Se o usuário não existir
     */
    public void excluirUsuario(int id) throws SQLException, IllegalArgumentException {
        if (id <= 0) {
            throw new IllegalArgumentException("ID de usuário inválido");
        }
        usuarioDAO.deletar(id);
    }

}
