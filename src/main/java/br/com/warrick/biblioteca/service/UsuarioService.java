package br.com.warrick.biblioteca.service;

import br.com.warrick.biblioteca.dao.UsuarioDAO;
import br.com.warrick.biblioteca.model.Usuario;

import java.sql.SQLException;
import java.util.List;

/**
 * Projeto: Biblioteca
 * @author Warrick
 * @since 28/10/2025
 */
public class UsuarioService {

    // DAO para acesso aos dados do usuário
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    // Método para cadastrar um novo usuário com validações de entrada
    public void cadastrarUsuario(String nome, String usuario, String senha, String email, String estiloPreferido) throws SQLException {
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

        Usuario novoUsuario = new Usuario(nome, usuario, senha, email, estiloPreferido);
        usuarioDAO.salvar(novoUsuario);
    }

    // Método para autenticar usuário com nome e senha
    public Usuario autenticar(String usuario, String senha) throws SQLException {
        Usuario user = usuarioDAO.buscarPorUsername(usuario);
        if (user != null && user.getSenha().equals(senha)) {
            return user;
        }
        return null;
    }

    // Método para buscar usuário pelo ID
    public Usuario buscarPorId(int id) throws SQLException {
        return usuarioDAO.buscarPorId(id);
    }

    // Método para listar todos os usuários
    public List<Usuario> listarTodos() throws SQLException {
        return usuarioDAO.listarTodos();
    }

    // Método para atualizar dados do usuário
    public void atualizarUsuario(Usuario usuario) throws SQLException {
        usuarioDAO.atualizar(usuario);
    }

    // Método para buscar usuário pelo nome de usuário
    public Usuario buscarPorUsername(String username) throws SQLException {
        return usuarioDAO.buscarPorUsername(username);
    }

    // Método para deletar usuário pelo ID
    public void deletarUsuario(int id) throws SQLException {
        usuarioDAO.deletar(id);
    }

}
