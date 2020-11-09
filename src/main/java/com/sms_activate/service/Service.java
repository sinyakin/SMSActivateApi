package com.sms_activate.service;

import org.jetbrains.annotations.NotNull;

public class Service {
  /**
   * Short name of service
   */
  private final String shortName;

  /**
   * Count number in service.
   */
  private final int countNumber;

  /**
   * Constructor service with short name of service.
   *
   * @param shortName short name of service.
   */
  public Service(@NotNull String shortName) {
    this(shortName, 0);
  }

  /**
   * Constructor service with short name of service and count number in service.
   *
   * @param shortName   short name of service.
   * @param countNumber count number in service.
   */
  public Service(@NotNull String shortName, int countNumber) {
    this.shortName = shortName;
    this.countNumber = countNumber;
  }

  /**
   * Returns the short name of service.
   *
   * @return short name of service.
   */
  @NotNull
  public String getShortName() {
    return shortName;
  }

  /**
   * Returns the count number in service.
   *
   * @return count number in service.
   */
  public int getCountNumber() {
    return countNumber;
  }
}
