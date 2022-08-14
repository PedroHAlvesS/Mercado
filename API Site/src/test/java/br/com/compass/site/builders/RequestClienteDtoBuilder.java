package br.com.compass.site.builders;

import br.com.compass.site.dto.cliente.request.RequestClienteDto;

public class RequestClienteDtoBuilder {

    private RequestClienteDto requestClienteDto;

    public RequestClienteDtoBuilder() {
    }

    public static RequestClienteDtoBuilder one() {
        RequestClienteDtoBuilder builder = new RequestClienteDtoBuilder();
        builder.requestClienteDto = new RequestClienteDto();

        builder.requestClienteDto.setNome("teste");
        builder.requestClienteDto.setCpf("111.222.333-44");

        return builder;
    }

    public RequestClienteDtoBuilder withNome(String nome) {
        this.requestClienteDto.setNome(nome);
        return this;
    }

    public RequestClienteDtoBuilder withCpf(String cpf) {
        this.requestClienteDto.setCpf(cpf);
        return this;
    }

    public RequestClienteDto now() {
        return this.requestClienteDto;
    }

}
