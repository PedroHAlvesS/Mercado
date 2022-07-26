package br.com.compass.Sprint05.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "pedidos_itens",
            joinColumns = {@JoinColumn(name = "pedido_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")})
    private List<ItemEntity> itens;
    private Double total;
}


