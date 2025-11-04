package br.com.warrick.biblioteca.util;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * Componente para alternar entre idiomas
 * 
 * Projeto: Biblioteca
 * 
 * @author Warrick
 * @since 04/11/2025
 */
public class LanguageSwitcher extends JPanel {
    
    private JComboBox<LanguageOption> languageComboBox;
    private I18nManager i18nManager;
    
    public LanguageSwitcher() {
        i18nManager = I18nManager.getInstance();
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // Criar opções de idioma
        LanguageOption[] languages = {
            new LanguageOption("Português (Brasil)", I18nManager.LOCALE_PT_BR),
            new LanguageOption("English (USA)", I18nManager.LOCALE_EN_US)
        };
        
        languageComboBox = new JComboBox<>(languages);
        
        // Selecionar idioma atual
        Locale currentLocale = i18nManager.getCurrentLocale();
        for (int i = 0; i < languages.length; i++) {
            if (languages[i].getLocale().equals(currentLocale)) {
                languageComboBox.setSelectedIndex(i);
                break;
            }
        }
        
        // Adicionar listener para mudança de idioma
        languageComboBox.addActionListener(e -> {
            LanguageOption selected = (LanguageOption) languageComboBox.getSelectedItem();
            if (selected != null) {
                i18nManager.setLocale(selected.getLocale());
                // Notificar que o idioma foi alterado
                onLanguageChanged();
            }
        });
        
        JLabel label = new JLabel(I18nManager.msg("settings.language") + ":");
        add(label);
        add(languageComboBox);
    }
    
    /**
     * Método chamado quando o idioma é alterado
     * Pode ser sobrescrito para atualizar a interface
     */
    protected void onLanguageChanged() {
        // Mostrar mensagem de que é necessário reiniciar
        JOptionPane.showMessageDialog(
            this,
            "Reinicie a aplicação para aplicar as mudanças de idioma.\nRestart the application to apply language changes.",
            "Info",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Classe interna para representar uma opção de idioma
     */
    private static class LanguageOption {
        private String displayName;
        private Locale locale;
        
        public LanguageOption(String displayName, Locale locale) {
            this.displayName = displayName;
            this.locale = locale;
        }
        
        public Locale getLocale() {
            return locale;
        }
        
        @Override
        public String toString() {
            return displayName;
        }
    }
}
