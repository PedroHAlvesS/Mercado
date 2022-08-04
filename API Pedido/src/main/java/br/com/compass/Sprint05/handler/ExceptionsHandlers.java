package br.com.compass.Sprint05.handler;

import br.com.compass.Sprint05.exceptions.ExceptionResponseDto;
import br.com.compass.Sprint05.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandlers {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionResponseDto>> handleInvalidArgument(MethodArgumentNotValidException exception) {
        List<ExceptionResponseDto> responseDTOList = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ExceptionResponseDto error = new ExceptionResponseDto(e.getField(), message);
            responseDTOList.add(error);
        });
        return ResponseEntity.badRequest().body(responseDTOList);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNaoEncontrado.class)
    public ResponseEntity<ExceptionResponseDto> handlerItemNaoEncontrado(ItemNaoEncontrado exception) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto("Item nao encontrado", "Item");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDTO);

    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(PedidoNaoEncontrado.class)
    public ResponseEntity<ExceptionResponseDto> handlerPedidoNaoEncontrado(PedidoNaoEncontrado exception) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto("Pedido nao encontrado", "Pedido");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDTO);

    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(OfertaNaoEncontrado.class)
    public ResponseEntity<ExceptionResponseDto> handlerOfertaNaoEncontrado(OfertaNaoEncontrado exception) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto("Oferta nao encontrada", "Oferta");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponseDTO);

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataValidadeInvalida.class)
    public ResponseEntity<ExceptionResponseDto> handlerDataValidadeAntesDaCriacao(DataValidadeInvalida exception) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto("Data validade está antes da data de criação", "dataValidade");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);

    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataInvalida.class)
    public ResponseEntity<ExceptionResponseDto> handlerDataInvalida(DataInvalida exception) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto("Data invalida", "Data");
        return ResponseEntity.badRequest().body(exceptionResponseDTO);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TipoDoPagamentoInvalido.class)
    public ResponseEntity<ExceptionResponseDto> handlerTipoDoPagamentoInvalido(TipoDoPagamentoInvalido exception) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto("Tipo de pagamento invalido", "TipoDoPagamento");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MarcaCartaoInvalida.class)
    public ResponseEntity<ExceptionResponseDto> handlerMarcaCataoInvalida(MarcaCartaoInvalida exception) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto("Marca do cartao invalida", "Marca");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TipoDaMoedaInvalida.class)
    public ResponseEntity<ExceptionResponseDto> handlerTipoDaMoedaInvalida(TipoDaMoedaInvalida exception) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto("Tipo de moeda invalida", "Moeda");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(ValorCartaoMaior.class)
    public ResponseEntity<ExceptionResponseDto> handlerValorCartaoMaior(ValorCartaoMaior exception) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(exception.getMessage(), "Valor");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponseDTO);
    }
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(ValorCartaoMenor.class)
    public ResponseEntity<ExceptionResponseDto> handlerValorCartaoMenor(ValorCartaoMenor exception) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(exception.getMessage(), "Valor");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exceptionResponseDTO);
    }

}
