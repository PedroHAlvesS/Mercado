package br.com.compass.site.services;

import br.com.compass.site.builders.ClienteEntityBuilder;
import br.com.compass.site.builders.RequestCartoesDtoBuilder;
import br.com.compass.site.constants.EnumMarcaCartao;
import br.com.compass.site.dto.cartoes.request.RequestCartoesDto;
import br.com.compass.site.dto.cartoes.response.ResponseCartoesDto;
import br.com.compass.site.entities.CartoesEntity;
import br.com.compass.site.entities.ClienteEntity;
import br.com.compass.site.exceptions.CartaoNaoVinculado;
import br.com.compass.site.repository.CartoesRepository;
import br.com.compass.site.repository.ClienteRepository;
import br.com.compass.site.util.ValidaCartoes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = CartoesService.class)
class CartoesServiceTest {

    @Autowired
    private CartoesService cartoesService;

    @MockBean
    private ValidaCartoes validaCartoes;

    @MockBean
    private ClienteRepository clienteRepository;

    @MockBean
    private CartoesRepository cartoesRepository;

    @SpyBean
    private ModelMapper modelMapper;


    @Test
    @DisplayName("deveria vincular um cartao com sucesso")
    void deveriaVincularUmCartaoComSucesso() {
        ClienteEntity clienteEntity = ClienteEntityBuilder.one().now();
        RequestCartoesDto cartoesDto = RequestCartoesDtoBuilder.one().now();

        Mockito.when(clienteRepository.findById(clienteEntity.getCpf())).thenReturn(Optional.of(clienteEntity));

        ResponseCartoesDto responseCartoesDto = cartoesService.vinculaCartao(clienteEntity.getCpf(), cartoesDto);

        Mockito.verify(clienteRepository).save(clienteEntity);
        Assertions.assertEquals(cartoesDto.getNumero(), responseCartoesDto.getNumero());
        Assertions.assertEquals(cartoesDto.getCodigo(), responseCartoesDto.getCodigo());
        Assertions.assertEquals(cartoesDto.getMesValidade(), responseCartoesDto.getMesValidade());
        Assertions.assertEquals(cartoesDto.getAnoValidade(), responseCartoesDto.getAnoValidade());
        Assertions.assertEquals(cartoesDto.getMarca(), responseCartoesDto.getMarca());
    }

    @Test
    @DisplayName("deveria lancar exception ao tentar vincular um cartao a um cpf nao existente")
    void deveriaLancarUmaExceptionAoTentarVincularAUmCpfNaoExistente() {
        RequestCartoesDto cartoesDto = RequestCartoesDtoBuilder.one().now();
        Assertions.assertThrows(ResponseStatusException.class, () -> cartoesService.vinculaCartao("123", cartoesDto));
    }

    @Test
    @DisplayName("deveria Mostrar todos os cartoes do cliente")
    void deveriaMostrarTodosOsCartoesDoCliente() {
        RequestCartoesDto cartoesDto = RequestCartoesDtoBuilder.one().now();
        CartoesEntity cartoesEntity1 = criaCartaoEntity(cartoesDto, 1L);
        CartoesEntity cartoesEntity2 = criaCartaoEntity(cartoesDto, 2L);
        List<CartoesEntity> cartoesEntityList = new ArrayList<>();
        cartoesEntityList.add(cartoesEntity1);
        cartoesEntityList.add(cartoesEntity2);

        ClienteEntity clienteEntity = ClienteEntityBuilder.one().now();
        clienteEntity.setCartoes(cartoesEntityList);

        Mockito.when(clienteRepository.findById(clienteEntity.getCpf())).thenReturn(Optional.of(clienteEntity));

        List<ResponseCartoesDto> responseCartoesDtoList = cartoesService.mostraCartoesDoCliente(clienteEntity.getCpf());

        for (int i = 0; i < cartoesEntityList.size(); i++) {
            Assertions.assertEquals(cartoesEntityList.get(i).getId(), responseCartoesDtoList.get(i).getId());
            Assertions.assertEquals(cartoesEntityList.get(i).getNumero(), responseCartoesDtoList.get(i).getNumero());
            Assertions.assertEquals(cartoesEntityList.get(i).getCodigo(), responseCartoesDtoList.get(i).getCodigo());
            Assertions.assertEquals(cartoesEntityList.get(i).getMesValidade(), responseCartoesDtoList.get(i).getMesValidade());
            Assertions.assertEquals(cartoesEntityList.get(i).getAnoValidade(), responseCartoesDtoList.get(i).getAnoValidade());
            Assertions.assertEquals(cartoesEntityList.get(i).getMarca().toString(), responseCartoesDtoList.get(i).getMarca());
        }
        Assertions.assertEquals(cartoesEntityList.size(), responseCartoesDtoList.size());
    }

