package br.com.compass.site.builders;

import br.com.compass.site.dto.item.request.RequestItemDto;

import java.time.LocalDateTime;

public class RequestItemDtoBuilder {

    private RequestItemDto requestItemDto;

    private RequestItemDtoBuilder() {
    }

    public static RequestItemDtoBuilder one() {
        RequestItemDtoBuilder builder = new RequestItemDtoBuilder();
        builder.requestItemDto = new RequestItemDto();
        LocalDateTime dataAgora = LocalDateTime.now();

        builder.requestItemDto.setNome("teste nome");
        builder.requestItemDto.setDescricao("teste descricao");
        builder.requestItemDto.setEstoque(50);
        builder.requestItemDto.setValor(100.0);
        builder.requestItemDto.setDataValidade(dataAgora);

        return builder;
    }

    public RequestItemDtoBuilder withNome(String nome) {
        this.requestItemDto.setNome(nome);
        return this;
    }

    public RequestItemDtoBuilder withDescricao(String descricao) {
        this.requestItemDto.setDescricao(descricao);
        return this;
    }

    public RequestItemDtoBuilder withEstoque(int estoque) {
        this.requestItemDto.setEstoque(estoque);
        return this;
    }

    public RequestItemDtoBuilder withValor(Double valor) {
        this.requestItemDto.setValor(valor);
        return this;
    }

    public RequestItemDtoBuilder withDataValidade(LocalDateTime dataValidade) {
        this.requestItemDto.setDataValidade(dataValidade);
        return this;
    }

    public RequestItemDto now() {
        return this.requestItemDto;
    }
}
