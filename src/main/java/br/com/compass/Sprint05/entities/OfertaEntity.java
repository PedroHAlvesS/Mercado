package br.com.compass.Sprint05.entities;

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
    private LocalDate dataCriacao;
    private LocalDate dataValidacao;
    private Double desconto;
    private String descricao;
}

