package br.com.warrick.biblioteca.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * Gerenciador de Internacionalização (i18n)
 * Responsável por carregar e fornecer mensagens traduzidas
 * 
 * Projeto: Biblioteca
 * 
 * @author Warrick
 * @since 04/11/2025
 */
public class I18nManager {
    
    private static I18nManager instance;
    private ResourceBundle bundle;
    private Locale currentLocale;
    private static final String BUNDLE_BASE_NAME = "messages";
    private static final String PREF_LANGUAGE_KEY = "app.language";
    private static final String PREF_COUNTRY_KEY = "app.country";
    
    // Lista de listeners para mudanças de idioma
    private final List<LanguageChangeListener> listeners = new ArrayList<>();
    
    // Locales suportados
    public static final Locale LOCALE_PT_BR = new Locale("pt", "BR");
    public static final Locale LOCALE_EN_US = new Locale("en", "US");
    
    /**
     * Construtor privado (Singleton)
     */
    private I18nManager() {
        loadSavedLocale();
    }
    
    /**
     * Obtém a instância única do gerenciador
     * @return Instância do I18nManager
     */
    public static I18nManager getInstance() {
        if (instance == null) {
            synchronized (I18nManager.class) {
                if (instance == null) {
                    instance = new I18nManager();
                }
            }
        }
        return instance;
    }
    
    /**
     * Carrega o locale salvo nas preferências ou usa o padrão do sistema
     */
    private void loadSavedLocale() {
        try {
            Preferences prefs = Preferences.userNodeForPackage(I18nManager.class);
            String language = prefs.get(PREF_LANGUAGE_KEY, null);
            String country = prefs.get(PREF_COUNTRY_KEY, null);
            
            if (language != null && country != null) {
                currentLocale = new Locale(language, country);
            } else {
                // Usar locale do sistema ou PT-BR como padrão
                Locale systemLocale = Locale.getDefault();
                if (systemLocale.getLanguage().equals("pt")) {
                    currentLocale = LOCALE_PT_BR;
                } else if (systemLocale.getLanguage().equals("en")) {
                    currentLocale = LOCALE_EN_US;
                } else {
                    currentLocale = LOCALE_PT_BR; // Fallback para PT-BR
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar locale salvo: " + e.getMessage());
            currentLocale = LOCALE_PT_BR;
        }
        
        loadBundle();
    }
    
    /**
     * Carrega o bundle de recursos para o locale atual
     */
    private void loadBundle() {
        try {
            bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, currentLocale);
            System.out.println("Bundle carregado: " + bundle.getLocale());
        } catch (Exception e) {
            System.err.println("Erro ao carregar bundle: " + e.getMessage());
            // Tentar carregar bundle padrão
            bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME);
        }
    }
    
    /**
     * Obtém uma mensagem traduzida pela chave
     * @param key Chave da mensagem
     * @return Mensagem traduzida
     */
    public String getMessage(String key) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            System.err.println("Chave não encontrada: " + key);
            return "!" + key + "!";
        }
    }
    
    /**
     * Obtém uma mensagem traduzida com parâmetros
     * @param key Chave da mensagem
     * @param params Parâmetros para substituir na mensagem
     * @return Mensagem traduzida com parâmetros
     */
    public String getMessage(String key, Object... params) {
        try {
            String message = bundle.getString(key);
            return String.format(message, params);
        } catch (Exception e) {
            System.err.println("Chave não encontrada: " + key);
            return "!" + key + "!";
        }
    }
    
    /**
     * Altera o locale da aplicação
     * @param locale Novo locale
     */
    public void setLocale(Locale locale) {
        Locale oldLocale = this.currentLocale;
        this.currentLocale = locale;
        loadBundle();
        saveLocale();
        
        // Notificar listeners
        notifyLanguageChanged(oldLocale, locale);
    }
    
    /**
     * Salva o locale atual nas preferências
     */
    private void saveLocale() {
        try {
            Preferences prefs = Preferences.userNodeForPackage(I18nManager.class);
            prefs.put(PREF_LANGUAGE_KEY, currentLocale.getLanguage());
            prefs.put(PREF_COUNTRY_KEY, currentLocale.getCountry());
            prefs.flush();
        } catch (Exception e) {
            System.err.println("Erro ao salvar locale: " + e.getMessage());
        }
    }
    
    /**
     * Obtém o locale atual
     * @return Locale atual
     */
    public Locale getCurrentLocale() {
        return currentLocale;
    }
    
    /**
     * Verifica se o locale atual é PT-BR
     * @return true se for PT-BR
     */
    public boolean isPortuguese() {
        return currentLocale.equals(LOCALE_PT_BR);
    }
    
    /**
     * Verifica se o locale atual é EN-US
     * @return true se for EN-US
     */
    public boolean isEnglish() {
        return currentLocale.equals(LOCALE_EN_US);
    }
    
    /**
     * Alterna entre PT-BR e EN-US
     */
    public void toggleLanguage() {
        if (isPortuguese()) {
            setLocale(LOCALE_EN_US);
        } else {
            setLocale(LOCALE_PT_BR);
        }
    }
    
    /**
     * Método estático para acesso rápido às mensagens
     * @param key Chave da mensagem
     * @return Mensagem traduzida
     */
    public static String msg(String key) {
        return getInstance().getMessage(key);
    }
    
    /**
     * Método estático para acesso rápido às mensagens com parâmetros
     * @param key Chave da mensagem
     * @param params Parâmetros
     * @return Mensagem traduzida
     */
    public static String msg(String key, Object... params) {
        return getInstance().getMessage(key, params);
    }
    
    /**
     * Adiciona um listener para mudanças de idioma
     * @param listener Listener a ser adicionado
     */
    public void addLanguageChangeListener(LanguageChangeListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    /**
     * Remove um listener de mudanças de idioma
     * @param listener Listener a ser removido
     */
    public void removeLanguageChangeListener(LanguageChangeListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Remove todos os listeners
     */
    public void clearLanguageChangeListeners() {
        listeners.clear();
    }
    
    /**
     * Notifica todos os listeners sobre mudança de idioma
     * @param oldLocale Locale anterior
     * @param newLocale Novo locale
     */
    private void notifyLanguageChanged(Locale oldLocale, Locale newLocale) {
        for (LanguageChangeListener listener : listeners) {
            try {
                listener.onLanguageChanged(oldLocale, newLocale);
            } catch (Exception e) {
                System.err.println("Erro ao notificar listener: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
