package br.com.compass.site.repository;

import br.com.compass.site.entities.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, String> {
    Optional<ClienteEntity> findByCpfAndCartoes_id(String cpf, Long id);
}
