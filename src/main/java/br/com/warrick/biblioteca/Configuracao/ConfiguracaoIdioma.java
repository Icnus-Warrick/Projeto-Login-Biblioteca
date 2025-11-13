package br.com.warrick.biblioteca.Configuracao;

import br.com.warrick.biblioteca.util.I18nManager;
import br.com.warrick.biblioteca.util.LanguageChangeListener;
import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * Painel de Configurações de Idioma da Aplicação
 * 
 * Projeto: Biblioteca
 * 
 * @author Warrick
 * @since 04/11/2025
 */
public class ConfiguracaoIdioma extends JPanel {
    
    private JLabel titleLabel;
    private JLabel languageLabel;
    private JComboBox<LanguageOption> languageComboBox;
    private JButton applyButton;
    private JButton cancelButton;
    private I18nManager i18nManager;
    
    public ConfiguracaoIdioma() {
        i18nManager = I18nManager.getInstance();
        initComponents();
        setupListeners();
        updateTexts();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Painel de configurações
        JPanel settingsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Label de idioma
        languageLabel = new JLabel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        settingsPanel.add(languageLabel, gbc);
        
        // ComboBox de idioma
        LanguageOption[] languages = {
            new LanguageOption("Português (Brasil)", I18nManager.LOCALE_PT_BR, "🇧🇷"),
            new LanguageOption("English (USA)", I18nManager.LOCALE_EN_US, "🇺🇸")
        };
        
        languageComboBox = new JComboBox<>(languages);
        languageComboBox.setPreferredSize(new Dimension(250, 30));
        
        // Selecionar idioma atual
        Locale currentLocale = i18nManager.getCurrentLocale();
        for (int i = 0; i < languages.length; i++) {
            if (languages[i].getLocale().equals(currentLocale)) {
                languageComboBox.setSelectedIndex(i);
                break;
            }
        }
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        settingsPanel.add(languageComboBox, gbc);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        applyButton = new JButton();
        applyButton.setPreferredSize(new Dimension(100, 35));
        
        cancelButton = new JButton();
        cancelButton.setPreferredSize(new Dimension(100, 35));
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(applyButton);
        
        // Adicionar componentes ao painel principal
        add(titleLabel, BorderLayout.NORTH);
        add(settingsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupListeners() {
        // Listener do botão aplicar
        applyButton.addActionListener(e -> applySettings());
        
        // Listener do botão cancelar
        cancelButton.addActionListener(e -> {
            // Restaurar seleção para o idioma atual
            Locale currentLocale = i18nManager.getCurrentLocale();
            for (int i = 0; i < languageComboBox.getItemCount(); i++) {
                if (languageComboBox.getItemAt(i).getLocale().equals(currentLocale)) {
                    languageComboBox.setSelectedIndex(i);
                    break;
                }
            }
        });
        
        // Listener para mudanças de idioma
        i18nManager.addLanguageChangeListener(new LanguageChangeListener() {
            @Override
            public void onLanguageChanged(Locale oldLocale, Locale newLocale) {
                updateTexts();
            }
        });
    }
    
    private void applySettings() {
        LanguageOption selected = (LanguageOption) languageComboBox.getSelectedItem();
        if (selected != null) {
            Locale selectedLocale = selected.getLocale();
            Locale currentLocale = i18nManager.getCurrentLocale();
            
            if (!selectedLocale.equals(currentLocale)) {
                // Alterar idioma
                i18nManager.setLocale(selectedLocale);
                
                // Mostrar mensagem de confirmação
                JOptionPane.showMessageDialog(
                    this,
                    I18nManager.msg("settings.language") + ": " + selected.getDisplayName() + "\n" +
                    (i18nManager.isPortuguese() ? 
                        "Configurações aplicadas com sucesso!" : 
                        "Settings applied successfully!"),
                    I18nManager.msg("success.title"),
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }
    
    /**
     * Atualiza todos os textos da interface
     */
    private void updateTexts() {
        titleLabel.setText(I18nManager.msg("settings.title"));
        languageLabel.setText(I18nManager.msg("settings.language") + ":");
        applyButton.setText(I18nManager.msg("button.ok"));
        cancelButton.setText(I18nManager.msg("button.cancel"));
        
        revalidate();
        repaint();
    }
    
    /**
     * Classe interna para representar uma opção de idioma
     */
    private static class LanguageOption {
        private String displayName;
        private Locale locale;
        private String flag;
        
        public LanguageOption(String displayName, Locale locale, String flag) {
            this.displayName = displayName;
            this.locale = locale;
            this.flag = flag;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public Locale getLocale() {
            return locale;
        }
        
        @Override
        public String toString() {
            return flag + " " + displayName;
        }
    }
    
    /**
     * Método main para testar o painel
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            JFrame frame = new JFrame("Configuração de Idioma");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ConfiguracaoIdioma());
            frame.setSize(500, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
