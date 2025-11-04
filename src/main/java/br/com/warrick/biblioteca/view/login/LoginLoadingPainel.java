package br.com.warrick.biblioteca.view.login;

import br.com.warrick.biblioteca.util.I18nManager;
import javax.swing.*;
import java.awt.*;

/**
 * Janela principal para exibirção da animação de login Versão Refatorada e Simplicada
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 02/11/2025
 */
public class LoginLoadingPainel extends javax.swing.JFrame {

    // Componentes Principais
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private LoginLoadingPortas painelPortas;

    // Construtor padrão
    public LoginLoadingPainel() {
        // Primeiro configurar a janela antes de inicializar componentes
        configurarJanela();
        // Depois inicializar os componentes
        initComponents();
        // Configurar o restante
        inicializarComponentes();
        configurarPaineis();
    }

    // Configuração da janela principal
    private void configurarJanela() {
        // Primeiro, definir a janela como não decorada
        setUndecorated(true);
        // Depois configurar o resto
        setTitle(I18nManager.msg("app.loading.title"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Configurar para que o fundo seja completamente transparente
        setBackground(new Color(0, 0, 0, 0));
    }

    // Iniciando componentes
    public void inicializarComponentes() {
        // Criar CardLayout para gerenciar múltiplas telas
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Configurar fundo do painel principal
        mainPanel.setOpaque(false);

    }

    // Configurar Paineis
    private void configurarPaineis() {
        // Criar painel de portas
        painelPortas = new LoginLoadingPortas(this) {
            @Override
            protected void onAnimacaoConcluida() {
                Timer delayTimer = new Timer(500, e -> {

                });
                delayTimer.setRepeats(false);
                // DelayTimer.start();
            }
        };
        // Criar painel wrapper para centralização automatica
        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(new Color(240, 240, 240));
        wrapperPanel.setOpaque(false);
        wrapperPanel.add(painelPortas);

        // Adicionar ao sistema de navegação CarLayout
        mainPanel.add(wrapperPanel, "PORTAS");

        // Configurar layout da janela
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Mostar painel de portas inicialmente
        cardLayout.show(mainPanel, "PORTAS");
    }

    // metodo publico para navegação
    public void mostrarPortas() {
        cardLayout.show(mainPanel, "PORTAS");
    }

    public void mostrarLogin() {
        mostrarPortas();
    }

    public void adicionarPainel(JPanel painel, String nome) {
        // Criar wrapper para centralização
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(mainPanel.getBackground());
        wrapper.add(painel);
        mainPanel.add(wrapper, nome);
    }

    public void mostarPainel(String nome) {
        cardLayout.show(mainPanel, nome);
    }

    public LoginLoadingPortas getPainelPortas() {
        return painelPortas;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    // Metodos auxiliares para integração
    public void reiniciarAnimacao() {
        painelPortas.fecharPortas();
        
        // Aguardar um momento antes de reabrir
        Timer timer = new Timer(300, e -> painelPortas.abrirPortas());
        timer.setRepeats(false);
        timer.start();
    }
    
    //Verifica se a animação está eme andamento
    public boolean isAnimando(){
        return painelPortas.isAnimando();
    }

    // metodo estatico para executar a aplicação
    public static void executar() {
        // Configurar Look and Feel do sistema na thread da interface gráfica
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Erro ao configurar Look and Feel: " + e.getMessage());
        }
        
        // Criar e exibir a janela na thread da interface gráfica
        SwingUtilities.invokeLater(() -> {
            try {
                LoginLoadingPainel app = new LoginLoadingPainel();
                // Ajustar tamanho da janela ao conteúdo
                app.pack();
                // Centralizar a janela na tela
                app.setLocationRelativeTo(null);
                // Configurar para tela cheia
                app.setExtendedState(JFrame.MAXIMIZED_BOTH);
                // Torna a janela visível por último
                app.setVisible(true);
                System.out.println(I18nManager.msg("app.success"));
            } catch (Exception e) {
                System.err.println("Erro ao exibir aplicação: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
    
    // Limpeza de Recursos
    @Override
    public void dispose(){
        // Libera recusros do painel de portas
        if(painelPortas != null){
            painelPortas.dispose();
        }
        super.dispose();
    }

    /**
     * Inicializa os componentes da interface gráfica. Código gerado pelo NetBeans Form Editor. Não modifique este
     * método manualmente.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 570, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 885, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
