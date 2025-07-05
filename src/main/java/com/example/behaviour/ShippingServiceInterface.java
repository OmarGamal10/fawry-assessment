package com.example.behaviour;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import com.example.model.ShippingItem;

public interface ShippingServiceInterface {
  double getShippingFees(List<SimpleEntry<ShippingItem, Integer>> shippingItems);

  void ship(List<SimpleEntry<ShippingItem, Integer>> shippingItems);
}
