package br.com.compass.Sprint05.entities;

import br.com.compass.Sprint05.constants.EnumStatus;
import br.com.compass.Sprint05.constants.EnumStatusPagamento;
import br.com.compass.Sprint05.constants.EnumTipoDoPagamento;
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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id")
    private List<ItemEntity> itens;
    private Double total;
    @Enumerated(EnumType.STRING)
    private EnumStatus status = EnumStatus.EM_ANDAMENTO;
    @Enumerated(EnumType.STRING)
    private EnumStatusPagamento statusDoPagamento = EnumStatusPagamento.PROCESSANDO;
    @Enumerated(EnumType.STRING)
    private EnumTipoDoPagamento tipoDoPagamento;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pagamento_id")
    private PagamentoEntity pagamento;
}


