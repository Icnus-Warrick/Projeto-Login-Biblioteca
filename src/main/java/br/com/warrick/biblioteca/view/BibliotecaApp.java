package br.com.warrick.biblioteca.view;

import br.com.warrick.biblioteca.persistence.model.Livro;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.components.FlatButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaApp extends javax.swing.JFrame {

    /* ================================================= CONSTRUTOR ================================================= */
    /**
     * Construtor padrão que inicializa os componentes do painel
     */
    public BibliotecaApp() {
        initComponents();
        configuraPainel();
        
        // Configurações adicionais
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Biblioteca App");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        
        // Inicializa a lista de livros
        livros = new ArrayList<>();
        painelLivros = new JPanel();
        inicializarLivros();
        atualizarEstante();
    }
    
    private void configuraPainel(){
        Painel1.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Painel.background;"
                + "foreground:$Painel.foreground;"
                + "arc:20;");
        Painel2.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Painel.background;"
                + "foreground:$Painel.foreground;"
                + "arc:20;");
    }
    
    /**
     * Inicializa a lista de livros a partir da pasta Ebook
     */
    private void inicializarLivros() {
        livros.clear();
        
        // Adiciona livros de exemplo (substitua pelo carregamento real dos PDFs)
        Livro livro1 = new Livro();
        livro1.setTitulo("Exemplo 1");
        livro1.setAutor("Autor 1");
        livro1.setGenero("Geral");
        livro1.setAnoPublicacao(2023);
        
        Livro livro2 = new Livro();
        livro2.setTitulo("Exemplo 2");
        livro2.setAutor("Autor 2");
        livro2.setGenero("Geral");
        livro2.setAnoPublicacao(2023);
        
        livros.add(livro1);
        livros.add(livro2);
    }
    
    /**
     * Atualiza a exibição dos livros na estante
     */
    private void atualizarEstante() {
        if (painelLivros == null) return;
        
        painelLivros.removeAll();
        painelLivros.setLayout(new GridLayout(0, 4, 20, 20));
        
        for (Livro livro : livros) {
            painelLivros.add(criarPainelLivro(livro));
        }
        
        painelLivros.revalidate();
        painelLivros.repaint();
    }
    
    /**
     * Cria um painel para exibir um livro
     */
    private JPanel criarPainelLivro(Livro livro) {
        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout(5, 5));
        painel.setBackground(UIManager.getColor("Panel.background"));
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor"), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        // Efeito hover
        painel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Color hoverColor = UIManager.getColor("Component.hoverBackground");
                if (hoverColor == null) {
                    // Se não houver cor de hover definida, usa um tom mais claro da cor de fundo
                    Color bg = UIManager.getColor("Panel.background");
                    hoverColor = bg != null ? bg.brighter() : new Color(0xF0F0F0);
                }
                painel.setBackground(hoverColor);
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                painel.setBackground(UIManager.getColor("Panel.background"));
                setCursor(Cursor.getDefaultCursor());
            }
        });
        
        // Título
        JLabel lblTitulo = new JLabel("<html><div style='width:150px;text-align:center;font-weight:bold;'>" + 
            livro.getTitulo() + "</div></html>");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(14f));
        
        // Label para a capa do livro (sem ícone)
        JLabel lblCapa = new JLabel("📚");
        lblCapa.setHorizontalAlignment(SwingConstants.CENTER);
        lblCapa.setFont(new Font(lblCapa.getFont().getName(), Font.PLAIN, 48));
        lblCapa.setPreferredSize(new Dimension(100, 140));
        
        // Autor e ano
        JLabel lblInfo = new JLabel("<html><div style='width:150px;text-align:center;font-size:11px;color:#666;'><i>" + 
            livro.getAutor() + "<br>(" + livro.getAnoPublicacao() + ")</i></div></html>");
        lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Botão para abrir o livro
        FlatButton btnLer = new FlatButton();
        btnLer.setText("Ler Livro");
        btnLer.setButtonType(FlatButton.ButtonType.roundRect);
        btnLer.addActionListener(e -> abrirLivro(livro));
        
        // Layout do painel
        JPanel contentPanel = new JPanel(new BorderLayout(5, 10));
        contentPanel.setOpaque(false);
        contentPanel.add(lblTitulo, BorderLayout.NORTH);
        contentPanel.add(lblCapa, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel(new BorderLayout(5, 5));
        panelInferior.setOpaque(false);
        panelInferior.add(lblInfo, BorderLayout.CENTER);
        panelInferior.add(btnLer, BorderLayout.SOUTH);
        
        contentPanel.add(panelInferior, BorderLayout.SOUTH);
        painel.add(contentPanel, BorderLayout.CENTER);
        
        return painel;
    }

    /**
     * Método para abrir um livro
     */
    private void abrirLivro(Livro livro) {
        try {
            if (livro.getCaminhoArquivo() != null && !livro.getCaminhoArquivo().isEmpty()) {
                // Converte a string do caminho para um objeto File
                File arquivo = new File(livro.getCaminhoArquivo());
                
                // Verifica se o arquivo existe
                if (arquivo.exists()) {
                    // Tenta abrir com o leitor padrão do sistema
                    Desktop.getDesktop().open(arquivo);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Arquivo não encontrado: " + livro.getCaminhoArquivo(), 
                        "Arquivo não encontrado", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Nenhum arquivo associado a este livro.", 
                    "Arquivo não encontrado", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao abrir o arquivo: " + ex.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    /* ========================================= CÓDIGO GERADO PELO NETBEANS ======================================== */
    /**
     * Inicializa os componentes da interface gráfica AVISO: NÃO modifique este código. O conteúdo deste método é sempre
     * regenerado pelo Editor de Formulários.
     *
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Painel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Painel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Painel1.setBackground(new java.awt.Color(244, 244, 244));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("BIBLIOTECA");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("BEM VINDO {NOME} A BIBLIOTECA.");

        javax.swing.GroupLayout Painel1Layout = new javax.swing.GroupLayout(Painel1);
        Painel1.setLayout(Painel1Layout);
        Painel1Layout.setHorizontalGroup(
            Painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Painel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 809, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        Painel1Layout.setVerticalGroup(
            Painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Painel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Painel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Painel2.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout Painel2Layout = new javax.swing.GroupLayout(Painel2);
        Painel2.setLayout(Painel2Layout);
        Painel2Layout.setHorizontalGroup(
            Painel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
        );
        Painel2Layout.setVerticalGroup(
            Painel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(Painel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(Painel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(Painel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(Painel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /* ============================================ VARIÁVEIS DO NETBEANS =========================================== */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Painel1;
    private javax.swing.JPanel Painel2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
    
    // Variáveis adicionais
    private List<Livro> livros;
    private JPanel painelLivros;
    
    /**
     * Filtra os livros com base no gênero selecionado
     */
    private void filtrarLivros(String genero) {
        // Implementação básica de filtro
        // Você pode melhorar isso conforme necessário
        atualizarEstante();
    }
    
    /**
     * Filtra os livros com base no texto de pesquisa
     */
    private void filtrarPesquisa() {
        // Implementação básica de pesquisa
        // Você pode melhorar isso conforme necessário
        atualizarEstante();
    }
}
