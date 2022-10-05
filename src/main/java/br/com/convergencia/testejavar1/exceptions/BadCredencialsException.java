package br.com.convergencia.testejavar1.exceptions;

public class BadCredencialsException extends RuntimeException {

    public BadCredencialsException(String message) {
        super(message);
    }
}
