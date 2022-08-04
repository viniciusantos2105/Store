package br.com.store.exceptions;

public class PasswordInvalidException extends RuntimeException{
    public PasswordInvalidException() {
        super("Senha inválida");
    }
}
