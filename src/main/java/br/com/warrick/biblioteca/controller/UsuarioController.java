package br.com.warrick.biblioteca.controller;

import br.com.warrick.biblioteca.persistence.model.Usuario;
import br.com.warrick.biblioteca.service.UsuarioService;

import java.sql.SQLException;

/**
 * Controlador responsável por gerenciar as operações de autenticação de usuários
 * Atua como intermediário entre a camada de visualização e a camada de serviço
 * para operações de login, registro e gerenciamento de contas.
 *
 * Projeto: Sistema de Autenticação
 *
 * @author Warrick
 * @version 1.0.0
 * @since 28/10/2025
 */
public class UsuarioController {

    /* ============================================== ATRIBUTOS ============================================== */
    private final UsuarioService usuarioService = new UsuarioService();
    
    /* ========================================= MÉTODOS DE AUTENTICAÇÃO ======================================= */
    /**
     * Autentica um usuário com nome de usuário e senha
     * @param usuario Nome de usuário
     * @param senha Senha do usuário
     * @return boolean - true se a autenticação for bem-sucedida, false caso contrário
     */
    public boolean fazerLogin(String usuario, String senha) {
        try {
            Usuario user = usuarioService.autenticar(usuario, senha);
            return user != null;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ========================================= MÉTODOS DE CADASTRO ========================================= */
    /**
     * Cadastra um novo usuário no sistema
     * @param nome Nome completo do usuário
     * @param username Nome de usuário único
     * @param senha Senha do usuário
     * @param email Email do usuário
     * @param estiloPreferido Estilo de leitura preferido
     * @return boolean - true se o cadastro for bem-sucedido, false caso contrário
     */
    public boolean cadastrarUsuario(String nome, String username, String senha, String email, String estiloPreferido) {
        try {
            usuarioService.cadastrarUsuario(nome, username, senha, email, estiloPreferido);
            return true;
        } catch (IllegalArgumentException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ========================================= MÉTODOS DE CONSULTA ========================================= */
    /**
     * Busca um usuário pelo nome de usuário
     * @param username Nome de usuário a ser buscado
     * @return Usuario - Objeto Usuario se encontrado, null caso contrário
     */
    public Usuario buscarUsuarioPorUsername(String username) {
        try {
            return usuarioService.buscarPorUsername(username);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* ========================================= MÉTODOS DE ATUALIZAÇÃO ======================================= */
    /**
     * Atualiza os dados de um usuário existente
     * @param usuario Objeto Usuario com os dados atualizados
     * @return boolean - true se a atualização for bem-sucedida, false caso contrário
     */
    public boolean atualizarUsuario(Usuario usuario) {
        try {
            usuarioService.atualizarUsuario(usuario);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ========================================= MÉTODOS DE EXCLUSÃO ========================================== */

    /**
     * Remove um usuário do sistema pelo seu ID
     * @param id ID do usuário a ser removido
     * @return boolean - true se a exclusão for bem-sucedida, false caso contrário
     */
    public boolean excluirUsuario(int id) {
        try {
            usuarioService.excluirUsuario(id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
