package br.com.warrick.biblioteca.persistence.model;

import java.io.Serializable;

/**
 * Classe que representa um usuário do sistema.
 * Contém informações como nome, usuário, senha, email e preferências.
 * 
 * @author Warrick
 * @since 11/11/2025
 */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
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
    
    /** Indica se o usuário é administrador */
    private boolean admin = false;
    
    /** Indica se o usuário está ativo no sistema */
    private boolean ativo = true;
    
    // Construtores
    public Usuario() {
        this.ativo = true;
    }
    
    public Usuario(String nome, String username, String senha, String email) {
        this();
        this.nome = nome;
        this.username = username;
        this.senha = senha;
        this.email = email;
    }
    
    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the estiloPreferido
     */
    public String getEstiloPreferido() {
        return estiloPreferido;
    }

    /**
     * @param estiloPreferido the estiloPreferido to set
     */
    public void setEstiloPreferido(String estiloPreferido) {
        this.estiloPreferido = estiloPreferido;
    }
    
    /**
     * @deprecated Use getEstiloPreferido() em vez disso
     */
    @Deprecated
    public String getTema() {
        return getEstiloPreferido();
    }

    /**
     * @deprecated Use setEstiloPreferido() em vez disso
     */
    @Deprecated
    public void setTema(String tema) {
        setEstiloPreferido(tema);
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    @Override
    public String toString() {
        return String.format("Usuario{id=%d, nome='%s', usuario='%s', email='%s', ativo=%b}", 
            id, nome, username, email, ativo);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Usuario usuario = (Usuario) o;
        return id == usuario.id;
    }
    
    @Override
    public int hashCode() {
        return id;
    }
}
