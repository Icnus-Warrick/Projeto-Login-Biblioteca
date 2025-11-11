package br.com.warrick.biblioteca.util;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

/**
 * Componente para troca de idioma da aplicação
 * Permite selecionar entre os idiomas suportados
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 04/11/2025
 */
public class LanguageSwitcher extends JPanel {
    
    /* ========================================== COMPONENTES ========================================== */
    private JComboBox<LanguageOption> languageComboBox;
    private final I18nManager i18nManager;
    
    /* ========================================== CONSTRUTOR ========================================== */
    
    /**
     * Cria um novo seletor de idioma
     * Inicializa os componentes e carrega os idiomas disponíveis
     */
    public LanguageSwitcher() {
        i18nManager = I18nManager.getInstance();
        initComponents();
    }
    
    /* ========================================== INICIALIZAÇÃO ========================================== */
    
    /**
     * Inicializa os componentes da interface
     * Configura o layout e carrega as opções de idioma
     */
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
    
    /* ========================================== EVENTOS ========================================== */
    
    /**
     * Chamado quando o idioma é alterado
     * Exibe uma mensagem informando sobre a necessidade de reiniciar
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
    
    /* ============================================== CLASSE INTERNA ============================================== */
    
    /**
     * Classe interna que representa uma opção de idioma na caixa de seleção.
     */
    private static class LanguageOption {
        
        /* ============================================== ATRIBUTOS ============================================== */
        
        /** Nome de exibição do idioma */
        private final String displayName;
        
        /** Locale correspondente ao idioma */
        private final Locale locale;
        
        /* ============================================== CONSTRUTOR ============================================== */
        
        /**
         * Cria uma nova opção de idioma.
         * 
         * @param displayName Nome a ser exibido na interface
         * @param locale Locale correspondente ao idioma
         */
        public LanguageOption(String displayName, Locale locale) {
            this.displayName = displayName;
            this.locale = locale;
        }
        
        /* ============================================== MÉTODOS PÚBLICOS ============================================== */
        
        /**
         * Obtém o locale associado a esta opção de idioma.
         * 
         * @return O objeto Locale correspondente
         */
        
        public Locale getLocale() {
            return locale;
        }
        
        /* ============================================== MÉTODOS SOBRESCRITOS ============================================== */
        
        /**
         * Retorna a representação em string desta opção de idioma.
         * 
         * @return O nome de exibição do idioma
         */
        
        @Override
        public String toString() {
            return displayName;
        }
    }
}
