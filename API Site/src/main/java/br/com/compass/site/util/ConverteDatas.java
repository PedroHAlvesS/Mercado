package br.com.compass.site.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

@Component
public class ConverteDatas {

    public String formataDataBrasileira(LocalDateTime dataFormatoISO) {
        DateTimeFormatter formatoBrasileiro = DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm:ss").withResolverStyle(ResolverStyle.STRICT);
        return dataFormatoISO.format(formatoBrasileiro);
    }
}
