package br.com.compass.Sprint05.models;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "oferta")
@Data
public class OfertaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double desconto;
    private LocalDate dataCriacao;
    private LocalDate dataValidacao;
}

