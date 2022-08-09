package br.com.store.exceptions;

public class ClientNotFoundExecption extends RuntimeException{
    public ClientNotFoundExecption() {
        super("Usuario não encontrado");
    }
}
