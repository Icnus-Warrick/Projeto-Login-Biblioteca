package br.com.warrick.biblioteca.model;

import java.awt.Color;
import java.io.Serializable;

public class Livro implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String titulo;
    private String autor;
    private String editora;
    private String isbn;
    private String colecao;
    private String genero;
    private Categoria categoria;
    private String idioma;
    private String formato;
    private Integer anoPublicacao;
    private Integer numeroPaginas;
    private Integer numeroColecao;
    private boolean disponivel;
    
    // Métodos de conveniência para acessar as cores da categoria
    public Color getCorLombada() {
        return categoria != null ? categoria.getCorLombada() : Color.GRAY;
    }
    
    public Color getCorBarras() {
        return categoria != null ? categoria.getCorBarras() : Color.LIGHT_GRAY;
    }
    
    public Color getCorFonte() {
        return categoria != null ? categoria.getCorFonte() : Color.BLACK;
    }

    // Construtores
    public Livro() {
        this.disponivel = true;
    }

    public Livro(String titulo, String autor, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.disponivel = true;
    }
    
    public Livro(String titulo, String autor, String isbn, Categoria categoria) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.categoria = categoria;
        this.disponivel = true;
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getColecao() {
        return colecao;
    }

    public void setColecao(String colecao) {
        this.colecao = colecao;
    }

    public String getGenero() {
        return genero;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public Integer getNumeroColecao() {
        return numeroColecao;
    }

    public void setNumeroColecao(Integer numeroColecao) {
        this.numeroColecao = numeroColecao;
    }

    public Color getCorLombada() {
        return corLombada;
    }

    public void setCorLombada(Color corLombada) {
        this.corLombada = corLombada;
    }

    public Color getCorBarras() {
        return corBarras;
    }

    public void setCorBarras(Color corBarras) {
        this.corBarras = corBarras;
    }

    public Color getCorFonte() {
        return corFonte;
    }

    public void setCorFonte(Color corFonte) {
        this.corFonte = corFonte;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Livro{" + "id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", disponivel=" + disponivel + '}';
    }
}
