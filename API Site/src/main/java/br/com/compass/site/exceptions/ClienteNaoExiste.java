package br.com.compass.site.exceptions;

public class ClienteNaoExiste extends RuntimeException{
    public ClienteNaoExiste(String message) {
        super(message);
    }
}
