package br.com.compass.site.builders;

import br.com.compass.site.dto.cartoes.request.RequestCartoesDto;

import java.time.LocalDate;

public class RequestCartoesDtoBuilder {
    private RequestCartoesDto requestCartoesDto;

    private RequestCartoesDtoBuilder() {
    }

    public static RequestCartoesDtoBuilder one() {
        RequestCartoesDtoBuilder builder = new RequestCartoesDtoBuilder();
        builder.requestCartoesDto = new RequestCartoesDto();
        // A verificação leva em conta o ano atual do sistema, por isso essa função pega o ano atual
        int anoAtual = LocalDate.now().getYear();
        String anoAtualString = String.valueOf(anoAtual);

        //Cartao valido
        builder.requestCartoesDto.setNumero("4916018168172481");
        builder.requestCartoesDto.setCodigo("123");
        builder.requestCartoesDto.setMesValidade("12");
        builder.requestCartoesDto.setAnoValidade(anoAtualString);
        builder.requestCartoesDto.setMarca("VISA");
        return builder;
    }

    public RequestCartoesDtoBuilder withCodigo(String codigo) {
        this.requestCartoesDto.setCodigo(codigo);
        return this;
    }

    public RequestCartoesDtoBuilder withMarca(String marca) {
        this.requestCartoesDto.setMarca(marca);
        return this;
    }

    public RequestCartoesDtoBuilder withMesValidade(String mesValidade) {
        this.requestCartoesDto.setMesValidade(mesValidade);
        return this;
    }

    public RequestCartoesDtoBuilder withAnoValidade(String anoValidade) {
        this.requestCartoesDto.setAnoValidade(anoValidade);
        return this;
    }

    public RequestCartoesDtoBuilder withNumero(String numero) {
        this.requestCartoesDto.setNumero(numero);
        return this;
    }

    public RequestCartoesDto now() {
        return this.requestCartoesDto;
    }

}
