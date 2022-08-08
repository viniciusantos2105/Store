package br.com.store.exceptions;

public class DeniedAuthorization extends RuntimeException{
    public DeniedAuthorization() {
        super("Acesso negado");
    }
}
