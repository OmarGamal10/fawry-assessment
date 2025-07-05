package com.example;

import java.time.LocalDate;

import com.example.behaviour.Expirable;
import com.example.behaviour.NonExpirable;
import com.example.behaviour.NonShippable;
import com.example.behaviour.Shippable;
import com.example.behaviour.ShippingServiceInterface;
import com.example.model.Customer;
import com.example.model.Product;
import com.example.service.CheckoutService;
import com.example.service.ShippingService;

public class App {
    public static void main(String[] args) {
        Customer omar = new Customer("Omar", 1000.0);
        Customer ahmed = new Customer("Ahmed", 500.0);

        // any shipping service provider implementing the interface
        ShippingServiceInterface shippingService = new ShippingService();

        // can have multiple checkout systems, each with its (injected) shipping service
        CheckoutService checkoutSystem = new CheckoutService(shippingService);

        Product TV = new Product("Samsung TV", 500.0, 5, new NonExpirable(), new Shippable(3000.0));
        Product Cheese = new Product("Cheese", 10.0, 10, new Expirable(LocalDate.now().plusDays(7)),
                new Shippable(250.0));
        Product laptop = new Product("Dell Laptop", 1200.0, 3, new NonExpirable(), new Shippable(2000.0));
        Product Biscuits = new Product("Biscuits", 20.0, 50, new Expirable(LocalDate.now().plusDays(90)),
                new Shippable(100.0));
        Product Battery = new Product("Battery", 10.0, 100, new Expirable(LocalDate.now().plusDays(360)),
                new Shippable(50.0));
        Product MobileScratchCard = new Product("Mobile Scratch Card", 5.0, 1000, new NonExpirable(),
                new NonShippable());
        Product expiredFood = new Product("Expired Food", 5.0, 10,
                new Expirable(LocalDate.now().minusDays(1)), new Shippable(100.0));

        // Successful checkout with shippable items
        try {
            omar.addToCart(Battery, 2);
            omar.addToCart(TV, 1);
            omar.addToCart(Cheese, 3);
            checkoutSystem.checkout(omar);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // Successful checkout with non-shippable items
        try {
            omar.addToCart(MobileScratchCard, 12);
            omar.addToCart(Biscuits, 5);
            checkoutSystem.checkout(omar);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Balance not sufficient
        try {
            ahmed.addToCart(laptop, 1);
            checkoutSystem.checkout(ahmed);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // empty cart (cart is cleared after successful checkout)
        try {
            checkoutSystem.checkout(omar);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // expired item
        try {
            omar.addToCart(expiredFood, 1);
            checkoutSystem.checkout(omar);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // item out of stock
        try {
            omar.getCart().clear();
            omar.addToCart(Cheese, 8);
            checkoutSystem.checkout(omar);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
