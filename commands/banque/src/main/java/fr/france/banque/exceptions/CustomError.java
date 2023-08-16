package fr.france.banque.exceptions;

public class CustomError extends RuntimeException {
    public CustomError(String message) {
        super(message);
    }
}
