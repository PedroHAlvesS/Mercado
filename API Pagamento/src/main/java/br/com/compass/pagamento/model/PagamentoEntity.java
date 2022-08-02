package br.com.compass.pagamento.model;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Pagamento")
@Data
public class PagamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pedidoId;
    private String cpf;
    private Double total;
    private String tipoDoPagamento;
    @CreationTimestamp
    private LocalDateTime dataCriado;
    @OneToOne(cascade = CascadeType.ALL)
    private CartaoPagamentoEntity pagamento;
}

