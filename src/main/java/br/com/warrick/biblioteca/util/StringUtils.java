package br.com.warrick.biblioteca.util;

/**
 * Utilitários para manipulação de strings
 * Fornece métodos auxiliares para operações comuns
 *
 * Projeto: Biblioteca
 *
 * @author Warrick
 * @since 28/10/2025
 */
public class StringUtils {

    /* ========================================== MÉTODOS ESTÁTICOS ========================================== */
    
    /**
     * Verifica se uma string é nula ou vazia
     * 
     * @param str String a ser verificada
     * @return true se for nula, vazia ou só espaços
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Capitaliza a primeira letra de uma string e converte o restante para minúsculas.
     * 
     * @param str String a ser capitalizada
     * @return String com a primeira letra maiúscula
     * 
     * @see String#toUpperCase()
     * @see String#toLowerCase()
     * @see String#substring(int)
     */
    public static String capitalize(String str) {
        if (isNullOrEmpty(str)) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
