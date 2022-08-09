package br.com.compass.site.util;

import br.com.compass.site.dto.item.request.RequestItemDto;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.UUID;

@Component
public class GerarSkuId {

    public String gerarSkuId(RequestItemDto requestItemDto) {
        String nomeTresLetras = requestItemDto.getNome().toLowerCase().substring(0, 3);
        return nomeTresLetras +
                String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
    }

}
