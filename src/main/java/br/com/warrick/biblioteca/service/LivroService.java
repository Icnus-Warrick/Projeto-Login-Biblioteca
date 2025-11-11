package br.com.warrick.biblioteca.service;

import br.com.warrick.biblioteca.dao.LivroDAO;
import br.com.warrick.biblioteca.dao.LivroDAOImpl;
import br.com.warrick.biblioteca.model.Livro;
import java.util.List;

public class LivroService {
    
    private final LivroDAO livroDAO;
    
    public LivroService() {
        this.livroDAO = new LivroDAOImpl();
    }
    
    // Métodos de negócio
    public void cadastrarLivro(Livro livro) {
        validarLivro(livro);
        livroDAO.salvar(livro);
    }
    
    public void atualizarLivro(Livro livro) {
        if (livro.getId() == null) {
            throw new IllegalArgumentException("ID do livro não pode ser nulo para atualização");
        }
        validarLivro(livro);
        livroDAO.atualizar(livro);
    }
    
    public void removerLivro(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do livro não pode ser nulo");
        }
        livroDAO.deletar(id);
    }
    
    public Livro buscarLivroPorId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID do livro não pode ser nulo");
        }
        return livroDAO.buscarPorId(id);
    }
    
    public List<Livro> listarTodosLivros() {
        return livroDAO.listarTodos();
    }
    
    public List<Livro> buscarLivrosPorTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Título não pode ser vazio");
        }
        return livroDAO.buscarPorTitulo(titulo);
    }
    
    public List<Livro> buscarLivrosPorAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("Autor não pode ser vazio");
        }
        return livroDAO.buscarPorAutor(autor);
    }
    
    public List<Livro> buscarLivrosPorColecao(String colecao) {
        if (colecao == null || colecao.trim().isEmpty()) {
            throw new IllegalArgumentException("Coleção não pode ser vazia");
        }
        return livroDAO.buscarPorColecao(colecao);
    }
    
    public List<Livro> listarLivrosDisponiveis() {
        return livroDAO.buscarDisponiveis();
    }
    
    public List<Livro> listarLivrosEmprestados() {
        return livroDAO.buscarEmprestados();
    }
    
    public void registrarEmprestimo(Integer livroId) {
        Livro livro = buscarLivroPorId(livroId);
        if (!livro.isDisponivel()) {
            throw new IllegalStateException("Livro já está emprestado");
        }
        livro.setDisponivel(false);
        livroDAO.atualizar(livro);
    }
    
    public void registrarDevolucao(Integer livroId) {
        Livro livro = buscarLivroPorId(livroId);
        if (livro.isDisponivel()) {
            throw new IllegalStateException("Livro não está emprestado");
        }
        livro.setDisponivel(true);
        livroDAO.atualizar(livro);
    }
    
    private void validarLivro(Livro livro) {
        if (livro.getTitulo() == null || livro.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("Título do livro é obrigatório");
        }
        if (livro.getAutor() == null || livro.getAutor().trim().isEmpty()) {
            throw new IllegalArgumentException("Autor do livro é obrigatório");
        }
    }
}
