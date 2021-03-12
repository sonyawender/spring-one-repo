package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.service.CartService;
import ru.geekbrains.service.LineItem;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartResource {

    private final CartService cartService;

    @Autowired
    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/user/{userId}/product/{productId}")
    public void putProduct(@PathVariable("userId") Long userId,
                           @PathVariable("productId") Long productId,
                           @RequestParam("quantity") Integer quantity) {
        cartService.putProduct(userId, productId, quantity);
    }

    @PostMapping("/remove/user/{userId}/product/{productId}")
    public void removeProduct(@PathVariable("userId") Long userId,
                              @PathVariable("productId") Long productId,
                              @RequestParam("quantity") Integer quantity) {
        cartService.removeProduct(userId, productId, quantity);
    }

    @DeleteMapping("/remove/user/{userId}")
    public void removeAllProducts(@PathVariable("userId") Long userId) {
        cartService.removeAllProducts(userId);
    }

    @GetMapping("/user/{userId}")
    public List<LineItem> getAllProductsInCart(@PathVariable("userId") Long userId) {
        return cartService.getAllProductsInCart(userId);
    }
}
