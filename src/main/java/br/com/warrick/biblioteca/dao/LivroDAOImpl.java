package br.com.warrick.biblioteca.dao;

import br.com.warrick.biblioteca.model.Livro;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LivroDAOImpl implements LivroDAO {
    
    private static final Map<Integer, Livro> livros = new HashMap<>();
    private static final AtomicInteger contador = new AtomicInteger(0);
    
    @Override
    public void salvar(Livro livro) {
        if (livro.getId() == null) {
            livro.setId(contador.incrementAndGet());
        }
        livros.put(livro.getId(), livro);
    }
    
    @Override
    public void atualizar(Livro livro) {
        if (livro.getId() != null && livros.containsKey(livro.getId())) {
            livros.put(livro.getId(), livro);
        }
    }
    
    @Override
    public void deletar(Integer id) {
        livros.remove(id);
    }
    
    @Override
    public Livro buscarPorId(Integer id) {
        return livros.get(id);
    }
    
    @Override
    public List<Livro> listarTodos() {
        return new ArrayList<>(livros.values());
    }
    
    @Override
    public List<Livro> buscarPorTitulo(String titulo) {
        List<Livro> resultado = new ArrayList<>();
        for (Livro livro : livros.values()) {
            if (livro.getTitulo() != null && 
                livro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                resultado.add(livro);
            }
        }
        return resultado;
    }
    
    @Override
    public List<Livro> buscarPorAutor(String autor) {
        List<Livro> resultado = new ArrayList<>();
        for (Livro livro : livros.values()) {
            if (livro.getAutor() != null && 
                livro.getAutor().toLowerCase().contains(autor.toLowerCase())) {
                resultado.add(livro);
            }
        }
        return resultado;
    }
    
    @Override
    public List<Livro> buscarPorColecao(String colecao) {
        List<Livro> resultado = new ArrayList<>();
        for (Livro livro : livros.values()) {
            if (livro.getColecao() != null && 
                livro.getColecao().equalsIgnoreCase(colecao)) {
                resultado.add(livro);
            }
        }
        return resultado;
    }
    
    @Override
    public List<Livro> buscarDisponiveis() {
        List<Livro> resultado = new ArrayList<>();
        for (Livro livro : livros.values()) {
            if (livro.isDisponivel()) {
                resultado.add(livro);
            }
        }
        return resultado;
    }
    
    @Override
    public List<Livro> buscarEmprestados() {
        List<Livro> resultado = new ArrayList<>();
        for (Livro livro : livros.values()) {
            if (!livro.isDisponivel()) {
                resultado.add(livro);
            }
        }
        return resultado;
    }
}
