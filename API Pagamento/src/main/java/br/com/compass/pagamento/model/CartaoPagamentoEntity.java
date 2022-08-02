package br.com.compass.pagamento.model;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "pagamento_cartao_dados")
public class CartaoPagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroCartao;
    private String nomeCartao;
    private String codigoSeguranca;
    private String marca;
    private int mesExpiracao;
    private int anoExpiracao;
    private String moeda;
    private Double valor;
}
