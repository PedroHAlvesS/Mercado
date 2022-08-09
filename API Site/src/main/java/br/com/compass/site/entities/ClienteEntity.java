package br.com.compass.site.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Table(name = "site_cliente")
public class ClienteEntity {
    @Id
    @Column(updatable = false, unique = true, nullable = false)
    private Long cpf;
    private String nome;
    @CreationTimestamp
    private LocalDateTime dataCriacao;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private List<CartoesEntity> cartoes;
}
