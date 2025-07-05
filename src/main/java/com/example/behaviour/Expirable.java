package com.example.behaviour;

import java.time.LocalDate;

public class Expirable implements ExpiryPolicy {
  private final LocalDate expiryDate;

  public Expirable(LocalDate expiryDate) {
    this.expiryDate = expiryDate;
  }

  @Override
  public boolean isExpirable() {
    return true;
  }

  @Override
  public boolean isExpired() {
    return expiryDate != null && LocalDate.now().isAfter(expiryDate);
  }

  @Override
  public LocalDate getExpiryDate() {
    return expiryDate;
  }
}
