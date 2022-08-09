package br.com.compass.site.handler;

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


//    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MarcaCartaoInvalida.class)
//    public ResponseEntity<ExceptionResponseDto> handlerMarcaCataoInvalida(MarcaCartaoInvalida exception) {
//        ExceptionResponseDto exceptionResponseDTO = new ExceptionResponseDto("Marca do cartao invalida", "Marca");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponseDTO);
//    }

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
