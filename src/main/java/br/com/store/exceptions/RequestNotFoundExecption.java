package br.com.store.exceptions;

public class RequestNotFoundExecption extends RuntimeException{
    public RequestNotFoundExecption() {
        super("Pedido não encontrado");
    }
}
