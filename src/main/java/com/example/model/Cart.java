package com.example.model;

import java.util.HashMap;

public class Cart {
  private HashMap<Product, Integer> products;

  public Cart() {
    this.products = new HashMap<Product, Integer>();
  }

  public void addProduct(Product product, Integer quantity) {
    if (product.getQuantity() == 0) {
      throw new IllegalArgumentException("Product is not available.");
    }
    if (products.containsKey(product) && products.get(product) + quantity > product.getQuantity()) {
      throw new IllegalArgumentException("Cannot add more of this product. [" + product.getName() + "] to the cart.");
    }
    products.put(product, products.getOrDefault(product, 0) + quantity);
  }

  public void removeProduct(Product product) {
    products.remove(product);
  }

  public double getTotalCartPrice() {
    return products.entrySet().stream().mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue()).sum();
  }

  public HashMap<Product, Integer> getProducts() {
    return products;
  }

  public boolean isEmpty() {
    return products.isEmpty();
  }

  public void clear() {
    products.clear();
  }
}
