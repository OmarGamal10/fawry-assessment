package com.example.behaviour;

public class Shippable implements ShippingPolicy {
  private final double weight;

  // in grams
  public Shippable(double weight) {
    this.weight = weight;
  }

  @Override
  public boolean isShippable() {
    return true;
  }

  @Override
  public double getWeight() {
    return weight;
  }
}
