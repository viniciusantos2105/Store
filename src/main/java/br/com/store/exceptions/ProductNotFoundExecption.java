package br.com.store.exceptions;

public class ProductNotFoundExecption extends RuntimeException{
    public ProductNotFoundExecption() {
        super("Produto não encontrado");
    }
}
