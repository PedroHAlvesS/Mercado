package br.com.compass.site.builders;

import br.com.compass.site.entities.CartoesEntity;
import br.com.compass.site.entities.ClienteEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClienteEntityBuilder {

    private ClienteEntity clienteEntity;

    public ClienteEntityBuilder() {
    }

    public static ClienteEntityBuilder one() {
        ClienteEntityBuilder builder = new ClienteEntityBuilder();
        builder.clienteEntity = new ClienteEntity();
        List<CartoesEntity> cartoesEntityList = new ArrayList<>();

        builder.clienteEntity.setNome("teste");
        builder.clienteEntity.setCpf("111.222.333-04");
        builder.clienteEntity.setDataCriacao(LocalDateTime.now());
        builder.clienteEntity.setCartoes(cartoesEntityList);

        return builder;
    }

    public ClienteEntityBuilder withNome(String nome) {
        this.clienteEntity.setNome(nome);
        return this;
    }

    public ClienteEntityBuilder withCpf(String cpf) {
        this.clienteEntity.setCpf(cpf);
        return this;
    }

    public ClienteEntityBuilder withDataCriacao(LocalDateTime dataCriacao) {
        this.clienteEntity.setDataCriacao(dataCriacao);
        return this;
    }

    public ClienteEntity now() {
        return this.clienteEntity;
    }

}
