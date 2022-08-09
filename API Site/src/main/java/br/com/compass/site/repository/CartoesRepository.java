package br.com.compass.site.repository;

import br.com.compass.site.entities.CartoesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartoesRepository extends JpaRepository<CartoesEntity, Long> {
}
