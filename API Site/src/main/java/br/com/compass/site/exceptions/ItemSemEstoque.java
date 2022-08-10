package br.com.compass.site.exceptions;

public class ItemSemEstoque extends RuntimeException{
    public ItemSemEstoque(String message) {
        super(message);
    }
}
