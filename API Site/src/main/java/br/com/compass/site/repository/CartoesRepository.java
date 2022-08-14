package br.com.compass.site.repository;

import br.com.compass.site.entities.CartoesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartoesRepository extends JpaRepository<CartoesEntity, Long> {
    Optional<CartoesEntity> findByIdAndCliente_cpf(Long id, String cpf);
}
