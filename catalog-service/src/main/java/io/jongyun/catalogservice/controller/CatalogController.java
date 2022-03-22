package io.jongyun.catalogservice.controller;

import io.jongyun.catalogservice.entity.Catalog;
import io.jongyun.catalogservice.service.CatalogService;
import io.jongyun.catalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jongyunha created on 22. 3. 22.
 */
@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {

    private final Environment env;
    private final CatalogService catalogService;

    @GetMapping("health_check")
    public String status() {
        return String.format("It's Working in catalog Service on PORT %s", env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs() {
        List<Catalog> allCatalogs = catalogService.getAllCatalogs();

        List<ResponseCatalog> result = new ArrayList<>();
        allCatalogs.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseCatalog.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
