package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

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
    public List<ProductDTO> findWithFilter(String productNameFilter) {
        return productRepository.findProductByNameLike(productNameFilter).stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findProductsByPrice(minPrice, maxPrice).stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }


    @Transactional
    @Override
    public void save(ProductDTO product) {
        productRepository.save(new Product(product));
    }

    @Transactional
    @Override
    public void delete(long id) {
        productRepository.deleteById(id);
    }
}
