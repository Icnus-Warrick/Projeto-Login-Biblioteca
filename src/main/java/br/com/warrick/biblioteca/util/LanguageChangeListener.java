package br.com.warrick.biblioteca.util;

import java.util.Locale;

/**
 * Interface para ouvir mudanças de idioma
 * 
 * Projeto: Biblioteca
 * 
 * @author Warrick
 * @since 04/11/2025
 */
@FunctionalInterface
public interface LanguageChangeListener {
    
    /**
     * Chamado quando o idioma é alterado
     * 
     * @param oldLocale Locale anterior
     * @param newLocale Novo locale
     */
    void onLanguageChanged(Locale oldLocale, Locale newLocale);
}
