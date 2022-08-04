package br.com.compass.pagamento.exceptions;

public class NaoAutenticadoException extends RuntimeException{

    NaoAutenticadoException() {
    }

    public NaoAutenticadoException(String message) {
        super(message);
    }

}
