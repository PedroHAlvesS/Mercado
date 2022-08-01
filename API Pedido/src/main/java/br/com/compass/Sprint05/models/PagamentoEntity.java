package br.com.compass.Sprint05.models;

import br.com.compass.Sprint05.constants.EnumMarcaCartao;
import br.com.compass.Sprint05.constants.EnumMoedaTipo;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "pedido_pagamentos")
public class PagamentoEntity {
    private Long id;
    private String numeroCartao;
    private String nomeCartao;
    @Enumerated(EnumType.STRING)
    private EnumMarcaCartao marca;
    private int mesExpiracao;
    private int anoExpiracao;
    @Enumerated(EnumType.STRING)
    private EnumMoedaTipo moeda;
    private Double valor;
}
