package br.com.compass.Sprint05.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    @CreationTimestamp
    private LocalDateTime dataCriacao;
    private LocalDateTime dataValidade;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "itens_ofertas",
            joinColumns = {@JoinColumn(name = "item_id")},
            inverseJoinColumns = {@JoinColumn(name = "oferta_id")})
    private List<OfertaEntity> ofertas;
}
