package br.com.warrick.biblioteca.model;

import java.awt.Color;
import java.io.Serializable;

/**
 * Classe responsável por gerenciar os estilos visuais dos livros por categoria.
 * Define cores, fontes e outros atributos visuais para diferentes categorias de livros.
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
     * Builder para criar instâncias de EstiloLivro de forma fluente.
     */
    public static class Builder {
        // Valores padrão
        private Color corLombada = new Color(75, 0, 130);  // Roxo escuro
        private Color corBarras = new Color(147, 112, 219); // Roxo claro
        private Color corFonte = Color.WHITE;
        private Color corFundoPainel = Color.WHITE;
        private Color corBorda = new Color(200, 200, 200);
        private int tamanhoFonteTitulo = 12;
        private int tamanhoFonteAutor = 10;
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
    
    /**
     * Cria um estilo padrão para uma categoria específica.
     */
    public static EstiloLivro criarEstiloPadrao(Categoria categoria) {
        if (categoria == null) {
            return new EstiloLivro.Builder()
                .corLombada(Color.GRAY)
                .corBarras(Color.LIGHT_GRAY)
                .corFonte(Color.BLACK)
                .build();
        }
        
        // Cores baseadas na categoria
        Color corLombada = categoria.getCorLombada();
        Color corBarras = categoria.getCorBarras();
        Color corFonte = categoria.getCorFonte();
        
        return new EstiloLivro.Builder()
            .corLombada(corLombada)
            .corBarras(corBarras)
            .corFonte(corFonte)
            .build();
    }
    
    /**
     * Cria um estilo personalizado para uma categoria específica.
     */
    public static EstiloLivro criarEstiloPersonalizado(Categoria categoria, EstiloLivro estiloBase) {
        if (estiloBase == null) {
            return criarEstiloPadrao(categoria);
        }
        
        // Se não for informada uma categoria, retorna o estilo base
        if (categoria == null) {
            return estiloBase;
        }
        
        // Cria um novo estilo baseado no estilo base, mas com as cores da categoria
        return new EstiloLivro.Builder()
            .corLombada(categoria.getCorLombada())
            .corBarras(categoria.getCorBarras())
            .corFonte(categoria.getCorFonte())
            .corFundoPainel(estiloBase.getCorFundoPainel())
            .corBorda(estiloBase.getCorBorda())
            .tamanhoFonteTitulo(estiloBase.getTamanhoFonteTitulo())
            .tamanhoFonteAutor(estiloBase.getTamanhoFonteAutor())
            .exibirBarras(estiloBase.isExibirBarras())
            .exibirNumeroColecao(estiloBase.isExibirNumeroColecao())
            .build();
    }
}
