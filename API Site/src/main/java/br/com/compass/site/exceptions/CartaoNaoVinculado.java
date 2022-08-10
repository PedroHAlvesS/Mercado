package br.com.compass.site.exceptions;

public class CartaoNaoVinculado extends RuntimeException{
    public CartaoNaoVinculado(String message) {
        super(message);
    }
}
