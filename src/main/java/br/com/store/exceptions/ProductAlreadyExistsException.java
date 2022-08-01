package br.com.store.exceptions;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException() {
        super("Já existe um produto com esse nome");
    }
}
