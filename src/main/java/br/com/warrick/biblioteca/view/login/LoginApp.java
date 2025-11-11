package br.com.warrick.biblioteca.view.login;

import java.awt.Color;
import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 * Classe principal da aplicação de login
 * Gerencia a navegação entre os painéis de login e registro
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 02/11/2025
 */
public class LoginApp extends javax.swing.JFrame {

    /* ============================================== COMPONENTES PRINCIPAIS ============================================= */
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private LoginFrente loginFrente;
    private LoginTras loginTras;
    private LoginRecupera loginRecupera;

    /* ============================================== CONSTRUTOR PADRÃO ============================================= */
    public LoginApp() {
        setUndecorated(true);
        initComponents();
        setSize(1035, 720); // Define o tamanho da janela
        setLocationRelativeTo(null); // Centraliza a janela
        setupTransparentWindow();
        setupPanels();
    }

    /* ========================================= CONFIGURAÇÃO DA JANELA TRANSPARENTE =================================== */
    private void setupTransparentWindow() {
        // Configurar para que apenas o fundo seja transparente
        setBackground(new Color(0, 0, 0, 0));
        getRootPane().setOpaque(false);
        getContentPane().setBackground(new Color(0, 0, 0, 0));
    }

    /* ========================================= CONFIGURAÇÃO DOS PAINÉIS ========================================== */
    private void setupPanels() {
        // Criar CardLayout para alternar entre painéis
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setOpaque(false);
        mainPanel.setBackground(new Color(0, 0, 0, 0));
        mainPanel.setPreferredSize(new java.awt.Dimension(1035, 720));

        // Criar instâncias dos painéis
        loginFrente = new LoginFrente(this);
        loginTras = new LoginTras(this);
        loginRecupera = new LoginRecupera();

        // Configurar os painéis para serem opacos e com tamanho fixo
        loginFrente.setOpaque(false);
        loginTras.setOpaque(false);
        loginRecupera.setOpaque(false);
        
        // Criar painéis de contêiner para centralização
        JPanel containerFrente = createCenteredPanel(loginFrente);
        JPanel containerTras = createCenteredPanel(loginTras);
        JPanel containerRecupera = createCenteredPanel(loginRecupera);

        // Adicionar painéis ao CardLayout
        mainPanel.add(containerFrente, "LOGIN");
        mainPanel.add(containerTras, "REGISTRO");
        mainPanel.add(containerRecupera, "RECUPERA");

        // Adicionar o painel principal à janela
        add(mainPanel, java.awt.BorderLayout.CENTER);

        // Mostrar painel de login inicialmente
        cardLayout.show(mainPanel, "LOGIN");
    }
    
    /**
     * Cria um painel centralizado que contém o painel especificado
     * @param panel Painel a ser centralizado
     * @return JPanel com o painel centralizado
     */
    private JPanel createCenteredPanel(JPanel panel) {
        JPanel container = new JPanel(new java.awt.GridBagLayout());
        container.setOpaque(false);
        container.setBackground(new java.awt.Color(0, 0, 0, 0));
        
        // Configurar restrições para centralizar o painel
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = java.awt.GridBagConstraints.CENTER;
        
        // Adicionar o painel ao container com as restrições
        container.add(panel, gbc);
        
        return container;
    }

    /* ========================================= MÉTODOS DE NAVEGAÇÃO =============================================== */
    public void mostrarLogin() {
        cardLayout.show(mainPanel, "LOGIN");
    }

    public void mostrarRegistro() {
        cardLayout.show(mainPanel, "REGISTRO");
    }
    
    public void mostrarRecuperacao(){
        cardLayout.show(mainPanel, "RECUPERA");
    }

    /* ========================================= CÓDIGO GERADO PELO NETBEANS ========================================= */
    /**
     * Este método é chamado dentro do construtor para inicializar o formulário.
     * AVISO: NÃO modifique este código. O conteúdo deste método é sempre regenerado pelo Editor de Formulários.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Código Gerado">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(528, 704));
        setSize(new java.awt.Dimension(528, 704));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    /* ========================================= VARIÁVEIS DO NETBEANS ========================================= */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
