package com.example.model;

public class Customer {
  private Double balance;
  private String name;
  private final Cart cart;

  public Customer(String name, Double balance) {
    this.name = name;
    this.balance = balance;
    cart = new Cart();
  }

  public String getName() {
    return name;
  }

  public void decreaseBalance(Double amount) {
    if (amount <= 0 || amount > balance) {
      throw new IllegalArgumentException("Cannot decrease balance below zero.");
    }
    this.balance -= amount;
  }

  public void addToCart(Product product, Integer quantity) {
    cart.addProduct(product, quantity);
  }

  public void removeFromCart(Product product) {
    cart.removeProduct(product);
  }

  public Cart getCart() {
    return cart;
  }

  public Double getBalance() {
    return balance;
  }

}
