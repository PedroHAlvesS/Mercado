package br.com.compass.site.util;

import br.com.compass.site.dto.checkout.request.RequestCheckoutItensDto;
import br.com.compass.site.entities.ItemEntity;
import br.com.compass.site.exceptions.ItemSemEstoque;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ControleDeEstoqueTest {

    private ControleDeEstoque controleDeEstoque;

    @BeforeEach
    private void inicializar() {
        this.controleDeEstoque = new ControleDeEstoque();
    }

    @Test
    @DisplayName("Deveria atualizar o estoque com sucesso quando quantidade solicitado for menor que estoque")
    void deveriaAtualizarOEstoqueQuandoQuantidadeSolicitaForMenorQueOEstoque() {
        RequestCheckoutItensDto checkoutItensDto = new RequestCheckoutItensDto();
        checkoutItensDto.setQtd(50);
        checkoutItensDto.setSkuId("abc123");
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setEstoque(80);
        int estoqueEsperado = 80 - 50;


        ItemEntity itemEntityAtualizado = controleDeEstoque.atualizaEstoque(checkoutItensDto, itemEntity);

        Assertions.assertEquals(estoqueEsperado, itemEntityAtualizado.getEstoque());
    }

    @Test
    @DisplayName("Deveria atualizar o estoque com sucesso quando quantidade solicitado for igual que estoque")
    void deveriaAtualizarOEstoqueQuandoQuantidadeSolicitaForIgualQueOEstoque() {
        RequestCheckoutItensDto checkoutItensDto = new RequestCheckoutItensDto();
        checkoutItensDto.setQtd(80);
        checkoutItensDto.setSkuId("abc123");
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setEstoque(80);
        int estoqueEsperado = 0;


        ItemEntity itemEntityAtualizado = controleDeEstoque.atualizaEstoque(checkoutItensDto, itemEntity);

        Assertions.assertEquals(estoqueEsperado, itemEntityAtualizado.getEstoque());
    }

    @Test
    @DisplayName("deveria lancar uma exception ao buscar mais itens do que disponivel no estoque")
    void deveriaLancarUmaExceptionQuandoBuscarMaisItemDoQueDisponivel() {
        RequestCheckoutItensDto checkoutItensDto = new RequestCheckoutItensDto();
        checkoutItensDto.setQtd(100);
        checkoutItensDto.setSkuId("abc123");
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setEstoque(80);

        Assertions.assertThrows(ItemSemEstoque.class, () -> controleDeEstoque.atualizaEstoque(checkoutItensDto, itemEntity));
    }
}