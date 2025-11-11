package br.com.warrick.biblioteca.persistence.model;

import java.awt.Color;
import java.io.Serializable;

/**
 * Classe responsável por gerenciar os estilos visuais dos livros por categoria.
 * Define cores, fontes e outros atributos visuais para diferentes categorias de livros.
 * 
 * @author Warrick
 * @since 11/11/2025
 */
public class EstiloLivro implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final Color corLombada;
    private final Color corBarras;
    private final Color corFonte;
    private final Color corFundoPainel;
    private final Color corBorda;
    private final int tamanhoFonteTitulo;
    private final int tamanhoFonteAutor;
    private final boolean exibirBarras;
    private final boolean exibirNumeroColecao;
    
    /**
     * Construtor privado. Use o Builder para criar instâncias.
     */
    private EstiloLivro(Builder builder) {
        this.corLombada = builder.corLombada;
        this.corBarras = builder.corBarras;
        this.corFonte = builder.corFonte;
        this.corFundoPainel = builder.corFundoPainel;
        this.corBorda = builder.corBorda;
        this.tamanhoFonteTitulo = builder.tamanhoFonteTitulo;
        this.tamanhoFonteAutor = builder.tamanhoFonteAutor;
        this.exibirBarras = builder.exibirBarras;
        this.exibirNumeroColecao = builder.exibirNumeroColecao;
    }
    
    // Getters
    public Color getCorLombada() { return corLombada; }
    public Color getCorBarras() { return corBarras; }
    public Color getCorFonte() { return corFonte; }
    public Color getCorFundoPainel() { return corFundoPainel; }
    public Color getCorBorda() { return corBorda; }
    public int getTamanhoFonteTitulo() { return tamanhoFonteTitulo; }
    public int getTamanhoFonteAutor() { return tamanhoFonteAutor; }
    public boolean isExibirBarras() { return exibirBarras; }
    public boolean isExibirNumeroColecao() { return exibirNumeroColecao; }
    
    /**
     * Cria um estilo padrão baseado nas cores da categoria.
     */
    public static EstiloLivro criarEstiloPadrao(Categoria categoria) {
        return new Builder()
            .corLombada(categoria.getCorLombada())
            .corBarras(categoria.getCorBarras())
            .corFonte(categoria.getCorFonte())
            .corFundoPainel(new Color(250, 250, 250))
            .corBorda(new Color(200, 200, 200))
            .tamanhoFonteTitulo(14)
            .tamanhoFonteAutor(12)
            .exibirBarras(true)
            .exibirNumeroColecao(true)
            .build();
    }
    
    /**
     * Cria um estilo personalizado baseado em um estilo existente, mas com cores da categoria.
     */
    public static EstiloLivro criarEstiloPersonalizado(Categoria categoria, EstiloLivro estiloBase) {
        return new Builder()
            .corLombada(categoria.getCorLombada())
            .corBarras(estiloBase.getCorBarras())
            .corFonte(categoria.getCorFonte())
            .corFundoPainel(estiloBase.getCorFundoPainel())
            .corBorda(estiloBase.getCorBorda())
            .tamanhoFonteTitulo(estiloBase.getTamanhoFonteTitulo())
            .tamanhoFonteAutor(estiloBase.getTamanhoFonteAutor())
            .exibirBarras(estiloBase.isExibirBarras())
            .exibirNumeroColecao(estiloBase.isExibirNumeroColecao())
            .build();
    }
    
    /**
     * Builder para criar instâncias de EstiloLivro de forma fluente.
     */
    public static class Builder {
        // Valores padrão
        private Color corLombada = new Color(150, 150, 150);
        private Color corBarras = new Color(100, 100, 100);
        private Color corFonte = Color.BLACK;
        private Color corFundoPainel = new Color(250, 250, 250);
        private Color corBorda = new Color(200, 200, 200);
        private int tamanhoFonteTitulo = 14;
        private int tamanhoFonteAutor = 12;
        private boolean exibirBarras = true;
        private boolean exibirNumeroColecao = true;
        
        public Builder corLombada(Color corLombada) {
            this.corLombada = corLombada;
            return this;
        }
        
        public Builder corBarras(Color corBarras) {
            this.corBarras = corBarras;
            return this;
        }
        
        public Builder corFonte(Color corFonte) {
            this.corFonte = corFonte;
            return this;
        }
        
        public Builder corFundoPainel(Color corFundoPainel) {
            this.corFundoPainel = corFundoPainel;
            return this;
        }
        
        public Builder corBorda(Color corBorda) {
            this.corBorda = corBorda;
            return this;
        }
        
        public Builder tamanhoFonteTitulo(int tamanho) {
            this.tamanhoFonteTitulo = tamanho;
            return this;
        }
        
        public Builder tamanhoFonteAutor(int tamanho) {
            this.tamanhoFonteAutor = tamanho;
            return this;
        }
        
        public Builder exibirBarras(boolean exibir) {
            this.exibirBarras = exibir;
            return this;
        }
        
        public Builder exibirNumeroColecao(boolean exibir) {
            this.exibirNumeroColecao = exibir;
            return this;
        }
        
        public EstiloLivro build() {
            return new EstiloLivro(this);
        }
    }
    
    @Override
    public String toString() {
        return String.format(
            "EstiloLivro{corLombada=%s, corBarras=%s, corFonte=%s}",
            corLombada, corBarras, corFonte
        );
    }
}
