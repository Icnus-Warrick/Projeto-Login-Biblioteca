package br.com.warrick.biblioteca.persistence.exception;

/**
 * Exceção lançada quando ocorre um erro na camada de persistência.
 *
 * @author Warrick
 * @since 11/11/2025
 */
public class PersistenceException extends RuntimeException {

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }
}
