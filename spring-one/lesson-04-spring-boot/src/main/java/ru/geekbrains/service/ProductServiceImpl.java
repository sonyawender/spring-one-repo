package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductSpecification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl  implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<ProductDTO> findById(long id) {
        return productRepository.findById(id).map(ProductDTO::new);
    }

    @Override
    public Page<ProductDTO> findWithFilter(String productNameFilter, BigDecimal minPrice, BigDecimal maxPrice,
                                           Integer page, Integer size, String sortByField) {
        Specification<Product> spec = Specification.where(null);
        if (productNameFilter != null && !productNameFilter.isBlank()) {
            spec = spec.and(ProductSpecification.productNameLike(productNameFilter));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpecification.minPrice(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecification.maxPrice(maxPrice));
        }

        if (sortByField != null && !sortByField.isBlank()) {
            return productRepository.findAll(spec, PageRequest.of(page, size, Sort.by(sortByField)))
                    .map(ProductDTO::new);
        }

        return productRepository.findAll(spec, PageRequest.of(page, size))
                .map(ProductDTO::new);
    }

    @Transactional
    @Override
    public void save(ProductDTO product) {
        Product productToSave = new Product(product);
        productRepository.save(productToSave);
        if (product.getId() == null){
            product.setId(productToSave.getId());
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
