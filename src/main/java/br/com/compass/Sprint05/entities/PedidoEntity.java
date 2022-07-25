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
    @OneToMany
    private List<ItemEntity> itens;
    private Double total;
}


