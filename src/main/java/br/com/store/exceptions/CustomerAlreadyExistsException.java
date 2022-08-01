package br.com.store.exceptions;

public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException() {
        super("Username ou CPF já existente");
    }
}
