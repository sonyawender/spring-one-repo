package ru.geekbrains.service;

import java.util.Objects;

public class LineItem {

    private ProductDTO product;
    private UserDTO user;
    private Integer quantity;

    public LineItem() {
    }

    public LineItem(long userId, long productId) {
        this.product = new ProductDTO();
        this.product.setId(productId);
        this.user = new UserDTO();
        this.user.setId(userId);
    }

    public LineItem(UserDTO user, ProductDTO product, Integer quantity) {
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineItem lineItem = (LineItem) o;
        return product.getId().equals(lineItem.product.getId()) &&
                user.getId().equals(lineItem.user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(product.getId(), user.getId());
    }
}
