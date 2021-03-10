package ru.geekbrains;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.geekbrains.persist.Product;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class Lesson04SpringBootApplicationTests {

    @PersistenceContext
    private EntityManager em;

    @Test
    void contextLoads() {
   /*     em.createQuery("select p from Product p " +
                "where (p.name like :productName or :productName is null) and " +
                "      (p.price >= :minPrice or :minPrice is null) and " +
                "      (p.price <= :maxPrice or :maxPrice is null)").getResultList();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);

        Root<Product> root = query.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.like(root.get("name"), "%ook%"));
        predicates.add(cb.greaterThanOrEqualTo(root.get("price"), 15));
        predicates.add(cb.lessThanOrEqualTo(root.get("price"), 120));
    */
        List<Product> resultList = findWithFilter(null, null, null);
        resultList.forEach(System.out::println);
    }

    List<Product> findWithFilter(String productNameFilter,
                                 BigDecimal minPrice,
                                 BigDecimal maxPrice) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);

        Root<Product> root = query.from(Product.class);
        List<Predicate> predicates = new ArrayList<>();

        if (productNameFilter != null && !productNameFilter.isBlank()) {
            predicates.add(cb.like(root.get("name"), productNameFilter));
        }
        if (minPrice != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("price"), minPrice));
        }
        if (maxPrice != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("price"), maxPrice));
        }

        return em.createQuery(query.select(root).where(predicates.toArray(new Predicate[0])))
                .getResultList();
    }

}
