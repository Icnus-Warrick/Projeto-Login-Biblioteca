package br.com.warrick.biblioteca.Configuracao;

import br.com.warrick.biblioteca.persistence.model.Categoria;
import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável por gerenciar as configurações de exibição das capas e lombadas dos livros.
 * Permite configurar estilos visuais diferentes para cada categoria de livro.
 */
public class ConfiguracaoCapa implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Instância única (Singleton)
    private static ConfiguracaoCapa instancia;
    
    // Mapa de configurações por categoria
    private final Map<Integer, ConfiguracaoEstilo> configuracoes;
    
    // Configuração padrão
    private final ConfiguracaoEstilo configuracaoPadrao;
    
    /**
     * Construtor privado para o padrão Singleton.
     * Inicializa as configurações padrão.
     */
    private ConfiguracaoCapa() {
        this.configuracoes = new HashMap<>();
        this.configuracaoPadrao = new ConfiguracaoEstilo();
        inicializarConfiguracoesPadrao();
    }
    
    /**
     * Obtém a instância única da classe.
     */
    public static synchronized ConfiguracaoCapa getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracaoCapa();
        }
        return instancia;
    }
    
    /**
     * Inicializa as configurações padrão para as categorias principais.
     */
    private void inicializarConfiguracoesPadrao() {
        // Configuração para Bíblias (exemplo com KJA)
        ConfiguracaoEstilo configBiblia = new ConfiguracaoEstilo()
                .comCorLombada(new Color(139, 0, 0))  // Vermelho escuro
                .comCorFonte(Color.WHITE)
                .comTipoExibicao(TipoExibicao.SVG)
                .comCaminhoSVG("/svg/lombada_biblia.svg");
        
        // Adiciona configurações para categorias específicas
        configuracoes.put(7, configBiblia);  // ID da categoria Bíblia
    }
    
    /**
     * Obtém a configuração para uma categoria específica.
     * Se não houver configuração específica, retorna a configuração padrão.
     */
    public ConfiguracaoEstilo getConfiguracao(Categoria categoria) {
        if (categoria == null) {
            return configuracaoPadrao;
        }
        return configuracoes.getOrDefault(categoria.getId(), configuracaoPadrao);
    }
    
    /**
     * Define uma configuração personalizada para uma categoria.
     */
    public void setConfiguracao(Categoria categoria, ConfiguracaoEstilo configuracao) {
        if (categoria != null && configuracao != null) {
            configuracoes.put(categoria.getId(), configuracao);
        }
    }
    
    /**
     * Remove uma configuração personalizada, restaurando o padrão para a categoria.
     */
    public void removerConfiguracao(Categoria categoria) {
        if (categoria != null) {
            configuracoes.remove(categoria.getId());
        }
    }
    
    /**
     * Enum que define os tipos de exibição disponíveis para as lombadas.
     */
    public enum TipoExibicao {
        BARRAS,     // Exibe as barras tradicionais
        SVG,        // Exibe uma imagem SVG personalizada
        TEXTO       // Exibe apenas o texto
    }
    
    /**
     * Classe interna que representa as configurações de estilo para uma categoria.
     */
    public static class ConfiguracaoEstilo implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private Color corLombada = new Color(75, 0, 130);  // Roxo escuro
        private Color corFonte = Color.WHITE;
        private TipoExibicao tipoExibicao = TipoExibicao.BARRAS;
        private String caminhoSVG = "/svg/lombada_padrao.svg";
        private boolean exibirNumeroColecao = true;
        private int tamanhoFonteTitulo = 12;
        private int tamanhoFonteAutor = 10;
        
        // Getters e Setters com estilo fluente
        public Color getCorLombada() { return corLombada; }
        public Color getCorFonte() { return corFonte; }
        public TipoExibicao getTipoExibicao() { return tipoExibicao; }
        public String getCaminhoSVG() { return caminhoSVG; }
        public boolean isExibirNumeroColecao() { return exibirNumeroColecao; }
        public int getTamanhoFonteTitulo() { return tamanhoFonteTitulo; }
        public int getTamanhoFonteAutor() { return tamanhoFonteAutor; }
        
        public ConfiguracaoEstilo comCorLombada(Color corLombada) { 
            this.corLombada = corLombada; 
            return this; 
        }
        
        public ConfiguracaoEstilo comCorFonte(Color corFonte) { 
            this.corFonte = corFonte; 
            return this; 
        }
        
        public ConfiguracaoEstilo comTipoExibicao(TipoExibicao tipoExibicao) { 
            this.tipoExibicao = tipoExibicao; 
            return this; 
        }
        
        public ConfiguracaoEstilo comCaminhoSVG(String caminhoSVG) { 
            this.caminhoSVG = caminhoSVG; 
            return this; 
        }
        
        public ConfiguracaoEstilo comExibirNumeroColecao(boolean exibir) { 
            this.exibirNumeroColecao = exibir; 
            return this; 
        }
        
        public ConfiguracaoEstilo comTamanhoFonteTitulo(int tamanho) { 
            this.tamanhoFonteTitulo = tamanho; 
            return this; 
        }
        
        public ConfiguracaoEstilo comTamanhoFonteAutor(int tamanho) { 
            this.tamanhoFonteAutor = tamanho; 
            return this; 
        }
    }
}
