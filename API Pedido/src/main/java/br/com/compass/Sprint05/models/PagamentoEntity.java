package br.com.compass.Sprint05.models;

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
    private int mesExpiracao;
    private int anoExpiracao;
    @Enumerated(EnumType.STRING)
    private EnumMoedaTipo moeda;
    private Double valor;
}
