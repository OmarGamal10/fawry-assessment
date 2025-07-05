package com.example.model;

public record ShippingItem(String name, double weight) {
  public String getName() {
    return name;
  }

  public double getWeight() {
    return weight;
  }
}
