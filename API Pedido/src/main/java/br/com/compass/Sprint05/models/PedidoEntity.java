package br.com.compass.Sprint05.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "pedidos_itens",
            joinColumns = {@JoinColumn(name = "pedido_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")})
    private List<ItemEntity> itens;
    private Double total;
}


