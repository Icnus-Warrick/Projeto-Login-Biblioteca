package br.com.warrick.biblioteca.dao;

import br.com.warrick.biblioteca.model.Livro;
import java.util.List;

public interface LivroDAO {
    // CRUD básico
    void salvar(Livro livro);
    void atualizar(Livro livro);
    void deletar(Integer id);
    Livro buscarPorId(Integer id);
    List<Livro> listarTodos();
    
    // Métodos específicos
    List<Livro> buscarPorTitulo(String titulo);
    List<Livro> buscarPorAutor(String autor);
    List<Livro> buscarPorColecao(String colecao);
    List<Livro> buscarDisponiveis();
    List<Livro> buscarEmprestados();
}
