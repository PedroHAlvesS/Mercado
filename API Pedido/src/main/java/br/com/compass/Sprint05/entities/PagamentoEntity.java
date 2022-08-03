package br.com.compass.Sprint05.entities;

import br.com.compass.Sprint05.constants.EnumMarcaCartao;
import br.com.compass.Sprint05.constants.EnumMoedaTipo;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "pedido_pagamentos")
@Data
public class PagamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroCartao;
    private String nomeCartao;
    private String codigoSeguranca;
    @Enumerated(EnumType.STRING)
    private EnumMarcaCartao marca;
    private String mesExpiracao;
    private String anoExpiracao;
    @Enumerated(EnumType.STRING)
    private EnumMoedaTipo moeda;
    private Double valor;
}
