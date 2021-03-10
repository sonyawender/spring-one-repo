package ru.geekbrains.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("select p from Product p " +
            "where (p.name like :productName or :productName is null) and " +
            "      (p.price >= :minPrice or :minPrice is null) and " +
            "      (p.price <= :maxPrice or :maxPrice is null)")
    List<Product> findWithFilter(@Param("productName") String productNameFilter,
                                 @Param("minPrice") BigDecimal minPrice,
                                 @Param("maxPrice") BigDecimal maxPrice);

/*    @Query("select p from Product p where p.name like :productname")
    List<Product> findProductByNameLike(@Param("productname") String productName);*/
}