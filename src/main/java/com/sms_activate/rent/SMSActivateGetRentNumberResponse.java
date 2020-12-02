package com.sms_activate.rent;

import org.jetbrains.annotations.NotNull;

public class SMSActivateGetRentNumberResponse {
  /**
   * Id rent.
   */
  private final int id;

  /**
   * Phone number.
   */
  private final String number;

  /**
   * End date of rent.
   */
  private final String endDate;

  /**
   * Service short name.
   */
  private final String serviceName;

  /**
   * Constructor response getRentNumber with data from server.
   *
   * @param id          id rent.
   * @param number      phone number.
   * @param endDate     end date of rent.
   * @param serviceName service short name.
   */
  public SMSActivateGetRentNumberResponse(int id, @NotNull String number, @NotNull String endDate, @NotNull String serviceName) {
    this.id = id;
    this.number = number;
    this.endDate = endDate;
    this.serviceName = serviceName;
  }

  /**
   * Returns the id rent.
   *
   * @return id rent.
   */
  public int getId() {
    return id;
  }

  /**
   * Returns the phone number.
   *
   * @return phone number
   */
  @NotNull
  public String getNumber() {
    return number;
  }

  /**
   * Returns the end date of rent.
   *
   * @return end date of rent.
   */
  @NotNull
  public String getEndDate() {
    return endDate;
  }

  /**
   * Returns the service short name.
   *
   * @return service short name.
   */
  @NotNull
  public String getServiceName() {
    return serviceName;
  }
}
