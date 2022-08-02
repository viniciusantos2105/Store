package br.com.store.exceptions;

public class AddressNotFoundExecption extends RuntimeException{
    public AddressNotFoundExecption() {
        super("Endereço não encontrado");
    }
}
