package br.com.compass.pagamento.service;

import br.com.compass.pagamento.dto.banco.request.RequestBancoPagamentoDto;
import br.com.compass.pagamento.dto.banco.request.RequestCartaoCliente;
import br.com.compass.pagamento.dto.banco.request.RequestClienteBancoDto;
import br.com.compass.pagamento.dto.banco.response.ResponseAuthDto;
import br.com.compass.pagamento.dto.banco.response.ResponseBancoPagamentoDto;
import br.com.compass.pagamento.dto.rabbitMQ.PagamentoMensagemRecebendoDto;
import br.com.compass.pagamento.entities.PagamentoEntity;
import br.com.compass.pagamento.exceptions.NaoAutenticadoException;
import br.com.compass.pagamento.repository.PagamentoRepository;
import br.com.compass.pagamento.util.AutenticaBanco;
import br.com.compass.pagamento.util.CriptografaNumeroCartao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private CriptografaNumeroCartao criptografaNumeroCartao;

    @Autowired
    private AutenticaBanco autenticaBanco;

    private String getAuthBearerToken = "teste";

    public Long salva(PagamentoMensagemRecebendoDto pagamentoDto) {
        PagamentoEntity pagamentoEntity = modelMapper.map(pagamentoDto, PagamentoEntity.class);
        pagamentoEntity.setPedidoId(pagamentoDto.getId());
        pagamentoEntity.getPagamento().setCartaoId(pagamentoDto.getPagamento().getId());

        PagamentoEntity savePagamento = pagamentoRepository.save(pagamentoEntity);
        return savePagamento.getId();

    }

    public ResponseBancoPagamentoDto retornoDoBanco(Long id, PagamentoMensagemRecebendoDto pagamentoDto) {
        Optional<PagamentoEntity> pagamentoEntityOptional = pagamentoRepository.findById(id);

        ResponseBancoPagamentoDto responseBancoPagamentoDto = solicitaBanco(pagamentoDto);
        if (pagamentoEntityOptional.isPresent()) {
            pagamentoEntityOptional.get().setStatus(responseBancoPagamentoDto.getStatus());
            pagamentoRepository.save(pagamentoEntityOptional.get());
        }

        return responseBancoPagamentoDto;

    }



    private ResponseBancoPagamentoDto solicitaBanco(PagamentoMensagemRecebendoDto pagamentoMensagemDto) {
        String urlBancoPagamento = "https://pb-getway-payment.herokuapp.com/v1/payments/credit-card";

        RequestBancoPagamentoDto bancoPagamentoDto = buildBancoPagamento(pagamentoMensagemDto);
        ResponseBancoPagamentoDto responseBancoPagamentoDto;


        try {
            responseBancoPagamentoDto = webClientBuilder.build()
                    .post()
                    .uri(urlBancoPagamento)
                    .bodyValue(bancoPagamentoDto)
                    .headers(h -> h.setBearerAuth(getAuthBearerToken))
                    .retrieve()
                    .onStatus(HttpStatus.FORBIDDEN::equals, clientResponses -> clientResponses.bodyToMono(String.class).map(NaoAutenticadoException::new))
                    .bodyToMono(ResponseBancoPagamentoDto.class)
                    .block();
        } catch (NaoAutenticadoException naoAutenticadoException) {

            ResponseAuthDto authDto = autenticaBanco.autentica();
            this.getAuthBearerToken = authDto.getAcessToken();

            responseBancoPagamentoDto = webClientBuilder.build()
                    .post()
                    .uri(urlBancoPagamento)
                    .bodyValue(bancoPagamentoDto)
                    .headers(h -> h.setBearerAuth(getAuthBearerToken))
                    .retrieve()
                    .bodyToMono(ResponseBancoPagamentoDto.class)
                    .block();
        }

        return responseBancoPagamentoDto;


    }

    private RequestBancoPagamentoDto buildBancoPagamento(PagamentoMensagemRecebendoDto pagamentoMensagemDto) {
        RequestBancoPagamentoDto bancoPagamentoDto = new RequestBancoPagamentoDto();
        RequestClienteBancoDto customer = new RequestClienteBancoDto();
        bancoPagamentoDto.setCustomer(customer);
        RequestCartaoCliente card = new RequestCartaoCliente();
        bancoPagamentoDto.setCard(card);

        bancoPagamentoDto.setSellerId("2ef97ff2-1df7-471a-9b6a-8a296790aa69");
        bancoPagamentoDto.getCustomer().setDocumentType("CPF");
        bancoPagamentoDto.getCustomer().setDocumentNumber(pagamentoMensagemDto.getCpf());
        bancoPagamentoDto.setPaymentType("CREDIT_CARD");
        bancoPagamentoDto.setCurrency("BRL");
        bancoPagamentoDto.setTransactionAmount(pagamentoMensagemDto.getTotal());
        bancoPagamentoDto.getCard().setNumberToken(criptografaNumeroCartao.criptografaParaMD5(pagamentoMensagemDto.getPagamento().getNumeroCartao()));
        bancoPagamentoDto.getCard().setBrand(pagamentoMensagemDto.getPagamento().getMarca());
        bancoPagamentoDto.getCard().setCardholderName(pagamentoMensagemDto.getPagamento().getNomeCartao());
        bancoPagamentoDto.getCard().setSecurityCode(pagamentoMensagemDto.getPagamento().getCodigoSeguranca());
        bancoPagamentoDto.getCard().setExpirationMonth(pagamentoMensagemDto.getPagamento().getMesExpiracao());
        bancoPagamentoDto.getCard().setExpirationYear(pagamentoMensagemDto.getPagamento().getAnoExpiracao());

        return bancoPagamentoDto;
    }

}
