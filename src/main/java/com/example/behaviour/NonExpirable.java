package com.example.behaviour;

import java.time.LocalDate;

public class NonExpirable implements ExpiryPolicy {

  @Override
  public boolean isExpired() {
    return false;
  }

  @Override
  public LocalDate getExpiryDate() {
    return null;
  }

  @Override
  public boolean isExpirable() {
    return false;
  }
}
