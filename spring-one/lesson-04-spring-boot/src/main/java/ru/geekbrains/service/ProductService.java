package ru.geekbrains.service;

import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> findAll();

    Optional<ProductDTO> findById(long id);

    Page<ProductDTO> findWithFilter(String productNameFilter, BigDecimal minPrice, BigDecimal maxPrice, Integer page, Integer size, String sortByField);

    void save(ProductDTO product);

    void delete(long id);
}
