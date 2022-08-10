package br.com.compass.site.handler;

import br.com.compass.site.exceptions.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MarcaCartaoInvalida.class)
    public ResponseEntity<ExceptionResponseDto> handlerMarcaCataoInvalida(MarcaCartaoInvalida exception) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto("Marca do cartao invalida", "Marca");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AnoCartaoInvalido.class)
    public ResponseEntity<ExceptionResponseDto> handlerAnoCartaoInvalido(AnoCartaoInvalido exception) {
        String message = "Ano do cartao invalido, deve conter 4 digitos, alem de ser posterior a data no qual o Sr(a) se encontra e inferior a 6 anos no futuro";
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(message, "AnoValidade");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CodigoSegurancaInvalido.class)
    public ResponseEntity<ExceptionResponseDto> handlerCodigoSegurancaInvalido(CodigoSegurancaInvalido exception) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto("Codigo de seguranca do cartao invalido, deve conter 3 digitos", "Codigo");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MesCartaoInvalido.class)
    public ResponseEntity<ExceptionResponseDto> handlerMesCartaoInvalido(MesCartaoInvalido exception) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto("Mes do cartao invalido, deve ser de 1 - 12", "MesValidade");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CartaoNaoVinculado.class)
    public ResponseEntity<ExceptionResponseDto> handlerCartaoNaoVinculado(CartaoNaoVinculado exception) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto("Cartao não vinculado ou cliente nao existe", "cartaoId ou clientId");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ItemNaoExiste.class)
    public ResponseEntity<ExceptionResponseDto> handlerItemNaoExiste(ItemNaoExiste exception) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto("SkuId nao pertence a nenhum item", "SkuId");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ItemSemEstoque.class)
    public ResponseEntity<ExceptionResponseDto> handlerItemSemEstoque(ItemSemEstoque exception) {
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(exception.getMessage(), "qtd");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(CpfJaCadastrado.class)
    public ResponseEntity<ExceptionResponseDto> handlerAnoCartaoInvalido(CpfJaCadastrado exception) {
        String message = "CPF já cadastrado";
        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto(message, "Cpf");
        return ResponseEntity.status(HttpStatus.OK).body(exceptionResponseDTO);
    }

    // Nao me processa Reginaldo
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponseDto> handleHttpValidationExceptions(HttpMessageNotReadableException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorMessage = "Invalid information";

        if (e.getCause() instanceof InvalidFormatException) {
            InvalidFormatException cause = (InvalidFormatException) e.getCause();
            errorMessage = cause.getCause().getMessage();
        }
        return ResponseEntity.status(status).body(new ExceptionResponseDto(String.valueOf(status.value()), errorMessage));

    }
}