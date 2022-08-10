package br.com.compass.site.repository;

import br.com.compass.site.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
     Optional<ItemEntity> findBySkuId(String skuId);
}
