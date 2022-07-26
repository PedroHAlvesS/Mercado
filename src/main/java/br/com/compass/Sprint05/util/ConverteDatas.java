package br.com.compass.Sprint05.util;

import br.com.compass.Sprint05.exceptions.DataInvalida;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

@Component
public class ConverteDatas {
    public LocalDateTime formataDataISO8601(String data) {
        try {
            DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss").withResolverStyle(ResolverStyle.STRICT);
            return LocalDateTime.parse(data, formatoBrasileiro);
        } catch (Exception e) {
            throw new DataInvalida();
        }
    }

    public String formataDataBrasileira(LocalDateTime dataFormatoISO) {
        DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss").withResolverStyle(ResolverStyle.STRICT);
        return dataFormatoISO.format(formatoBrasileiro);
    }
}
