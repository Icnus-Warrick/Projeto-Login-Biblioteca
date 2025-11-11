package br.com.warrick.biblioteca.Livro;

import br.com.warrick.biblioteca.persistence.model.Categoria;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

/**
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 02/11/2025
 */
public class Lombada extends javax.swing.JPanel {

    // InformaÃ§Ãµes bÃ¡sicas do livro
    private String titulo;
    private String autor;
    private String isbn;
    private String colecao;
    private String genero;
    private String idioma;
    private String formato;
    private Integer anoPublicacao;
    private Integer numeroPaginas;    
    private Integer numeroColecao;
    
    // Categoria e configuraÃ§Ã£o do livro
    private Categoria categoria;
    private br.com.warrick.biblioteca.Configuracao.ConfiguracaoCapa.ConfiguracaoEstilo configuracao;
    private boolean isColecao;  // Indica se pertence a uma coleÃ§Ã£o
    private Image imagemLombada; // Imagem SVG da lombada

    public Lombada(String titulo, String autor, String colecao, Categoria categoria) {
        initComponents();
        this.titulo = titulo;
        this.autor = autor;
        this.colecao = colecao;
        this.categoria = categoria;
        this.isColecao = (colecao != null && !colecao.isEmpty());
        
        // ObtÃ©m a configuraÃ§Ã£o para a categoria
        this.configuracao = br.com.warrick.biblioteca.Configuracao.ConfiguracaoCapa.getInstancia().getConfiguracao(categoria);
        
        // Carrega a imagem SVG se necessÃ¡rio
        if (configuracao.getTipoExibicao() == br.com.warrick.biblioteca.Configuracao.ConfiguracaoCapa.TipoExibicao.SVG) {
            carregarImagemSVG();
        }
        
        setPreferredSize(new Dimension(20, 100)); // fina como lombada
        setBackground(configuracao.getCorLombada());
        
        // Atualiza os componentes visuais
        atualizarCores();
    }
    
    /**
     * Carrega a imagem SVG da lombada, se necessÃ¡rio.
     */
    private void carregarImagemSVG() {
        if (configuracao.getTipoExibicao() == br.com.warrick.biblioteca.Configuracao.ConfiguracaoCapa.TipoExibicao.SVG && 
            configuracao.getCaminhoSVG() != null) {
            
            try {
                // Tenta carregar a imagem do arquivo SVG
                imagemLombada = new ImageIcon(getClass().getResource(configuracao.getCaminhoSVG())).getImage();
            } catch (Exception e) {
                System.err.println("Erro ao carregar imagem SVG: " + configuracao.getCaminhoSVG());
                e.printStackTrace();
            }
        }
    }
    
