package com.example.behaviour;

import java.time.LocalDate;

public interface ExpiryPolicy {
  boolean isExpired();

  LocalDate getExpiryDate();

  boolean isExpirable();
}
