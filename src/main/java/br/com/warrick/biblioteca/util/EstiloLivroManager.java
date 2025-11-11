package br.com.warrick.biblioteca.util;

import br.com.warrick.biblioteca.model.Categoria;
import br.com.warrick.biblioteca.model.EstiloLivro;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Gerenciador de estilos para diferentes categorias de livros.
 * Permite configurar estilos personalizados para cada categoria.
 */
public class EstiloLivroManager {
    
    private static EstiloLivroManager instancia;
    private final Map<Integer, EstiloLivro> estilosPersonalizados;
    
    private EstiloLivroManager() {
        this.estilosPersonalizados = new HashMap<>();
        inicializarEstilosPadrao();
    }
    
    /**
     * Obtém a instância única do gerenciador de estilos.
     */
    public static synchronized EstiloLivroManager getInstance() {
        if (instancia == null) {
            instancia = new EstiloLivroManager();
        }
        return instancia;
    }
    
    /**
     * Inicializa os estilos padrão para as categorias principais.
     */
    private void inicializarEstilosPadrao() {
        // Estilo para Ficção
        EstiloLivro estiloFiccao = new EstiloLivro.Builder()
                .corLombada(new Color(75, 0, 130))     // Roxo escuro
                .corBarras(new Color(147, 112, 219))   // Roxo claro
                .corFonte(Color.WHITE)
                .corFundoPainel(new Color(245, 245, 255)) // Azul muito claro
                .tamanhoFonteTitulo(12)
                .tamanhoFonteAutor(10)
                .exibirBarras(true)
                .exibirNumeroColecao(true)
                .build();
        
        // Estilo para Técnico
        EstiloLivro estiloTecnico = new EstiloLivro.Builder()
                .corLombada(new Color(0, 0, 139))      // Azul escuro
                .corBarras(new Color(100, 149, 237))   // Azul claro
                .corFonte(Color.WHITE)
                .corFundoPainel(new Color(240, 248, 255)) // Azul alice
                .tamanhoFonteTitulo(12)
                .tamanhoFonteAutor(10)
                .exibirBarras(true)
                .exibirNumeroColecao(true)
                .build();
        
        // Estilo para Religião
        EstiloLivro estiloReligiao = new EstiloLivro.Builder()
                .corLombada(new Color(139, 0, 0))      // Vermelho escuro
                .corBarras(new Color(205, 92, 92))     // Vermelho claro
                .corFonte(Color.WHITE)
                .corFundoPainel(new Color(255, 240, 240)) // Vermelho muito claro
                .tamanhoFonteTitulo(12)
                .tamanhoFonteAutor(10)
                .exibirBarras(true)
                .exibirNumeroColecao(true)
                .build();
        
        // Estilo para Infantil
        EstiloLivro estiloInfantil = new EstiloLivro.Builder()
                .corLombada(new Color(255, 215, 0))    // Dourado
                .corBarras(new Color(255, 255, 0))     // Amarelo
                .corFonte(Color.BLACK)
                .corFundoPainel(new Color(255, 255, 200)) // Amarelo muito claro
                .tamanhoFonteTitulo(14)  // Fonte maior para melhor legibilidade
                .tamanhoFonteAutor(12)
                .exibirBarras(true)
                .exibirNumeroColecao(true)
                .build();
        
        // Adiciona os estilos ao mapa
        estilosPersonalizados.put(Categoria.FICCAO.getId(), estiloFiccao);
        estilosPersonalizados.put(Categoria.TECNICO.getId(), estiloTecnico);
        estilosPersonalizados.put(Categoria.RELIGIAO.getId(), estiloReligiao);
        estilosPersonalizados.put(Categoria.INFANTIL.getId(), estiloInfantil);
    }
    
    /**
     * Obtém o estilo para uma categoria específica.
     * Se um estilo personalizado foi definido, retorna-o.
     * Caso contrário, retorna o estilo padrão da categoria.
     */
    public EstiloLivro getEstiloParaCategoria(Categoria categoria) {
        if (categoria == null) {
            return EstiloLivro.criarEstiloPadrao(null);
        }
        
        // Verifica se há um estilo personalizado para esta categoria
        EstiloLivro estiloPersonalizado = estilosPersonalizados.get(categoria.getId());
        if (estiloPersonalizado != null) {
            return estiloPersonalizado;
        }
        
        // Se não houver estilo personalizado, retorna o estilo padrão da categoria
        return categoria.getEstilo();
    }
    
    /**
     * Define um estilo personalizado para uma categoria.
     * Se estilo for null, o estilo personalizado será removido.
     */
    public void setEstiloParaCategoria(Categoria categoria, EstiloLivro estilo) {
        if (categoria == null) {
            return;
        }
        
        if (estilo == null) {
            estilosPersonalizados.remove(categoria.getId());
        } else {
            estilosPersonalizados.put(categoria.getId(), estilo);
        }
    }
    
    /**
     * Restaura o estilo padrão para uma categoria.
     */
    public void restaurarEstiloPadrao(Categoria categoria) {
        if (categoria != null) {
            estilosPersonalizados.remove(categoria.getId());
        }
    }
}
