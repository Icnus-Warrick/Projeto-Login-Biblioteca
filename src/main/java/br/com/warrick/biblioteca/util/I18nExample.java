package br.com.warrick.biblioteca.util;

import javax.swing.*;
import java.awt.*;

/**
 * Exemplo de uso do sistema de internacionalização
 * Execute esta classe para ver o i18n em ação
 * 
 * Projeto: Biblioteca
 * 
 * @author Warrick
 * @since 04/11/2025
 */
public class I18nExample extends JFrame {
    
    private JLabel titleLabel;
    private JLabel messageLabel;
    private JButton loginButton;
    private JButton toggleButton;
    private LanguageSwitcher languageSwitcher;
    
    public I18nExample() {
        initComponents();
        updateTexts();
    }
    
    private void initComponents() {
        setTitle("i18n Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Painel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Labels
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        messageLabel = new JLabel();
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Botões
        loginButton = new JButton();
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        toggleButton = new JButton("Toggle Language / Alternar Idioma");
        toggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        toggleButton.addActionListener(e -> {
            I18nManager.getInstance().toggleLanguage();
            updateTexts();
        });
        
        // Seletor de idioma
        languageSwitcher = new LanguageSwitcher() {
            @Override
            protected void onLanguageChanged() {
                updateTexts();
                JOptionPane.showMessageDialog(
                    I18nExample.this,
                    I18nManager.msg("settings.language") + " " + 
                    I18nManager.getInstance().getCurrentLocale().getDisplayName(),
                    I18nManager.msg("settings.title"),
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        };
        languageSwitcher.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Adicionar componentes
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(messageLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(loginButton);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(toggleButton);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(languageSwitcher);
        
        // Painel de informações
        JPanel infoPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Informações / Information"));
        
        JLabel currentLocaleLabel = new JLabel("Current Locale: " + 
            I18nManager.getInstance().getCurrentLocale());
        JLabel isPtLabel = new JLabel("Is Portuguese: " + 
            I18nManager.getInstance().isPortuguese());
        JLabel isEnLabel = new JLabel("Is English: " + 
            I18nManager.getInstance().isEnglish());
        
        infoPanel.add(currentLocaleLabel);
        infoPanel.add(isPtLabel);
        infoPanel.add(isEnLabel);
        
        add(mainPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    /**
     * Atualiza todos os textos da interface com as mensagens traduzidas
     */
    private void updateTexts() {
        titleLabel.setText(I18nManager.msg("app.title"));
        messageLabel.setText(I18nManager.msg("animation.opening.doors"));
        loginButton.setText(I18nManager.msg("login.button"));
        
        // Atualizar título da janela
        setTitle(I18nManager.msg("app.title") + " - i18n Example");
        
        // Forçar repaint
        revalidate();
        repaint();
    }
    
    /**
     * Método main para executar o exemplo
     */
    public static void main(String[] args) {
        // Configurar Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Criar e exibir a janela
        SwingUtilities.invokeLater(() -> {
            I18nExample example = new I18nExample();
            example.setVisible(true);
            
            // Mostrar mensagens no console
            System.out.println("=== i18n Example ===");
            System.out.println("App Title: " + I18nManager.msg("app.title"));
            System.out.println("Login Button: " + I18nManager.msg("login.button"));
            System.out.println("Error Message: " + I18nManager.msg("error.database"));
            System.out.println("Success Message: " + I18nManager.msg("success.login"));
            System.out.println("Current Locale: " + I18nManager.getInstance().getCurrentLocale());
            System.out.println("===================");
        });
    }
}
