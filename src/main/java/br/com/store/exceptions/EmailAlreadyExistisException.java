package br.com.store.exceptions;

public class EmailAlreadyExistisException extends RuntimeException{
    public EmailAlreadyExistisException() {
        super("Email já existe");
    }
}
