package br.com.compass.site.util;

import br.com.compass.site.constants.EnumMarcaCartao;
import br.com.compass.site.dto.cartoes.request.RequestCartoesDto;
import br.com.compass.site.exceptions.AnoCartaoInvalido;
import br.com.compass.site.exceptions.CodigoSegurancaInvalido;
import br.com.compass.site.exceptions.MarcaCartaoInvalida;
import br.com.compass.site.exceptions.MesCartaoInvalido;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ValidaCartoes {
    public void ValidaCartao(RequestCartoesDto requestCartoesDto) {
        validaDigitoVerificador(requestCartoesDto);
        validaMesExpiracao(requestCartoesDto);
        validaAnoExpiracao(requestCartoesDto);
        validaMarcaCartao(requestCartoesDto);
    }

    private void validaMarcaCartao(RequestCartoesDto requestCartoesDto) {
        try {
            EnumMarcaCartao.valueOf(requestCartoesDto.getMarca());
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new MarcaCartaoInvalida();
        }
    }

    private void validaDigitoVerificador(RequestCartoesDto requestCartoesDto) {
        String regexStringCodigoSegurancaCartao = "^[0-9]{3}";

        if (!requestCartoesDto.getCodigo().matches(regexStringCodigoSegurancaCartao)) {
            throw new CodigoSegurancaInvalido();
        }
    }

    private void validaMesExpiracao(RequestCartoesDto requestCartoesDto) {
        // Nao me processa Reginaldo
        String regexStringMesExpiracao = "^[1-9]{1}|^1[0-2]{1}";

        if (!requestCartoesDto.getMesValidade().matches(regexStringMesExpiracao)) {
            throw new MesCartaoInvalido();
        }
    }

    private void validaAnoExpiracao(RequestCartoesDto requestCartoesDto) {
        String regexStringAnoExpiracao = "^[0-9]{4}";
        boolean anoCartaoValido = false;

        if (requestCartoesDto.getAnoValidade().matches(regexStringAnoExpiracao)) {
            int anoAtual = LocalDate.now().getYear();
            int anoInformado = Integer.parseInt(requestCartoesDto.getAnoValidade());

            if (anoInformado >= anoAtual && anoInformado <= anoAtual + 5) {
                anoCartaoValido = true;
            }
        }

        if (!anoCartaoValido) {
            throw new AnoCartaoInvalido();
        }
    }
}
