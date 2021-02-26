package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductByNameLike(String productName);

    @Query("select p from Product p where p.price between :minPrice and :maxPrice")
    List<Product> findProductsByPrice(BigDecimal minPrice, BigDecimal maxPrice);

/*    @Query("select p from Product p where p.name like :productname")
    List<Product> findProductByNameLike(@Param("productname") String productName);*/
}