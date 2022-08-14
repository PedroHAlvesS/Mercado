package br.com.compass.site.entities;

import br.com.compass.site.constants.EnumMarcaCartao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "site_cartoes")
public class CartoesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private String codigo;
    @Column(name = "mes_validade")
    private String mesValidade;
    @Column(name = "ano_validade")
    private String anoValidade;
    @Enumerated(EnumType.STRING)
    private EnumMarcaCartao marca;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;
}
