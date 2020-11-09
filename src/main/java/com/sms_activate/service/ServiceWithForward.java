package com.sms_activate.service;

import org.jetbrains.annotations.NotNull;

public class ServiceWithForward extends Service {
  /**
   * Supported service forward.
   */
  private final boolean forward;

  /**
   * Constructor service with support forward, short name of service and count number in service.
   *
   * @param shortName   short name of service (not be null).
   * @param countNumber count number in service.
   * @param forward     service forward.
   */
  public ServiceWithForward(@NotNull String shortName, int countNumber, boolean forward) {
    super(shortName, countNumber);
    this.forward = forward;
  }

  /**
   * Returns the support service forward.
   *
   * @return support service forward.
   */
  public boolean isForward() {
    return forward;
  }
}
