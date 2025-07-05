package com.example.service;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.example.behaviour.ShippingServiceInterface;
import com.example.model.Cart;
import com.example.model.Customer;
import com.example.model.Product;
import com.example.model.ShippingItem;

public class CheckoutService {

  private final ShippingServiceInterface shippingService;

  public CheckoutService(ShippingServiceInterface shippingService) {
    this.shippingService = shippingService;
  }

  private List<SimpleEntry<ShippingItem, Integer>> prepareShippingItems(Cart cart) {
    return cart.getProducts().entrySet().stream().filter(entry -> entry.getKey().isShippable())
        .map(entry -> new SimpleEntry<>(new ShippingItem(entry.getKey().getName(), entry.getKey().getWeight()),
            entry.getValue()))
        .collect(Collectors.toList());
  }

  Double getTotalCartPrice(Cart cart) {
    return cart.getProducts().entrySet().stream().mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
        .sum();
  }

  private List<Product> checkExpiry(Cart cart) {
    return cart.getProducts().keySet().stream()
        .filter(product -> product.isExpirable() && product.isExpired())
        .collect(Collectors.toList());
  }

  private List<Product> checkOutOfStock(Cart cart) {
    return cart.getProducts().entrySet().stream()
        .filter(entry -> entry.getKey().getQuantity() < entry.getValue())
        .map(entry -> entry.getKey())
        .collect(Collectors.toList());
  }

  public void checkout(Customer customer) {

    Cart cart = customer.getCart();
    Double shippingFees = 0.0;
    Double subTotal = 0.0;
    Double total = 0.0;

    if (cart.isEmpty()) {
      throw new IllegalArgumentException("Cart is empty.");
    }

    List<Product> outOfStockProducts = checkOutOfStock(cart);
    if (!outOfStockProducts.isEmpty()) {
      String outOfStockProductNames = outOfStockProducts.stream()
          .map(Product::getName)
          .collect(Collectors.joining(", "));
      throw new IllegalArgumentException("The following products are out of stock: " + outOfStockProductNames);
    }

    List<Product> expiredProducts = checkExpiry(cart);
    if (!expiredProducts.isEmpty()) {
      String expiredProductNames = expiredProducts.stream()
          .map(Product::getName)
          .collect(Collectors.joining(", "));
      throw new IllegalArgumentException("The following products are expired: " + expiredProductNames);
    }

    subTotal = getTotalCartPrice(cart);
    List<SimpleEntry<ShippingItem, Integer>> shippingItems = prepareShippingItems(cart);
    if (!shippingItems.isEmpty()) {
      shippingFees = shippingService.getShippingFees(shippingItems);
    }
    total = subTotal + shippingFees;
    if (total > customer.getBalance()) {
      throw new IllegalArgumentException("Insufficient balance for checkout.");
    }
    shippingService.ship(shippingItems);
    customer.decreaseBalance(total);
    decrementProductQuantities(cart);
    logCheckoutDetails(cart, shippingItems, subTotal, shippingFees, total);
    cart.clear();
  }

  private void decrementProductQuantities(Cart cart) {
    for (Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
      Product product = entry.getKey();
      int quantity = entry.getValue();
      product.decreaseQuantity(quantity);
    }
  }

  private void logCheckoutDetails(Cart cart, List<SimpleEntry<ShippingItem, Integer>> shippingItems,
      Double subTotal, Double shippingFees, Double total) {
    StringBuilder log = new StringBuilder();
    if (shippingFees != 0) {
      log.append("** Shipment notice **\n");
      for (SimpleEntry<ShippingItem, Integer> entry : shippingItems) {
        log.append(entry.getValue()).append("x ").append(entry.getKey().getName()).append("\n");
      }
      double totalWeight = shippingItems.stream()
          .mapToDouble(entry -> entry.getKey().getWeight() * entry.getValue()).sum();
      if (totalWeight > 1000) {
        totalWeight /= 1000;
        log.append("Total package weight ").append(totalWeight).append("kg\n");
      } else {
        log.append("Total package weight ").append(totalWeight).append("g\n");
      }
    }
    log.append("** Checkout receipt **\n");
    for (Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
      log.append(entry.getValue()).append("x ").append(entry.getKey().getName()).append(" ")
          .append(entry.getKey().getPrice()).append("\n");
    }
    log.append("----------------------\n");
    log.append("Subtotal ").append(subTotal).append("\n");
    log.append("Shipping ").append(shippingFees).append("\n");
    log.append("Amount ").append(total).append("\n");

    System.out.println(log.toString());
  }
}
