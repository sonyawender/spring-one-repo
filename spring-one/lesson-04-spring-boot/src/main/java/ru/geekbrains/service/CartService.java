package ru.geekbrains.service;

import java.util.List;

public interface CartService {

    void putProduct(long userId, long productId, int quantity);

    void removeProduct (long userId, long productId, int quantity);

    void removeAllProducts (long userId);

    List<LineItem> getAllProductsInCart (long userId);
}
