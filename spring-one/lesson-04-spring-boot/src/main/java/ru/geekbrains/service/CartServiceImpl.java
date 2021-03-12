package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.controller.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CartServiceImpl implements CartService {

    private final Map<Long, Map <LineItem, Integer>> lineItemMap = new ConcurrentHashMap<>();
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public CartServiceImpl(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public void putProduct(long userId, long productId, int quantity) {
        Map<LineItem, Integer> cartOfUser = lineItemMap.computeIfAbsent(userId, k -> new HashMap<>());

        ProductDTO product = productService.findById(productId).orElseThrow(NotFoundException::new);
        UserDTO user = userService.findById(userId).orElseThrow(NotFoundException::new);
        LineItem key = new LineItem(user, product, quantity);

        cartOfUser.merge(key, quantity, Integer::sum);
    }

    @Override
    public void removeProduct(long userId, long productId, int quantity) {
        Map<LineItem, Integer> cartOfUser = lineItemMap.getOrDefault(userId, new HashMap<>());

        if (cartOfUser == null) {
            return;
        }

        LineItem key = new LineItem(userId, productId);
        Integer count = cartOfUser.get(key);
        if (count != null) {
            if (count < quantity) {
                cartOfUser.remove(key);
            } else {
                cartOfUser.put(key, count - quantity);
            }
        }
    }

    @Override
    public void removeAllProducts(long userId) {
        lineItemMap.remove(userId);
    }

    @Override
    public List<LineItem> getAllProductsInCart(long userId) {
        return new ArrayList<>(lineItemMap.get(userId).keySet());
    }
}
