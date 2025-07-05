package com.example.behaviour;

public class NonShippable implements ShippingPolicy {

  @Override
  public double getWeight() {
    return 0.0;
  }

  @Override
  public boolean isShippable() {
    return false;
  }
}
