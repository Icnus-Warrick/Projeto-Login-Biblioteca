package br.com.warrick.biblioteca.util;

import java.util.Locale;

/**
 * Interface para notificação de mudança de idioma
 * Permite que componentes sejam notificados quando o idioma é alterado
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 04/11/2025
 */
@FunctionalInterface
public interface LanguageChangeListener {
    
    /* ============================================== MÉTODOS ============================================== */
    
    /* ========================================== MÉTODO PRINCIPAL ========================================== */
    
    /**
     * Chamado quando o idioma é alterado
     * 
     * @param oldLocale Idioma anterior
     * @param newLocale Novo idioma selecionado
     */
    void onLanguageChanged(Locale oldLocale, Locale newLocale);
    
    /* ============================================== FIM DA INTERFACE ============================================== */
}
