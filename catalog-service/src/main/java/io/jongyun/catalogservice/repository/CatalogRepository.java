package io.jongyun.catalogservice.repository;

import io.jongyun.catalogservice.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author jongyunha created on 22. 3. 22.
 */
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    Optional<Catalog> findByProductId(String productId);
}
