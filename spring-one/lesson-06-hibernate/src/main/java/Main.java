import org.hibernate.cfg.Configuration;
import ru.geekbrains.persist.Customer;
import ru.geekbrains.persist.CustomerRepository;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        ProductRepository productRepository = new ProductRepository(emFactory);
        CustomerRepository customerRepository = new CustomerRepository(emFactory);

        fillDatabase(productRepository, customerRepository);

        //Lesson 06. Task 3
        System.out.println("Products bought by Customer 1: \n" + productRepository.findProductsByUserId(1));
        System.out.println("Products bought by Customer 2: \n" + productRepository.findProductsByUserId(2));

    }

    private static void fillDatabase(ProductRepository productRepository, CustomerRepository customerRepository) {

        Product product1 = new Product("book", new BigDecimal("150.0"));
        Product product2 = new Product("pen", new BigDecimal("10.0"));
        Product product3 = new Product("notebook", new BigDecimal("50.0"));
        Product product4 = new Product("pencil", new BigDecimal("5.0"));

        productRepository.insert(product1);
        productRepository.insert(product2);
        productRepository.insert(product3);
        productRepository.insert(product4);

        List<Product> productList1 = new ArrayList<>();
        productList1.add(product1);
        productList1.add(product2);
        productList1.add(product2);
        productList1.add(product3);

        List<Product> productList2 = new ArrayList<>();
        productList2.add(product3);
        productList2.add(product3);
        productList2.add(product4);


        Customer customer1 = new Customer("customer1", productList1);
        Customer customer2 = new Customer("customer2", productList2);

        customerRepository.insert(customer1);
        customerRepository.insert(customer2);
    }
}
