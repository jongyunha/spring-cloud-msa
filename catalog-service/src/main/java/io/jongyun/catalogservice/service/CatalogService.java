package io.jongyun.catalogservice.service;

import io.jongyun.catalogservice.domain.Catalog;

import java.util.List;

/**
 * @author jongyunha created on 22. 3. 22.
 */
public interface CatalogService {
    List<Catalog> getAllCatalogs();
}
