package ru.geekbrains.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ProductRepository {

    private final EntityManagerFactory emFactory;

    public ProductRepository(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    public List<Product> findAll() {
        return executeForEntityManager(
                em -> em.createQuery("from Product", Product.class).getResultList()
        );
    }

    public Product findById(long id) {
        return executeForEntityManager(
                em -> em.find(Product.class, id)
        );
    }

    public void deleteById(long id) {
        executeInTransaction(em -> {
            Product product = em.find(Product.class, id);
            if (product != null) {
                em.remove(product);
            }
        });
    }

    //Lesson 06. Task 3
    public List<Product> findProductsByUserId(long userId) {
        return executeForEntityManager(
                em -> em.createNativeQuery("SELECT * FROM products p " +
                        "WHERE id IN(SELECT products_id FROM customers_products cp WHERE cp.customers_id = :userid)", Product.class)
                        .setParameter("userid", userId)
                        .getResultList()
        );
    }

    public void insert(Product product) {
        executeInTransaction(em -> em.persist(product));
    }

    public void update(Product product) {
        executeInTransaction(em -> em.merge(product));
    }


    private <R> R executeForEntityManager(Function<EntityManager, R> function) {
        EntityManager em = emFactory.createEntityManager();
        try {
            return function.apply(em);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private void executeInTransaction(Consumer<EntityManager> consumer) {
        EntityManager em = emFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            consumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