    private void atualizarCores() {
        // Configura a exibiÃ§Ã£o com base no tipo de exibiÃ§Ã£o selecionado
        switch (configuracao.getTipoExibicao()) {
            case BARRAS:
                // Exibe as barras tradicionais
                Color corBarras = configuracao.getCorLombada().darker();
                BarSup1.setBackground(corBarras);
                BarSup2.setBackground(corBarras);
                BarSup3.setBackground(corBarras);
                BarSup4.setBackground(corBarras);
                BarInf1.setBackground(corBarras);
                BarInf2.setBackground(corBarras);
                BarInf3.setBackground(corBarras);
                BarInf4.setBackground(corBarras);
                
                // Torna as barras visÃ­veis
                BarSup1.setVisible(true);
                BarSup2.setVisible(true);
                BarSup3.setVisible(true);
                BarSup4.setVisible(true);
                BarInf1.setVisible(true);
                BarInf2.setVisible(true);
                BarInf3.setVisible(true);
                BarInf4.setVisible(true);
                break;
                
            case SVG:
                // Esconde as barras quando usando imagem SVG
                BarSup1.setVisible(false);
                BarSup2.setVisible(false);
                BarSup3.setVisible(false);
                BarSup4.setVisible(false);
                BarInf1.setVisible(false);
                BarInf2.setVisible(false);
                BarInf3.setVisible(false);
                BarInf4.setVisible(false);
                break;
                
            case TEXTO:
                // Esconde as barras quando exibindo apenas texto
                BarSup1.setVisible(false);
                BarSup2.setVisible(false);
                BarSup3.setVisible(false);
                BarSup4.setVisible(false);
                BarInf1.setVisible(false);
                BarInf2.setVisible(false);
                BarInf3.setVisible(false);
                BarInf4.setVisible(false);
                break;
        }
        
        // Atualiza cor da fonte
        Color corFonte = configuracao.getCorFonte();
        lblNome.setForeground(corFonte);
        lblAutor.setForeground(corFonte);
        
        // Atualiza tamanho da fonte
        Font fonteAtual = lblNome.getFont();
        lblNome.setFont(new Font(fonteAtual.getName(), fonteAtual.getStyle(), 
                               configuracao.getTamanhoFonteTitulo()));
        
        fonteAtual = lblAutor.getFont();
        lblAutor.setFont(new Font(fonteAtual.getName(), fonteAtual.getStyle(), 
                                 configuracao.getTamanhoFonteAutor()));
        
        // Atualiza cor de fundo do painel
        setBackground(configuracao.getCorLombada());
        setBorder(BorderFactory.createLineBorder(configuracao.getCorLombada().darker(), 1));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Desenha a imagem de fundo se estiver usando SVG
        if (configuracao.getTipoExibicao() == br.com.warrick.biblioteca.Configuracao.ConfiguracaoCapa.TipoExibicao.SVG && imagemLombada != null) {
            g2d.drawImage(imagemLombada, 0, 0, getWidth(), getHeight(), this);
        }
        
        // Configura a cor do texto
        g2d.setColor(configuracao.getCorFonte());
        
        // Rotaciona o contexto grÃ¡fico para desenhar o texto na vertical
        g2d.rotate(Math.PI / 2);
        
        // Desenha o tÃ­tulo
        g2d.drawString(titulo, 5, -5);
        
        // Se for parte de uma coleÃ§Ã£o e estiver habilitado, mostra o nÃºmero da coleÃ§Ã£o
        if (isColecao && numeroColecao != null && configuracao.isExibirNumeroColecao()) {
            g2d.drawString("#" + numeroColecao, 25, -5);
        }
    }
    
    // Getters e Setters para os campos adicionais
//    public void setEditora(String editora) { this.editora = editora; }
//    public String getEditora() { return editora; }
    
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getIsbn() { return isbn; }
    
    public void setGenero(String genero) { this.genero = genero; }
    public String getGenero() { return genero; }
    
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public String getIdioma() { return idioma; }
    
    public void setFormato(String formato) { this.formato = formato; }
    public String getFormato() { return formato; }
    
    public void setAnoPublicacao(Integer anoPublicacao) { 
        this.anoPublicacao = anoPublicacao; 
    }
    public Integer getAnoPublicacao() { 
        return anoPublicacao; 
    }
    
    public void setNumeroPaginas(Integer numeroPaginas) { 
        this.numeroPaginas = numeroPaginas; 
    }
    public Integer getNumeroPaginas() { 
        return numeroPaginas; 
    }
    
    public void setNumeroColecao(Integer numeroColecao) { 
        this.numeroColecao = numeroColecao;
        this.isColecao = (numeroColecao != null);
        repaint();
    }
    public Integer getNumeroColecao() { return numeroColecao; }
    
    public void setCorLombada(Color corLombada) { 
        this.corLombada = corLombada;
        setBackground(corLombada);
        repaint();
    }
    public Color getCorLombada() { return corLombada; }
    
    public void setCorBarras(Color corBarras) { 
        this.corBarras = corBarras;
        atualizarCores();
    }
    public Color getCorBarras() { return corBarras; }
    
    public void setCorFonte(Color corFonte) { 
        this.corFonte = corFonte;
        atualizarCores();
    }
    public Color getCorFonte() { return corFonte; }
    
    public Color corBarras;
    public Color corFonte;
    public Color corLombada;

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BarSup1 = new javax.swing.JPanel();
        BarSup2 = new javax.swing.JPanel();
        BarSup3 = new javax.swing.JPanel();
        BarSup4 = new javax.swing.JPanel();
        lblIcon = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        lblAutor = new javax.swing.JLabel();
        lblColecao = new javax.swing.JLabel();
        BarInf4 = new javax.swing.JPanel();
        BarInf3 = new javax.swing.JPanel();
        BarInf2 = new javax.swing.JPanel();
        BarInf1 = new javax.swing.JPanel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BarSup1.setBackground(new java.awt.Color(229, 191, 76));

