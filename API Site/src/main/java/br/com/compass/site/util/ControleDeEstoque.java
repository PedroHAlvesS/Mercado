package br.com.compass.site.util;

import br.com.compass.site.dto.checkout.request.RequestCheckoutItensDto;
import br.com.compass.site.entities.ItemEntity;
import br.com.compass.site.exceptions.ItemSemEstoque;
import org.springframework.stereotype.Component;

@Component
public class ControleDeEstoque {
    private void confereEstoque(RequestCheckoutItensDto requestDto, ItemEntity itemEntity) {
        if (requestDto.getQtd() > itemEntity.getEstoque()) {
            throw new ItemSemEstoque("Item de SkuId: " + requestDto.getSkuId() + " " +
                    "Nao tem essa quantidade no estoque, temos apenas: " + itemEntity.getEstoque());
        }
    }

    public ItemEntity atualizaEstoque(RequestCheckoutItensDto requestDto, ItemEntity itemEntity) {
        confereEstoque(requestDto, itemEntity);
        itemEntity.setEstoque(itemEntity.getEstoque() - requestDto.getQtd());
        return itemEntity;
    }

}
