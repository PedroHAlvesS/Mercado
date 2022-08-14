package br.com.compass.site.services;

import br.com.compass.site.builders.ClienteEntityBuilder;
import br.com.compass.site.builders.RequestClienteDtoBuilder;
import br.com.compass.site.dto.cliente.request.RequestClienteDto;
import br.com.compass.site.dto.cliente.request.RequestPutClienteDto;
import br.com.compass.site.dto.cliente.response.ResponseClienteDto;
import br.com.compass.site.entities.ClienteEntity;
import br.com.compass.site.exceptions.CpfJaCadastrado;
import br.com.compass.site.repository.ClienteRepository;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = ClienteService.class)
class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @MockBean
    private ClienteRepository clienteRepository;

    @SpyBean
    private ModelMapper modelMapper;

    @Test
    @DisplayName("deveria criar um cliente caso o cpf nao esteja cadastrado")
    void deveriaCriarUmClienteCasoOCpfNaoEstejaJaCadastrado() {
        RequestClienteDto clienteDto = RequestClienteDtoBuilder.one().now();
        ClienteEntity savedEntity = ClienteEntityBuilder.one().withDataCriacao(LocalDateTime.now()).now();

        Mockito.when(clienteRepository.existsById(clienteDto.getCpf())).thenReturn(false);
        Mockito.when(clienteRepository.save(any())).thenReturn(savedEntity);

        ResponseClienteDto responseDto = clienteService.criaCliente(clienteDto);

        Assertions.assertEquals(savedEntity.getCpf(), responseDto.getCpf());
        Assertions.assertEquals(savedEntity.getNome(), responseDto.getNome());
        Assertions.assertEquals(savedEntity.getDataCriacao(), responseDto.getDataCriacao());
    }

    @Test
    @DisplayName("deveria lancar uma exception caso o cpf já esteja cadastrado")
    void deveriaLancarUmaExceptionQuandoCadastraUmCpfJaExistente() {
        RequestClienteDto clienteDto = RequestClienteDtoBuilder.one().now();

        Mockito.when(clienteRepository.existsById(clienteDto.getCpf())).thenReturn(true);

        Assertions.assertThrows(CpfJaCadastrado.class, () -> clienteService.criaCliente(clienteDto));
    }


    @Test
    @DisplayName("deveria listar todos os clientes")
    void deveriaListarTodosOsClientes() {
        ClienteEntity clienteEntity1 = ClienteEntityBuilder.one().withCpf("123").now();
        ClienteEntity clienteEntity2 = ClienteEntityBuilder.one().withCpf("456").now();
        List<ClienteEntity> clienteEntityList = new ArrayList<>();
        clienteEntityList.add(clienteEntity1);
        clienteEntityList.add(clienteEntity2);

        Mockito.when(clienteRepository.findAll()).thenReturn(clienteEntityList);

        List<ResponseClienteDto> responseClienteDtos = clienteService.listaClientes();

        for (int i = 0; i < clienteEntityList.size(); i++) {
            Assertions.assertEquals(clienteEntityList.get(i).getNome(), responseClienteDtos.get(i).getNome());
            Assertions.assertEquals(clienteEntityList.get(i).getCpf(), responseClienteDtos.get(i).getCpf());
            Assertions.assertEquals(clienteEntityList.get(i).getDataCriacao(), responseClienteDtos.get(i).getDataCriacao());;
        }
        Assertions.assertEquals(clienteEntityList.size(), responseClienteDtos.size());
    }

    @Test
    @DisplayName("deveria listar cliente unitario")
    void deveriaListarUmClienteUnitario() {
        ClienteEntity clienteEntity = ClienteEntityBuilder.one().withCpf("111.222.333-44").now();

        Mockito.when(clienteRepository.findById(clienteEntity.getCpf())).thenReturn(Optional.of(clienteEntity));

        ResponseClienteDto responseClienteDto = clienteService.listaCliente(clienteEntity.getCpf());

        Assertions.assertEquals(clienteEntity.getNome(), responseClienteDto.getNome());
        Assertions.assertEquals(clienteEntity.getCpf(), responseClienteDto.getCpf());
        Assertions.assertEquals(clienteEntity.getDataCriacao(), responseClienteDto.getDataCriacao());;
    }

    @Test
    @DisplayName("deveria lancar uma exception ao buscar por um cpf nao existente")
    void deveriaLancarUmaExceptionAoBuscarPorUmCpfNaoExistente() {
        ClienteEntity clienteEntity = ClienteEntityBuilder.one().withCpf("111.222.333-44").now();

        Mockito.when(clienteRepository.findById(clienteEntity.getCpf())).thenReturn(Optional.of(clienteEntity));

        Assertions.assertThrows(ResponseStatusException.class, () -> clienteService.listaCliente("xxx.yyy.zzz-ww"));
    }

    @Test
    @DisplayName("deveria atualizar um cliente (apenas o nome) ")
    void deveriaAtualizarApenasONomeDoCliente() {
        LocalDateTime dataCriada = LocalDateTime.of(2000, 10, 27, 23, 45, 0);
        ClienteEntity clienteEntity = ClienteEntityBuilder.one()
                .withNome("teste")
                .withCpf("xxx")
                .withDataCriacao(dataCriada)
                .now();
        RequestPutClienteDto putClienteDto = new RequestPutClienteDto();
        putClienteDto.setNome("Novo nome teste");

        Mockito.when(clienteRepository.findById(clienteEntity.getCpf())).thenReturn(Optional.of(clienteEntity));

        clienteService.atualizaCliente(clienteEntity.getCpf(), putClienteDto);

        Mockito.verify(clienteRepository).save(clienteEntity);
        Assertions.assertEquals("xxx", clienteEntity.getCpf());
        Assertions.assertEquals(dataCriada, clienteEntity.getDataCriacao());
        Assertions.assertEquals(putClienteDto.getNome(), clienteEntity.getNome());
    }

    @Test
    @DisplayName("deveria lancar uma exception ao buscar por um cpf nao existente")
    void deveriaLancarUmaExceptionQuandoTentarAtualizaUmCpfNaoExistente() {
        ClienteEntity clienteEntity = ClienteEntityBuilder.one().withCpf("111.222.333-44").now();
        RequestPutClienteDto putClienteDto = new RequestPutClienteDto();
        putClienteDto.setNome("abc");

        Mockito.when(clienteRepository.findById(clienteEntity.getCpf())).thenReturn(Optional.of(clienteEntity));

        Assertions.assertThrows(ResponseStatusException.class, () -> clienteService.atualizaCliente("xxx.yyy.zzz-ww", putClienteDto));
    }

}