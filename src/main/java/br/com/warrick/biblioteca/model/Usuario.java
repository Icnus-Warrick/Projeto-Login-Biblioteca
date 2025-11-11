package br.com.warrick.biblioteca.model;

/**
 * Classe que representa um usuário do sistema
 * Contém informações como nome, usuário, senha, email e preferências
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 28/10/2025
 */
public class Usuario {

    /* ============================================== ATRIBUTOS ============================================== */
    /** Identificador único do usuário na base de dados */
    private int id;
    
    /** Nome completo do usuário */
    private String nome;
    
    /** Nome de usuário para login (deve ser único) */
    private String username;
    
    /** Senha criptografada do usuário */
    private String senha;
    
    /** Endereço de email do usuário */
    private String email;
    
    /** Estilo visual preferido pelo usuário */
    private String estiloPreferido;

    /* ============================================== CONSTRUTORES ============================================ */
    /**
     * Construtor padrão sem argumentos
     */
    public Usuario() {
    }

    /**
     * Construtor com parâmetros para inicializar um usuário
     * 
     * @param nome Nome completo do usuário
     * @param username Nome de usuário para login
     * @param senha Senha do usuário (será criptografada)
     * @param email Email do usuário
     * @param estiloPreferido Preferência de estilo visual
     */
    public Usuario(String nome, String username, String senha, String email, String estiloPreferido) {
        this.nome = nome;
        this.username = username;
        this.senha = senha;
        this.email = email;
        this.estiloPreferido = estiloPreferido;
    }

    /* ============================================== MÉTODOS PÚBLICOS ========================================= */
    
    /* ============================================== GETTERS E SETTERS ========================================= */
    /**
     * Obtém o ID do usuário
     * @return int - ID do usuário
     */
    public int getId() {
        return id;
    }

    /**
     * Obtém o nome do usuário
     * @return String - Nome completo do usuário
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém o nome de usuário para login
     * @return String - Nome de usuário
     */
    public String getUsername() {
        return username;
    }

    /**
     * Obtém a senha (criptografada) do usuário
     * @return String - Senha criptografada
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Obtém o email do usuário
     * @return String - Endereço de email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obtém o estilo visual preferido do usuário
     * @return String - Nome do estilo preferido
     */
    public String getEstiloPreferido() {
        return estiloPreferido;
    }

    /* =================================================== SETTERS ================================================== */
    /**
     * Define o ID do usuário
     * @param id Novo ID do usuário
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Define o nome do usuário
     * @param nome Novo nome do usuário
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Define o nome de usuário para login
     * @param username Novo nome de usuário
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Define a senha do usuário (deve ser fornecida já criptografada)
     * @param senha Nova senha (já criptografada)
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Define o email do usuário
     * @param email Novo endereço de email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Define o estilo visual preferido do usuário
     * @param estiloPreferido Nome do estilo preferido
     */
    public void setEstiloPreferido(String estiloPreferido) {
        this.estiloPreferido = estiloPreferido;
    }

    /* =============================================== OUTROS MÉTODOS =============================================== */
    /**
     * Retorna uma representação em string do objeto Usuário
     * @return String - Representação do usuário
     */
    @Override
    public String toString() {
        return "Usuario{" + 
               "id=" + id + 
               ", nome='" + nome + '\'' + 
               ", username='" + username + '\'' + 
               ", senha='[PROTECTED]'" + 
               ", email='" + email + '\'' + 
               ", estiloPreferido='" + estiloPreferido + '\'' + 
               '}';
    }
}
