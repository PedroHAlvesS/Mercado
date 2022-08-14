package br.com.compass.site.services;

import br.com.compass.site.dto.ApiPedido.request.RequestApiPedidoCartaoDto;
import br.com.compass.site.dto.ApiPedido.request.RequestApiPedidoDto;
import br.com.compass.site.dto.ApiPedido.request.RequestApiPedidoItemDto;
import br.com.compass.site.dto.ApiPedido.response.ResponseApiPedidoDto;
import br.com.compass.site.dto.cartoes.response.ResponseCartoesDto;
import br.com.compass.site.dto.checkout.request.RequestCheckoutDto;
import br.com.compass.site.dto.checkout.request.RequestCheckoutItensDto;
import br.com.compass.site.dto.checkout.response.ResponseCheckoutDto;
import br.com.compass.site.dto.checkout.response.ResponseCheckoutItensDto;
import br.com.compass.site.entities.CartoesEntity;
import br.com.compass.site.entities.ClienteEntity;
import br.com.compass.site.entities.ItemEntity;
import br.com.compass.site.exceptions.CartaoNaoVinculado;
import br.com.compass.site.exceptions.ClienteNaoCadastrado;
import br.com.compass.site.exceptions.ItemNaoExiste;
import br.com.compass.site.repository.CartoesRepository;
import br.com.compass.site.repository.ClienteRepository;
import br.com.compass.site.repository.ItemRepository;
import br.com.compass.site.util.ControleDeEstoque;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final WebClient.Builder webBuilder;
    private final ItemRepository itemRepository;
    private final ClienteRepository clienteRepository;
    private final CartoesRepository cartoesRepository;
    private final ModelMapper modelMapper;
    private final ControleDeEstoque controleDeEstoque;


    public ResponseCheckoutDto enviaPedido(RequestCheckoutDto requestDto) {
        List<RequestApiPedidoItemDto> requestApiPedidoItemDtoList = recebeListaDeItensDto(requestDto);
        Double total = calculaTotalDosItens(requestApiPedidoItemDtoList);

        String nome = recuperaNomeCliente(requestDto);
        CartoesEntity cartoesEntity = recuperaCartaoEntity(requestDto);
        ResponseCartoesDto responseCartoesDto = montaDadosDoCartao(cartoesEntity);

        RequestApiPedidoDto apiPedidoDto = criaPedidoDto(requestDto, total, requestApiPedidoItemDtoList, responseCartoesDto, nome);
        ResponseApiPedidoDto responseApiPedidoDto = mandaRequestParaApiPedido(apiPedidoDto);
        return montaCheckoutResponse(responseApiPedidoDto);
    }

    private ResponseCartoesDto montaDadosDoCartao(CartoesEntity cartoesEntity) {
        return modelMapper.map(cartoesEntity, ResponseCartoesDto.class);
    }

    private ResponseCheckoutDto montaCheckoutResponse(ResponseApiPedidoDto responseApiPedidoDto) {
        ResponseCheckoutDto responseCheckoutDto = new ResponseCheckoutDto();
        responseCheckoutDto.setNumeroDoPedido(responseApiPedidoDto.getId());
        responseCheckoutDto.setTotal(responseApiPedidoDto.getTotal());
        responseCheckoutDto.setStatus(responseApiPedidoDto.getStatus());
        List<ResponseCheckoutItensDto> responseCheckoutItensDtoList = responseApiPedidoDto.getItens().stream().map(
                responseApiPedidoItemDto -> modelMapper.map(responseApiPedidoItemDto, ResponseCheckoutItensDto.class)).collect(Collectors.toList());
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

    private String recuperaNomeCliente(RequestCheckoutDto requestCheckoutDto) {
        String cpf = requestCheckoutDto.getClienteInfo().getClientId();
        ClienteEntity clienteEntity = clienteRepository.findById(cpf).orElseThrow(ClienteNaoCadastrado::new);
        return clienteEntity.getNome();
    }

    private CartoesEntity recuperaCartaoEntity(RequestCheckoutDto requestDto) {
        String cpf = requestDto.getClienteInfo().getClientId();
        Long cartaoId = requestDto.getClienteInfo().getCartaoId();
        return cartoesRepository.findByIdAndCliente_cpf(cartaoId, cpf).orElseThrow(CartaoNaoVinculado::new);
    }

    private Double calculaTotalDosItens(List<RequestApiPedidoItemDto> requestDto) {
        Double total = 0.0;
        for (RequestApiPedidoItemDto requestApiPedidoItemDto : requestDto) {
            Double valorItem = requestApiPedidoItemDto.getValor();
            int quantidadeItem = requestApiPedidoItemDto.getQtd();
            total += (valorItem * quantidadeItem);
        }
        return total;
    }

    private List<RequestApiPedidoItemDto> recebeListaDeItensDto(RequestCheckoutDto requestDto) {
        List<RequestApiPedidoItemDto> requestApiPedidoItemDtoList = new ArrayList<>();
        for (int i = 0; i < requestDto.getItens().size(); i++) {
            String skuId = requestDto.getItens().get(i).getSkuId();
            RequestCheckoutItensDto requestCheckoutItensDtoUnitario = requestDto.getItens().get(i);

            ItemEntity itemEntity = itemRepository.findBySkuId(skuId).orElseThrow(() -> new ItemNaoExiste("Item de SkuId: " + requestCheckoutItensDtoUnitario.getSkuId() + " nao existe"));
            ItemEntity itemEntityEstoqueAtualizado = controleDeEstoque.atualizaEstoque(requestCheckoutItensDtoUnitario, itemEntity);

            RequestApiPedidoItemDto requestApiPedidoItemDto = modelMapper.map(itemEntity, RequestApiPedidoItemDto.class);
            requestApiPedidoItemDto.setQtd(requestDto.getItens().get(i).getQtd());
            requestApiPedidoItemDtoList.add(requestApiPedidoItemDto);
            itemRepository.save(itemEntityEstoqueAtualizado);
        }
        return requestApiPedidoItemDtoList;
    }

}