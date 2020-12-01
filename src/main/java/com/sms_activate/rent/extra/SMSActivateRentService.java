package com.sms_activate.rent.extra;

import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

public class SMSActivateRentService {
  /**
   * Service short name.
   */
  private final String name;

  /**
   * Cost by service.
   */
  private final BigDecimal cost;

  /**
   * Count number in service.
   */
  private final int count;

  /**
   * Constructor rent service with data.
   *
   * @param name  short name service.
   * @param cost  cost number by service.
   * @param count count number in service.
   */
  public SMSActivateRentService(@NotNull String name, @NotNull BigDecimal cost, int count) {
    this.name = name;
    this.cost = cost;
    this.count = count;
  }

  /**
   * Returns the short service name.
   *
   * @return short service name.
   */
  @NotNull
  public String getName() {
    return name;
  }

  /**
   * Returns the cost number by service.
   *
   * @return cost number by service.
   */
  @NotNull
  public BigDecimal getCost() {
    return cost;
  }

  /**
   * Returns the count number in service.
   *
   * @return count number in service.
   */
  public int getCount() {
    return count;
  }
}
