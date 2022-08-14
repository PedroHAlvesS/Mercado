package br.com.compass.site.exceptions;

public class ItemNaoExiste extends RuntimeException{
    public ItemNaoExiste(String message) {
        super(message);
    }
}
