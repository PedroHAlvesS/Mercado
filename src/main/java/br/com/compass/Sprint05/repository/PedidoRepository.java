package br.com.compass.Sprint05.repository;

import br.com.compass.Sprint05.entities.PedidoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {
    Page<PedidoEntity> findByCpf(String Cpf, Pageable pageable);
}
