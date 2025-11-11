package br.com.warrick.biblioteca.controller;

import br.com.warrick.biblioteca.persistence.model.Categoria;
import br.com.warrick.biblioteca.persistence.model.Livro;
import br.com.warrick.biblioteca.service.LivroService;
import java.awt.Color;
import java.util.List;

public class LivroController {
    
    private final LivroService livroService;
    
    public LivroController() {
        this.livroService = new LivroService();
    }
    
    // Métodos para gerenciamento de livros
    public void adicionarLivro(String titulo, String autor, String isbn, String editora, 
                              String genero, Integer anoPublicacao, Integer numeroPaginas,
                              String colecao, Integer numeroColecao, 
                              Color corLombada, Color corBarras, Color corFonte) {
        
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setIsbn(isbn);
        livro.setEditora(editora);
        livro.setGenero(genero);
        livro.setAnoPublicacao(anoPublicacao);
        livro.setNumeroPaginas(numeroPaginas);
        livro.setColecao(colecao);
        livro.setNumeroColecao(numeroColecao);
        
        // Criar e configurar a categoria com as cores fornecidas
        Categoria categoria = new Categoria();
        categoria.setCorLombada(corLombada);
        categoria.setCorBarras(corBarras);
        categoria.setCorFonte(corFonte);
        livro.setCategoria(categoria);
        
        livroService.cadastrarLivro(livro);
    }
    
    public void atualizarLivro(Integer id, String titulo, String autor, String isbn, String editora, 
                              String genero, Integer anoPublicacao, Integer numeroPaginas,
                              String colecao, Integer numeroColecao, 
                              Color corLombada, Color corBarras, Color corFonte) {
        
        Livro livro = livroService.buscarLivroPorId(id);
        if (livro != null) {
            livro.setTitulo(titulo);
            livro.setAutor(autor);
            livro.setIsbn(isbn);
            livro.setEditora(editora);
            livro.setGenero(genero);
            livro.setAnoPublicacao(anoPublicacao);
            livro.setNumeroPaginas(numeroPaginas);
            livro.setColecao(colecao);
            livro.setNumeroColecao(numeroColecao);
            
            // Atualizar as cores através da categoria
            if (livro.getCategoria() == null) {
                livro.setCategoria(new Categoria());
            }
            livro.getCategoria().setCorLombada(corLombada);
            livro.getCategoria().setCorBarras(corBarras);
            livro.getCategoria().setCorFonte(corFonte);
            
            livroService.atualizarLivro(livro);
        }
    }
    
    public void removerLivro(Integer id) {
        livroService.removerLivro(id);
    }
    
    public Livro buscarLivro(Integer id) {
        return livroService.buscarLivroPorId(id);
    }
    
    public List<Livro> listarTodosLivros() {
        return livroService.listarTodosLivros();
    }
    
    public List<Livro> pesquisarLivros(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return livroService.listarTodosLivros();
        }
        
        // Tenta buscar por título
        List<Livro> resultado = livroService.buscarLivrosPorTitulo(termo);
        
        // Se não encontrou por título, tenta por autor
        if (resultado.isEmpty()) {
            resultado = livroService.buscarLivrosPorAutor(termo);
        }
        
        // Se ainda não encontrou, tenta por coleção
        if (resultado.isEmpty()) {
            resultado = livroService.buscarLivrosPorColecao(termo);
        }
        
        return resultado;
    }
    
    public void registrarEmprestimo(Integer livroId) {
        livroService.registrarEmprestimo(livroId);
    }
    
    public void registrarDevolucao(Integer livroId) {
        livroService.registrarDevolucao(livroId);
    }
    
    public List<Livro> listarLivrosDisponiveis() {
        return livroService.listarLivrosDisponiveis();
    }
    
    public List<Livro> listarLivrosEmprestados() {
        return livroService.listarLivrosEmprestados();
    }
}
