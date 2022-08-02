package br.com.compass.Sprint05.util;

import br.com.compass.Sprint05.exceptions.ValorCartaoMaior;
import br.com.compass.Sprint05.exceptions.ValorCartaoMenor;
import org.springframework.stereotype.Component;

@Component
public class ValidaValorCartao {

    public void validaValorDoCartao(Double total, Double valorCartao) {
        if (valorCartao > total) {
            throw new ValorCartaoMaior("Valor do cartao é maior que o total da soma dos itens, o total foi: " + total);
        } else if (valorCartao < total) {
            throw new ValorCartaoMenor("Valor do cartao é menor que o total da soma dos itens, o total foi: " + total);
        }
    }

}
