package br.com.compass.Sprint05.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "item")
@Data
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double valor;
    private LocalDate dataCriacao;
    private LocalDate dataValidade;
    @OneToOne
    private OfertaEntity oferta;
}
