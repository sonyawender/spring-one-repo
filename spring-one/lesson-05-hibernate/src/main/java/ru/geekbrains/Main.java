package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import javax.persistence.EntityManagerFactory;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        createAndChangeProductTable(emFactory);

        createAndChangeUserTable(emFactory);
    }

    private static void createAndChangeProductTable(EntityManagerFactory emFactory) {
        ProductRepository productRepository = new ProductRepository(emFactory);

        Product product1 = new Product("book", "interesting one", 150);
        Product product2 = new Product("pen", "blue", 10);
        Product product3 = new Product("notebook", "64 pages", 50);
        Product product4 = new Product("pencil", "", 5);

        productRepository.insert(product1);
        productRepository.insert(product2);
        productRepository.insert(product3);
        productRepository.insert(product4);

        System.out.println(productRepository.findAll());

        System.out.println(productRepository.findById(2L));

        productRepository.deleteById(1L);

        System.out.println(productRepository.findAll());

        product4.setPrice(7);
        productRepository.update(product4);

        System.out.println(productRepository.findAll());
    }

    private static void createAndChangeUserTable(EntityManagerFactory emFactory) {
        UserRepository userRepository = new UserRepository(emFactory);

        User user1 = new User("user1", "12345", "user1@test.de");
        User user2 = new User("user2", "11111", "user2@test.de");
        User user3 = new User("user3", "psswrd", "user3@test.de");
        User user4 = new User("user4", "pass", "user4@test.de");

        userRepository.insert(user1);
        userRepository.insert(user2);
        userRepository.insert(user3);
        userRepository.insert(user4);

        System.out.println(userRepository.findAll());

        System.out.println(userRepository.findById(2L));

        userRepository.delete(1L);

        System.out.println(userRepository.findAll());

        user4.setUsername("newNameForUser4");
        userRepository.update(user4);

        System.out.println(userRepository.findAll());

    }
}
