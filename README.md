# Fawry Assessment

## Running the Application

- Ensure Maven is installed on your system.
- Compile and execute the application using the following commands:

```bash
mvn compile
mvn -q exec:java -Dexec.mainClass='com.example.App'
```

## Design Decisions

- **Policies/Strategies**: Product categories are independent of behaviors `such as expirability, shippability, etc`. To address this, `Policies` are injected per product, with each behavior assigned a specific policy. This approach prevents a combinatorial explosion of classes when introducing new behaviors (e.g., discounts or returnability) and decouples products from their behaviors.
- **Dependency Injection**: The `ShippingService` module implements the `ShippingServiceInterface` and is injected into the` CheckoutService`. This design allows for easy swapping of implementations or support for multiple shipping service providers.
- **Stock Management**: Quantities are decremented during checkout to prevent customers from reserving items in their carts indefinitely. Quantity validations occur at two levels:
  - At the cart level, to ensure the requested quantity is less than or equal to the available stock.
  - At the checkout stage, to verify that the stock is still available.
