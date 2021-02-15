package ru.geekbrains.persist;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class UserRepository {

    private final EntityManagerFactory emFactory;

    public UserRepository(EntityManagerFactory emFactory) {
        this.emFactory = emFactory;
    }

    public List<User> findAll() {
        EntityManager em = emFactory.createEntityManager();;
        return em.createQuery("from User", User.class)
                .getResultList();
    }

    public User findById(long id) {
        EntityManager em = emFactory.createEntityManager();;
        return em.find(User.class, id);
    }

    public void insert(User user) {
        EntityManager em = emFactory.createEntityManager();;
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
        em.close();
    }

    public void update(User user) {
        EntityManager em = emFactory.createEntityManager();
        User userToUpdate = em.find(User.class, user.getId());

        em.getTransaction().begin();

        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPassword(user.getPassword());

        em.getTransaction().commit();
        em.close();
    }

    public void delete(long id) {
        EntityManager em = emFactory.createEntityManager();;
        User user = em.find(User.class, id);
        if (user != null) {
            em.getTransaction().begin();
            em.remove(user);
            em.getTransaction().commit();
            em.close();
        } else {
            System.out.println("User doesn't exist. Try another ID.");
        }
    }

}
