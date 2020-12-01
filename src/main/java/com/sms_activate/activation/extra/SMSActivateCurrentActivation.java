package com.sms_activate.activation.extra;

import org.jetbrains.annotations.NotNull;

public class SMSActivateCurrentActivation {
  /**
   * Id activation.
   */
  private final int id;

  /**
   * Activation is forward.
   */
  private final boolean forward;

  /**
   * Phone number.
   */
  private final String number;

  /**
   * Short service name.
   */
  private final String serviceName;

  /**
   * Country id.
   */
  private final int countryId;

  /**
   * Constructor activation with info.
   *
   * @param id          id activation.
   * @param forward     is forward acitvation.
   * @param number      phone number.
   * @param countryId   country id.
   * @param serviceName short service name.
   */
  public SMSActivateCurrentActivation(
    int id,
    boolean forward,
    @NotNull String number,
    int countryId,
    @NotNull String serviceName
  ) {
    this.id = id;
    this.forward = forward;
    this.number = number;
    this.serviceName = serviceName;
    this.countryId = countryId;
  }

  /**
   * Returns the id activation.
   *
   * @return id activation.
   */
  public int getId() {
    return id;
  }

  /**
   * Returns the true if activation is forwarding else false.
   *
   * @return true if activation is forwarding else false.
   */
  public boolean isForward() {
    return forward;
  }

  /**
   * Returns the phone number.
   *
   * @return phone number.
   */
  @NotNull
  public String getNumber() {
    return number;
  }

  /**
   * Returns the short service name.
   *
   * @return short service name.
   */
  @NotNull
  public String getServiceName() {
    return serviceName;
  }

  /**
   * Returns the country id.
   *
   * @return country id.
   */
  public int getCountryId() {
    return countryId;
  }
}
