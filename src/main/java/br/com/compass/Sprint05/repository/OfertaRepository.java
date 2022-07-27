package br.com.compass.Sprint05.repository;

import br.com.compass.Sprint05.models.OfertaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaRepository extends JpaRepository<OfertaEntity, Long> {
}
