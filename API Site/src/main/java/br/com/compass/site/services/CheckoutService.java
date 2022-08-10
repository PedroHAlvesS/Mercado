package br.com.compass.site.services;

import br.com.compass.site.dto.ApiPedido.request.RequestApiPedidoCartaoDto;
import br.com.compass.site.dto.ApiPedido.request.RequestApiPedidoDto;
import br.com.compass.site.dto.ApiPedido.request.RequestApiPedidoItemDto;
import br.com.compass.site.dto.ApiPedido.response.ResponseApiPedidoDto;
import br.com.compass.site.dto.cartoes.response.ResponseCartoesDto;
import br.com.compass.site.dto.checkout.request.RequestCheckoutDto;
import br.com.compass.site.dto.checkout.response.ResponseCheckoutDto;
import br.com.compass.site.dto.checkout.response.ResponseCheckoutItensDto;
import br.com.compass.site.entities.ClienteEntity;
import br.com.compass.site.entities.ItemEntity;
import br.com.compass.site.exceptions.*;
import br.com.compass.site.repository.ClienteRepository;
import br.com.compass.site.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final WebClient.Builder webBuilder;
    private final ItemRepository itemRepository;
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;


    public ResponseCheckoutDto enviaPedido(RequestCheckoutDto requestDto) {
        Double total = calculaTotalDosItens(requestDto);
        List<RequestApiPedidoItemDto> requestApiPedidoItemDtoList = recebeListaDeItensDto(requestDto);
        ResponseCartoesDto responseCartoesDto = validaClienteCartao(requestDto);
        String nome = recuperaNomeCliente(requestDto);
        RequestApiPedidoDto apiPedidoDto = criaPedidoDto(requestDto, total, requestApiPedidoItemDtoList, responseCartoesDto, nome);
        ResponseApiPedidoDto responseApiPedidoDto = mandaRequestParaApiPedido(apiPedidoDto);
        return montaCheckoutResponse(responseApiPedidoDto);
    }

    private ResponseCheckoutDto montaCheckoutResponse(ResponseApiPedidoDto responseApiPedidoDto) {
        ResponseCheckoutDto responseCheckoutDto = new ResponseCheckoutDto();
        responseCheckoutDto.setNumeroDoPedido(responseApiPedidoDto.getId());
        responseCheckoutDto.setTotal(responseApiPedidoDto.getTotal());
        responseCheckoutDto.setStatus(responseApiPedidoDto.getStatus());
        List<ResponseCheckoutItensDto> responseCheckoutItensDtoList = responseApiPedidoDto.getItens().stream().map(responseApiPedidoItemDto -> modelMapper.map(responseApiPedidoItemDto, ResponseCheckoutItensDto.class)).collect(Collectors.toList());
        responseCheckoutDto.setItens(responseCheckoutItensDtoList);
        return responseCheckoutDto;
    }

    private ResponseApiPedidoDto mandaRequestParaApiPedido(RequestApiPedidoDto apiPedidoDto) {
        String urlPedido = "http://localhost:8080/api/pedido";
        return webBuilder.build().post().uri(urlPedido).bodyValue(apiPedidoDto).retrieve().bodyToMono(ResponseApiPedidoDto.class).block();
    }

    private RequestApiPedidoDto criaPedidoDto(RequestCheckoutDto requestDto, Double total, List<RequestApiPedidoItemDto> requestApiPedidoItemDtoList, ResponseCartoesDto responseCartoesDto, String nome) {
        RequestApiPedidoDto apiPedidoDto = new RequestApiPedidoDto();
        apiPedidoDto.setCpf(requestDto.getClienteInfo().getClientId());
        apiPedidoDto.setItens(requestApiPedidoItemDtoList);
        // cria o cartao
        RequestApiPedidoCartaoDto requestApiPedidoCartaoDto = new RequestApiPedidoCartaoDto();
        requestApiPedidoCartaoDto.setNumeroCartao(responseCartoesDto.getNumero());
        requestApiPedidoCartaoDto.setNomeCartao(nome);
        requestApiPedidoCartaoDto.setMesExpiracao(responseCartoesDto.getMesValidade());
        requestApiPedidoCartaoDto.setAnoExpiracao(responseCartoesDto.getAnoValidade());
        requestApiPedidoCartaoDto.setMarca(responseCartoesDto.getMarca());
        requestApiPedidoCartaoDto.setCodigoSeguranca(responseCartoesDto.getCodigo());
        requestApiPedidoCartaoDto.setValor(total);
        apiPedidoDto.setPagamento(requestApiPedidoCartaoDto);

        return apiPedidoDto;
    }

    private String recuperaNomeCliente(RequestCheckoutDto requestDto) {
        ClienteEntity clienteEntity = clienteRepository.findById(requestDto.getClienteInfo().getClientId())
                .orElseThrow(() -> new ClienteNaoExiste("Cliente com cpf: " + requestDto.getClienteInfo().getClientId() + " nao existe"));
        return clienteEntity.getNome();
    }

    private List<RequestApiPedidoItemDto> recebeListaDeItensDto(RequestCheckoutDto requestDto) {
        List<RequestApiPedidoItemDto> requestApiPedidoItemDtoList = new ArrayList<>();
        for (int i = 0; i < requestDto.getItens().size(); i++) {
            ItemEntity itemEntity = itemRepository.findBySkuId(requestDto.getItens().get(i).getSkuId()).orElseThrow(ItemNaoExiste::new);
            RequestApiPedidoItemDto requestApiPedidoItemDto = modelMapper.map(itemEntity, RequestApiPedidoItemDto.class);
            requestApiPedidoItemDto.setQtd(requestDto.getItens().get(i).getQtd());
            requestApiPedidoItemDtoList.add(requestApiPedidoItemDto);
        }
        return requestApiPedidoItemDtoList;
    }

    private ResponseCartoesDto validaClienteCartao(RequestCheckoutDto requestDto) {
        ClienteEntity clienteEntity = clienteRepository.findById(requestDto.getClienteInfo().getClientId())
                .orElseThrow(() -> new ClienteNaoExiste("Cliente com cpf: " + requestDto.getClienteInfo().getClientId() + " nao existe"));
        if (clienteEntity.getCartoes() == null) {
            throw new ClienteNaoPossuiCartao();
        }
        for (int i = 0; i < clienteEntity.getCartoes().size(); i++) {
            Long id = clienteEntity.getCartoes().get(i).getId();
            if (Objects.equals(id, requestDto.getClienteInfo().getCartaoId())) {
                return modelMapper.map(clienteEntity.getCartoes().get(i), ResponseCartoesDto.class);
            }
        }
        throw new CartaoNaoVinculado("Cartao de id: " + requestDto.getClienteInfo().getCartaoId() + "nao esta vinculado a esse cliente");

    }

    private Double calculaTotalDosItens(RequestCheckoutDto requestDto) {
        Double total = 0.0;
        for (int i = 0; i < requestDto.getItens().size(); i++) {
            ItemEntity itemEntity = itemRepository.findBySkuId(requestDto.getItens().get(i).getSkuId()).orElseThrow(ItemNaoExiste::new);
            if (itemEntity.getEstoque() < requestDto.getItens().get(i).getQtd()) {
                throw new ItemSemEstoque("Item de SkuId: " + requestDto.getItens().get(i).getSkuId() + "" +
                        "Nao tem essa quantidade no estoque, temos apenas: " + itemEntity.getEstoque());
            }
            total += itemEntity.getValor() * requestDto.getItens().get(i).getQtd();
            itemEntity.setEstoque(itemEntity.getEstoque() - requestDto.getItens().get(i).getQtd());
        }
        return total;
    }


}