    @Test
    @DisplayName("deveria lancar uma exception ao mostrar todos os cartoes de um cpf nao existente")
    void deveriaLancarUmaExceptionAoMostrarTodosOsCartoesDeUmCpfNaoExistente() {
        Assertions.assertThrows(ResponseStatusException.class, () -> cartoesService.mostraCartoesDoCliente("123"));
    }

    @Test
    @DisplayName("deveria mostrar o cartao de forma unitaria")
    void deveriaMostrarOCartaoDeFormaUnitaria() {
        RequestCartoesDto cartoesDto = RequestCartoesDtoBuilder.one().now();
        CartoesEntity cartoesEntity = criaCartaoEntity(cartoesDto, 1L);

        Mockito.when(cartoesRepository.findByIdAndCliente_cpf(1L, "123")).thenReturn(Optional.of(cartoesEntity));

        ResponseCartoesDto responseCartoesDto = cartoesService.mostraCartaoDoClienteUnitario("123", 1L);

        Assertions.assertEquals(cartoesEntity.getId(), responseCartoesDto.getId());
        Assertions.assertEquals(cartoesEntity.getCodigo(), responseCartoesDto.getCodigo());
        Assertions.assertEquals(cartoesEntity.getAnoValidade(), responseCartoesDto.getAnoValidade());
        Assertions.assertEquals(cartoesEntity.getMesValidade(), responseCartoesDto.getMesValidade());
        Assertions.assertEquals(cartoesEntity.getNumero(), responseCartoesDto.getNumero());
        Assertions.assertEquals(cartoesEntity.getMarca().toString(), responseCartoesDto.getMarca());
    }

    @Test
    @DisplayName("deveria lancar uma exception ao mostrar o cartao de um id/cpf nao existente")
    void deveriaLancarUmaExceptionQuandoTentarMostrarOCartaoDeIdInexisteOuCpfNaoExistente() {
        Assertions.assertThrows(CartaoNaoVinculado.class, () -> cartoesService.mostraCartaoDoClienteUnitario("123", 1L));
    }

    @Test
    @DisplayName("deveria atualizar todos os campos do cartao exceto o id")
    void atualizaCartao() {
        RequestCartoesDto cartoesDto = RequestCartoesDtoBuilder.one().now();
        CartoesEntity cartoesEntity = criaCartaoEntity(cartoesDto, 1L);

        RequestCartoesDto putCartoesDto = RequestCartoesDtoBuilder.one()
                .withNumero("1111222233334444")
                .withAnoValidade("2024")
                .withCodigo("000")
                .withMesValidade("1")
                .withMarca("MASTER_CARD")
                .now();

        Mockito.when(cartoesRepository.findByIdAndCliente_cpf(1L, "123")).thenReturn(Optional.of(cartoesEntity));

        cartoesService.atualizaCartao("123", 1L, putCartoesDto);

        Assertions.assertEquals(putCartoesDto.getNumero(), cartoesEntity.getNumero());
        Assertions.assertEquals(putCartoesDto.getCodigo(), cartoesEntity.getCodigo());
        Assertions.assertEquals(putCartoesDto.getAnoValidade(), cartoesEntity.getAnoValidade());
        Assertions.assertEquals(putCartoesDto.getMesValidade(), cartoesEntity.getMesValidade());
        Assertions.assertEquals(putCartoesDto.getMarca(), cartoesEntity.getMarca().toString());
    }

    @Test
    @DisplayName("deveria lancar uma exception ao atualizar o cartao de um id/cpf nao existente")
    void deveriaLancarUmaExceptionQuandoTentarAtualizarOCartaoDeIdInexisteOuCpfNaoExistente() {
        RequestCartoesDto cartoesDto = RequestCartoesDtoBuilder.one().now();
        Assertions.assertThrows(CartaoNaoVinculado.class, () -> cartoesService.atualizaCartao("123", 1L, cartoesDto));
    }

    private CartoesEntity criaCartaoEntity(RequestCartoesDto cartoesDto, Long id) {
        CartoesEntity cartoesEntity = new CartoesEntity();
        cartoesEntity.setId(id);
        cartoesEntity.setCodigo(cartoesDto.getCodigo());
        cartoesEntity.setMesValidade(cartoesDto.getMesValidade());
        cartoesEntity.setAnoValidade(cartoesDto.getAnoValidade());
        cartoesEntity.setMarca(EnumMarcaCartao.valueOf(cartoesDto.getMarca()));
        cartoesEntity.setNumero(cartoesDto.getNumero());
        return cartoesEntity;
    }
}