package br.com.compass.site.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "site_cliente")
public class ClienteEntity {
    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private String cpf;
    private String nome;
    @CreationTimestamp
    private LocalDateTime dataCriacao;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private List<CartoesEntity> cartoes;
}

