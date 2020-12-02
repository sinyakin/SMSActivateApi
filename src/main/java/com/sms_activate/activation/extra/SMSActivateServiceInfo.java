package com.sms_activate.activation.extra;

import org.jetbrains.annotations.NotNull;

public class SMSActivateServiceInfo {
  /**
   * Service support forward.
   */
  private final boolean forward;

  /**
   * Count of service numbers.
   */
  private final int countNumber;

  /**
   * Service short name.
   */
  private final String nameService;

  /**
   * Constructor response getNumbersStatus with data from server.
   *
   * @param forward     service support forward.
   * @param countNumber count of service numbers.
   * @param nameService service short name.
   */
  public SMSActivateServiceInfo(
    boolean forward,
    int countNumber,
    @NotNull String nameService
  ) {
    this.forward = forward;
    this.countNumber = countNumber;
    this.nameService = nameService;
  }

  /**
   * Returns the support service forward.
   *
   * @return support service forward.
   */
  public boolean isForward() {
    return forward;
  }


  /**
   * Returns the count of service numbers.
   *
   * @return count of service numbers.
   */
  public int getCountNumber() {
    return countNumber;
  }

  /**
   * Returns the short name of service.
   *
   * @return short name of service.
   */
  @NotNull
  public String getShortName() {
    return nameService;
  }
}
