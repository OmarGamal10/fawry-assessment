package com.example.model;

import com.example.behaviour.ExpiryPolicy;
import com.example.behaviour.ShippingPolicy;

public class Product {
  private final ExpiryPolicy expiryPolicy;
  private final ShippingPolicy shippingPolicy;

  private String name;
  private double price;
  private Integer quantity;

  public Product(String name, double price, Integer quantity, ExpiryPolicy expiryPolicy,
      ShippingPolicy shippingPolicy) {
    this.name = name;
    this.price = price;
    this.quantity = quantity;
    this.expiryPolicy = expiryPolicy;
    this.shippingPolicy = shippingPolicy;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void decreaseQuantity(Integer quantity) {
    if (quantity == null || quantity <= 0 || quantity > this.quantity) {
      throw new IllegalArgumentException("Cannot decrease quantity below zero.");
    }
    this.quantity -= quantity;
  }

  public boolean isExpirable() {
    return expiryPolicy.getExpiryDate() != null;
  }

  public boolean isExpired() {
    return expiryPolicy.isExpired();
  }

  public boolean isShippable() {
    return shippingPolicy.isShippable();
  }

  public double getWeight() {
    return shippingPolicy.getWeight();
  }
}