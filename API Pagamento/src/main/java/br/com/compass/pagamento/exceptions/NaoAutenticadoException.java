package br.com.compass.pagamento.exceptions;

public class NaoAutenticadoException extends RuntimeException{
    public NaoAutenticadoException(String message) {
        super(message);
    }

}
