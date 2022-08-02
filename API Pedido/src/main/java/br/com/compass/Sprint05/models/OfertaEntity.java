package br.com.compass.Sprint05.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedido_itens_ofertas")
@Data
public class OfertaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double desconto;
    @CreationTimestamp
    private LocalDateTime dataCriacao;
    private LocalDateTime dataValidade;
}

