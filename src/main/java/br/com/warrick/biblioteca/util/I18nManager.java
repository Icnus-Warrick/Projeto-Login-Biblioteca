package br.com.warrick.biblioteca.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * Gerenciador de internacionalização (i18n) da aplicação
 * Implementação em Singleton para acesso global
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 04/11/2025
 */
public class I18nManager {
    
    /* ========================================== INSTÂNCIA SINGLETON ========================================== */
    private static I18nManager instance;
    /* ========================================== RECURSOS DE IDIOMA ========================================== */
    private ResourceBundle bundle;
    private Locale currentLocale;
    private static final String BUNDLE_BASE_NAME = "messages";
    
    /* ========================================== PREFERÊNCIAS ========================================== */
    private static final String PREF_LANGUAGE_KEY = "app.language";
    private static final String PREF_COUNTRY_KEY = "app.country";
    
    /* ========================================== LISTENERS ========================================== */
    private final List<LanguageChangeListener> listeners = new ArrayList<>();
    
    /* ========================================== LOCALES SUPORTADOS ========================================== */
    
    /** Locale para Português do Brasil */
    public static final Locale LOCALE_PT_BR = new Locale("pt", "BR");
    
    /** Locale para Inglês Americano */
    public static final Locale LOCALE_EN_US = new Locale("en", "US");
    
    /* ============================================== CONSTRUTOR ============================================== */
    
    /**
     * Construtor privado para o padrão Singleton
     * Inicializa o gerenciador de internacionalização
     */
    private I18nManager() {
        loadSavedLocale();
        loadBundle();
    }
    
    /* ============================================== MÉTODOS PÚBLICOS ============================================== */
    
    /**
     * Obtém a instância única do gerenciador de internacionalização.
     * Implementa o padrão Singleton com verificação thread-safe.
     * 
     * @return A instância única de I18nManager
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
    
    /* ============================================== MÉTODOS PRIVADOS ============================================== */
    
    /**
     * Carrega o locale salvo nas preferências do usuário ou utiliza o padrão do sistema.
     * Se não encontrar um locale salvo, tenta detectar o idioma do sistema operacional.
     * 
     * @see Preferences
     * @see Locale#getDefault()
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
     * Carrega o ResourceBundle correspondente ao locale atual.
     * Se ocorrer algum erro ao carregar o bundle, tenta carregar o bundle padrão.
     * 
     * @see ResourceBundle
     * @see #currentLocale
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
     * Obtém uma mensagem traduzida com base na chave fornecida.
     * Se a chave não for encontrada, retorna a própria chave entre exclamações.
     * 
     * @param key A chave da mensagem no arquivo de recursos
     * @return A mensagem traduzida ou a chave entre '!' se não encontrada
     * @see ResourceBundle#getString(String)
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
     * Obtém uma mensagem traduzida com parâmetros formatados.
     * Os parâmetros são substituídos na mensagem usando String.format().
     * 
     * @param key A chave da mensagem no arquivo de recursos
     * @param params Parâmetros a serem inseridos na mensagem
     * @return A mensagem formatada com os parâmetros ou a chave entre '!' se não encontrada
     * @see String#format(String, Object...)
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
     * Altera o locale atual da aplicação e notifica todos os listeners registrados.
     * 
     * @param locale O novo locale a ser definido
     * @see #notifyLanguageChanged(Locale, Locale)
     * @see #loadBundle()
     * @see #saveLocale()
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
     * Salva as configurações de idioma atuais nas preferências do usuário.
     * As preferências são persistidas entre execuções da aplicação.
     * 
     * @see Preferences
     * @see #currentLocale
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
     * Retorna o locale atualmente configurado na aplicação.
     * 
     * @return O objeto Locale atual
     * @see Locale
     */
    public Locale getCurrentLocale() {
        return currentLocale;
    }
    
    /**
     * Verifica se o idioma atual é Português do Brasil.
     * 
     * @return true se o locale atual for PT-BR, false caso contrário
     * @see #LOCALE_PT_BR
     */
    public boolean isPortuguese() {
        return currentLocale.equals(LOCALE_PT_BR);
    }
    
    /**
     * Verifica se o idioma atual é Inglês Americano.
     * 
     * @return true se o locale atual for EN-US, false caso contrário
     * @see #LOCALE_EN_US
     */
    public boolean isEnglish() {
        return currentLocale.equals(LOCALE_EN_US);
    }
    
    /**
     * Alterna o idioma entre Português do Brasil e Inglês Americano.
     * Se o idioma atual for PT-BR, muda para EN-US, e vice-versa.
     * 
     * @see #setLocale(Locale)
     * @see #isPortuguese()
     */
    public void toggleLanguage() {
        if (isPortuguese()) {
            setLocale(LOCALE_EN_US);
        } else {
            setLocale(LOCALE_PT_BR);
        }
    }
    
    /**
     * Método de conveniência estático para obter mensagens sem precisar da instância.
     * 
     * @param key A chave da mensagem no arquivo de recursos
     * @return A mensagem traduzida
     * @see #getMessage(String)
     */
    public static String msg(String key) {
        return getInstance().getMessage(key);
    }
    
    /**
     * Método de conveniência estático para obter mensagens formatadas sem precisar da instância.
     * 
     * @param key A chave da mensagem no arquivo de recursos
     * @param params Parâmetros a serem inseridos na mensagem
     * @return A mensagem formatada com os parâmetros
     * @see #getMessage(String, Object...)
     */
    public static String msg(String key, Object... params) {
        return getInstance().getMessage(key, params);
    }
    
    /**
     * Registra um listener para ser notificado quando o idioma for alterado.
     * O mesmo listener não será adicionado mais de uma vez.
     * 
     * @param listener O listener a ser registrado
     * @see LanguageChangeListener
     * @see #removeLanguageChangeListener(LanguageChangeListener)
     */
    public void addLanguageChangeListener(LanguageChangeListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
    
    /**
     * Remove um listener previamente registrado para mudanças de idioma.
     * 
     * @param listener O listener a ser removido
     * @see #addLanguageChangeListener(LanguageChangeListener)
     * @see #clearLanguageChangeListeners()
     */
    public void removeLanguageChangeListener(LanguageChangeListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * Remove todos os listeners registrados para mudanças de idioma.
     * 
     * @see #addLanguageChangeListener(LanguageChangeListener)
     * @see #removeLanguageChangeListener(LanguageChangeListener)
     */
    public void clearLanguageChangeListeners() {
        listeners.clear();
    }
    
    /**
     * Notifica todos os listeners registrados sobre a mudança de idioma.
     * Este método é chamado internamente sempre que o locale é alterado.
     * 
     * @param oldLocale O locale antes da mudança
     * @param newLocale O novo locale após a mudança
     * @see LanguageChangeListener#onLanguageChanged(Locale, Locale)
     * @see #setLocale(Locale)
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
