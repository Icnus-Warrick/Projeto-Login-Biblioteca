package br.com.warrick.biblioteca.persistence.dao;

import br.com.warrick.biblioteca.persistence.model.Livro;
import java.util.List;

/**
 * Interface para operações de persistência da entidade Livro.
 *
 * @author Warrick
 * @since 11/11/2025
 */
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
