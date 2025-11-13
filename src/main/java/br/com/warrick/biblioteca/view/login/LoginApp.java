package br.com.warrick.biblioteca.view.login;

import br.com.warrick.biblioteca.view.BibliotecaApp;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.*;


/**
 * Classe principal da aplicação de login Gerencia a navegação entre os painéis de login e registro
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 02/11/2025
 */
public class LoginApp extends javax.swing.JFrame {

    /* ============================================ COMPONENTES PRINCIPAIS ========================================== */
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel centeredContainer; // Container para centralização
    private LoginFrente loginFrente;
    private LoginTras loginTras;
    private LoginRecupera loginRecupera;
    private LoginPortas loginPortas;

    /* ============================================== CONSTRUTOR PADRÃO ============================================= */
    public LoginApp() {
        // Inicializa os componentes da interface
        initComponents();

        // Configurações iniciais da janela
        setUndecorated(true);
        setResizable(false);

        // Configura o tamanho e a posição da janela
        setSize(1920, 1080);

        // Centraliza a janela na tela
        setLocationRelativeTo(null);

        // Configura o fundo transparente
        setupTransparentWindow();

        // Inicializar os painéis
        initializePanels();

        // Configurar o painel principal e o card layout
        setupMainPanel();

        // Garante que a janela esteja visível
        setVisible(true);

        // IMPORTANTE: Mostrar o painel de login ao iniciar
        SwingUtilities.invokeLater(() -> {
            cardLayout.show(mainPanel, "LOGIN");
            loginFrente.resetForm();
        });
    }

    /**
     * Exibe o painel de login SEM animação
     */
    public void showLogin() {
        SwingUtilities.invokeLater(() -> {
            cardLayout.show(mainPanel, "LOGIN");
            loginFrente.resetForm();
        });
    }

    /**
     * Exibe o painel de registro SEM animação
     */
    public void showRegister() {
        SwingUtilities.invokeLater(() -> {
            cardLayout.show(mainPanel, "REGISTRO");
            loginTras.resetForm();
        });
    }

    /**
     * Exibe o painel de recuperação de senha SEM animação
     */
    public void showRecovery() {
        SwingUtilities.invokeLater(() -> {
            cardLayout.show(mainPanel, "RECUPERAR");
            loginRecupera.resetForm();
        });
    }

