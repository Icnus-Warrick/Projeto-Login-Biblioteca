package br.com.warrick.biblioteca.controller;

import br.com.warrick.biblioteca.model.Usuario;
import br.com.warrick.biblioteca.service.UsuarioService;

import java.sql.SQLException;

/**
 * Projeto: Biblioteca
 * @author Warrick
 * @since 28/10/2025
 */
public class UsuarioController {

    // Serviço para operações relacionadas ao usuário
    private UsuarioService usuarioService = new UsuarioService();
    
    /* =================================================== MÉTODOS ================================================== */
    // Método para autenticar o usuário com nome e senha
    public boolean fazerLogin(String usuario, String senha) {
        try {
            Usuario user = usuarioService.autenticar(usuario, senha);
            return user != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para cadastrar um novo usuário no sistema
    public boolean cadastrarUsuario(String nome, String username, String senha, String email, String estiloPreferido) {
        try {
            usuarioService.cadastrarUsuario(nome, username, senha, email, estiloPreferido);
            return true;
        } catch (IllegalArgumentException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para buscar um usuário pelo nome de usuário
    public Usuario buscarUsuarioPorUsername(String username) {
        try {
            return usuarioService.buscarPorUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método para atualizar os dados de um usuário existente
    public boolean atualizarUsuario(Usuario usuario) {
        try {
            usuarioService.atualizarUsuario(usuario);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para deletar um usuário pelo ID
    public boolean deletarUsuario(int id) {
        try {
            usuarioService.deletarUsuario(id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
