package com.example.service;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import com.example.behaviour.ShippingServiceInterface;
import com.example.model.ShippingItem;

// this is an example shipping service
//any shipping service provider would implement this interface with their own policies
public class ShippingService implements ShippingServiceInterface {

  @Override
  public double getShippingFees(List<SimpleEntry<ShippingItem, Integer>> shippingItems) {
    double baseCost = 5.0; // base shipping cost
    double costPerKg = 2.0; // cost per kg of weight
    double totalWeight = shippingItems.stream().mapToDouble(entry -> {
      ShippingItem item = entry.getKey();
      int quantity = entry.getValue();
      double itemWeight = item.getWeight() / 1000.0;
      return itemWeight * quantity;
    }).sum();
    return totalWeight * costPerKg + baseCost;
  }

  @Override
  public void ship(List<SimpleEntry<ShippingItem, Integer>> shippingItems) {
    for (SimpleEntry<ShippingItem, Integer> entry : shippingItems) {
      System.out.println("Shipping item: " + entry.getKey().getName() +
          ", Quantity: " + entry.getValue());
    }
  }

}