        javax.swing.GroupLayout BarSup1Layout = new javax.swing.GroupLayout(BarSup1);
        BarSup1.setLayout(BarSup1Layout);
        BarSup1Layout.setHorizontalGroup(
            BarSup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        BarSup1Layout.setVerticalGroup(
            BarSup1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        add(BarSup1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 15, 70, 5));

        BarSup2.setBackground(new java.awt.Color(229, 191, 76));

        javax.swing.GroupLayout BarSup2Layout = new javax.swing.GroupLayout(BarSup2);
        BarSup2.setLayout(BarSup2Layout);
        BarSup2Layout.setHorizontalGroup(
            BarSup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        BarSup2Layout.setVerticalGroup(
            BarSup2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        add(BarSup2, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 22, 70, 3));

        BarSup3.setBackground(new java.awt.Color(229, 191, 76));

        javax.swing.GroupLayout BarSup3Layout = new javax.swing.GroupLayout(BarSup3);
        BarSup3.setLayout(BarSup3Layout);
        BarSup3Layout.setHorizontalGroup(
            BarSup3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        BarSup3Layout.setVerticalGroup(
            BarSup3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        add(BarSup3, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 80, 70, 3));

        BarSup4.setBackground(new java.awt.Color(229, 191, 76));

        javax.swing.GroupLayout BarSup4Layout = new javax.swing.GroupLayout(BarSup4);
        BarSup4.setLayout(BarSup4Layout);
        BarSup4Layout.setHorizontalGroup(
            BarSup4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        BarSup4Layout.setVerticalGroup(
            BarSup4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        add(BarSup4, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 90, 70, 3));

        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setText("ICONE");
        add(lblIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 30, 70, 45));

        lblNome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNome.setText("NOME");
        add(lblNome, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 100, 70, 380));

        lblAutor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAutor.setText("AUTOR");
        add(lblAutor, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 490, 70, 30));

        lblColecao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblColecao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblColecao.setText("01");
        add(lblColecao, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 545, 70, 25));

        BarInf4.setBackground(new java.awt.Color(229, 191, 76));

        javax.swing.GroupLayout BarInf4Layout = new javax.swing.GroupLayout(BarInf4);
        BarInf4.setLayout(BarInf4Layout);
        BarInf4Layout.setHorizontalGroup(
            BarInf4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        BarInf4Layout.setVerticalGroup(
            BarInf4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        add(BarInf4, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 525, 70, 3));

        BarInf3.setBackground(new java.awt.Color(229, 191, 76));

        javax.swing.GroupLayout BarInf3Layout = new javax.swing.GroupLayout(BarInf3);
        BarInf3.setLayout(BarInf3Layout);
        BarInf3Layout.setHorizontalGroup(
            BarInf3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        BarInf3Layout.setVerticalGroup(
            BarInf3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        add(BarInf3, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 535, 70, 3));

        BarInf2.setBackground(new java.awt.Color(229, 191, 76));

        javax.swing.GroupLayout BarInf2Layout = new javax.swing.GroupLayout(BarInf2);
        BarInf2.setLayout(BarInf2Layout);
        BarInf2Layout.setHorizontalGroup(
            BarInf2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        BarInf2Layout.setVerticalGroup(
            BarInf2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        add(BarInf2, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 575, 70, 3));

        BarInf1.setBackground(new java.awt.Color(229, 191, 76));

        javax.swing.GroupLayout BarInf1Layout = new javax.swing.GroupLayout(BarInf1);
        BarInf1.setLayout(BarInf1Layout);
        BarInf1Layout.setHorizontalGroup(
            BarInf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );
        BarInf1Layout.setVerticalGroup(
            BarInf1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 5, Short.MAX_VALUE)
        );

        add(BarInf1, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 580, 70, 5));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BarInf1;
    private javax.swing.JPanel BarInf2;
    private javax.swing.JPanel BarInf3;
    private javax.swing.JPanel BarInf4;
    private javax.swing.JPanel BarSup1;
    private javax.swing.JPanel BarSup2;
    private javax.swing.JPanel BarSup3;
    private javax.swing.JPanel BarSup4;
    private javax.swing.JLabel lblAutor;
    private javax.swing.JLabel lblColecao;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblNome;
    // End of variables declaration//GEN-END:variables
}