    /**
     * Inicia a sequência de login bem-sucedido COM EFEITO DE ENTRADA:
     * 1. Mostra o painel de portas fechadas
     * 2. Aguarda X segundos
     * 3. Abre as portas com animação
     * 4. Automaticamente inicia o zoom quando as portas abrirem
     * 5. Abre a BibliotecaApp e fecha a janela de login
     */
    public void iniciarSequenciaLogin() {
        System.out.println("=== INICIANDO SEQUÊNCIA DE LOGIN ===");

        final int DELAY_ANTES_ABRIR = 1000;

        // Mostrar painel de portas
        cardLayout.show(mainPanel, "LOADING");
        loginPortas.fecharPortas();

        // Configurar callback para quando TODO o processo terminar (após o zoom)
        Runnable abrirApp = () -> {
            System.out.println("Abrindo BibliotecaApp...");

            // Fechar a janela de login
            java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(loginPortas);
            if (window != null) {
                window.dispose();
            }

            // Iniciar a aplicação principal
            SwingUtilities.invokeLater(() -> {
                try {
                    br.com.warrick.biblioteca.view.BibliotecaApp app
                            = new br.com.warrick.biblioteca.view.BibliotecaApp();
                    app.setVisible(true);
                } catch (Exception ex) {
                    System.err.println("Erro ao abrir BibliotecaApp: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
        };

        Timer delayAntes = new Timer(DELAY_ANTES_ABRIR, e -> {
            System.out.println("Iniciando abertura das portas...");

            // Iniciar apenas a abertura das portas
            // O zoom será iniciado automaticamente quando as portas abrirem completamente
            loginPortas.iniciarAnimacao(abrirApp);
        });
        delayAntes.setRepeats(false);
        delayAntes.start();
    }

    /**
     * Exibe o painel de carregamento com animação
     * MÉTODO MANTIDO POR COMPATIBILIDADE, MAS NÃO USADO PARA NAVEGAÇÃO ENTRE TELAS
     *
     * @param onComplete Callback a ser executado quando a animação terminar
     */
    public void showLoading(Runnable onComplete) {
        cardLayout.show(mainPanel, "LOADING");

        // Inicia a animação após um pequeno delay para garantir que o painel esteja visível
        javax.swing.Timer timer = new javax.swing.Timer(100, e -> {
            // Fecha as portas e chama o callback quando terminar
            loginPortas.fecharPortas();
            loginPortas.iniciarAnimacao(() -> {
                // Executa a ação de carregamento
                if (onComplete != null) {
                    onComplete.run();
                }
            });
        });
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Inicializa todos os painéis da aplicação
     */
    private void initializePanels() {
        // Criar instâncias dos painéis
        loginFrente = new LoginFrente();
        loginFrente.setParentApp(this);

        loginTras = new LoginTras();
        loginTras.setParentApp(this);

        loginRecupera = new LoginRecupera();
        loginRecupera.setParentApp(this);

        // Inicializar o painel de portas sem iniciar a animação
        loginPortas = new LoginPortas();
        loginPortas.setParentApp(this);
    }

    /**
     * Configura o painel principal e o card layout
     */
    private void setupMainPanel() {
        // Criar CardLayout para gerenciar os painéis
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setOpaque(false);

        // Adicionar painéis ao CardLayout com wrappers centralizados
        mainPanel.add(createCenteredPanel(loginFrente), "LOGIN");
        mainPanel.add(createCenteredPanel(loginTras), "REGISTRO");
        mainPanel.add(createCenteredPanel(loginRecupera), "RECUPERAR");
        mainPanel.add(createCenteredPanel(loginPortas), "LOADING");

        // Adicionar o mainPanel diretamente ao JFrame
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Forçar atualização do layout
        revalidate();
        repaint();
    }

    /**
     * Fecha as portas e executa uma ação quando a animação terminar
     * MÉTODO DEPRECADO - Use iniciarSequenciaLogin() para login bem-sucedido
     *
     * @param acao Ação a ser executada após o fechamento das portas
     * @deprecated Use iniciarSequenciaLogin() para login ou navegação direta para outras telas
     */
    @Deprecated
    public void fecharPortasERodar(Runnable acao) {
        if (loginPortas.isAnimando()) {
            // Se já estiver animando, aguardar um pouco e tentar novamente
            Timer timer = new Timer(100, e -> fecharPortasERodar(acao));
            timer.setRepeats(false);
            timer.start();
            return;
        }

        // Mostrar o painel de portas
        cardLayout.show(mainPanel, "LOADING");

        // Fechar as portas e executar a ação quando terminar
        loginPortas.fecharPortas();
        loginPortas.iniciarAnimacao(acao);
    }

    /**
     * Cria um painel centralizado que contém o painel especificado
     *
     * @param panel Painel a ser centralizado
     * @return JPanel com o painel centralizado
     */
    private JPanel createCenteredPanel(JPanel panel) {
        JPanel container = new JPanel(new GridBagLayout());
        container.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        container.add(panel, gbc);

        return container;
    }

    /**
     * @deprecated Use showRegister() em vez disso
     */
    @Deprecated
    public void mostrarRegistro() {
        showRegister();
    }

    /**
     * @deprecated Use showLogin() em vez disso
     */
    @Deprecated
    public void mostrarLogin() {
        showLogin();
    }

    /**
     * Exibe o painel de recuperação de senha com animação
     */
    public void mostrarRecuperacao() {
        showRecovery();
    }

    /**
     * Retorna a instância do painel de portas
     *
     * @return Instância do LoginPortas
     */
    public LoginPortas getLoginPortas() {
        return loginPortas;
    }

    /**
     * Configura a janela para ter fundo transparente
     */
    private void setupTransparentWindow() {
        // Configura o fundo como totalmente transparente
        setBackground(new Color(0, 0, 0, 0));

        // Configura o gerenciador de layout do content pane
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0, 0, 0, 0));
    }

    /* ========================================= CÓDIGO GERADO PELO NETBEANS ========================================= */
    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        // Configuração do layout principal
        getContentPane().setLayout(new java.awt.BorderLayout());

        // Configuração do ícone da janela
        try {
            // Substitua pelo caminho do seu ícone

        } catch (Exception e) {
            System.err.println("Erro ao carregar o ícone da aplicação: " + e.getMessage());
        }
    }

    /* ========================================= VARIÁVEIS DO NETBEANS ========================================= */
// Variables declaration - do not modify//GEN-BEGIN:variables
// End of variables declaration//GEN-END:variables
}