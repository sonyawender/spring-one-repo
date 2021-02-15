package ru.geekbrains.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ProductRepository {

    private final EntityManagerFactory emFactory;

    public ProductRepository(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    public List<Product> findAll() {
        EntityManager em = emFactory.createEntityManager();;
        return em.createQuery("from Product", Product.class)
                .getResultList();
    }

    public Product findById(long id) {
        EntityManager em = emFactory.createEntityManager();;
        return em.find(Product.class, id);
    }

    public void deleteById(long id) {
        EntityManager em = emFactory.createEntityManager();;
        Product product = em.find(Product.class, id);
        if (product != null) {
            em.getTransaction().begin();
            em.remove(product);
            em.getTransaction().commit();
            em.close();
        } else {
            System.out.println("Product doesn't exist. Try another ID.");
        }
    }

    public void insert(Product product) {
        EntityManager em = emFactory.createEntityManager();;
        em.getTransaction().begin();
        em.persist(product);
        em.getTransaction().commit();
        em.close();
    }

    public void update(Product product) {
        EntityManager em = emFactory.createEntityManager();;
        Product productToUpdate = em.find(Product.class, product.getId());

        em.getTransaction().begin();

        productToUpdate.setTitle(product.getTitle());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setPrice(product.getPrice());

        em.getTransaction().commit();
        em.close();
    }
}
