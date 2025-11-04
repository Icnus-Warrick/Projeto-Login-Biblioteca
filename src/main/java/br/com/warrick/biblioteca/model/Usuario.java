package br.com.warrick.biblioteca.model;

/**
 * Projeto: Biblioteca
 * @author Warrick
 * @since 28/10/2025
 */
public class Usuario {

    // Identificador único do usuário na base de dados
    private int id;
    // Nome completo do usuário
    private String nome;
    // Nome de usuário para login
    private String username;
    // Senha criptografada do usuário
    private String senha;
    // Endereço de email do usuário
    private String email;
    // Estilo visual preferido pelo usuário
    private String estiloPreferido;

    /* ================================================= CONSTRUTOR ================================================= */
    public Usuario() {
    }

    // Construtor com parâmetros para inicializar um usuário com dados fornecidos
    public Usuario(String nome, String username, String senha, String email, String estiloPreferido) {
        this.nome = nome;
        this.username = username;
        this.senha = senha;
        this.email = email;
        this.estiloPreferido = estiloPreferido;
    }

    /* =================================================== GETTERS ================================================== */
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getUsername() {
        return username;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }

    public String getEstiloPreferido() {
        return estiloPreferido;
    }

    /* =================================================== SETTERS ================================================== */
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEstiloPreferido(String estiloPreferido) {
        this.estiloPreferido = estiloPreferido;
    }

    /* =============================================== OUTROS MÉTODOS =============================================== */
    // Método que retorna uma representação em string do objeto Usuario
    @Override
    public String toString() {
        return "Usuario{"
                + "id=" + id
                + ", nome='" + nome + '\''
                + ", username='" + username + '\''
                + ", email='" + email + '\''
                + ", estiloPreferido='" + estiloPreferido + '\''
                + '}';
    }
}
