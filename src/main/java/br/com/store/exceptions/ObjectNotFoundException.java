package br.com.store.exceptions;

public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException() {
        super("Produto não encontrado");
    }
}
