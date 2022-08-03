package br.com.compass.pagamento.entities;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "pagamento_cartao_dados")
public class CartaoPagamentoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cartaoId;
    private String numeroCartao;
    private String nomeCartao;
    private String codigoSeguranca;
    private String marca;
    private String mesExpiracao;
    private String anoExpiracao;
    private String moeda;
    private Double valor;
}
