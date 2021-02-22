package ru.geekbrains.service;

import ru.geekbrains.persist.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductDTO> findAll();

    Optional<ProductDTO> findById(long id);

    List<ProductDTO> findWithFilter(String productNameFilter);

    List<ProductDTO> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);

    void save(ProductDTO product);

    void delete(long id);
}
