package com.sms_activate.activation;

import org.jetbrains.annotations.NotNull;

public class SMSActivateActivation {
  /**
   * Id activation.
   */
  private final int activation;

  /**
   * Phone number.
   */
  private final String phone;

  /**
   * The name of the service for which the activation was purchased.
   */
  private final String service;

  /**
   * Constructor activation
   *
   * @param id      id activation.
   * @param number  phone number.
   * @param service service name.
   */
  public SMSActivateActivation(
    int id,
    @NotNull String number,
    @NotNull String service
  ) {
    this.activation = id;
    this.phone = number;
    this.service = service;
  }


  /**
   * Returns the id activation.
   *
   * @return id activation.
   */
  public int getId() {
    return activation;
  }

  /**
   * Returns the phone number.
   *
   * @return phone number.
   */
  @NotNull
  public String getNumber() {
    return phone;
  }

  /**
   * Returns the name of the service for which the activation was purchased.
   *
   * @return name of the service for which the activation was purchased.
   */
  @NotNull
  public String getShortName() {
    return service;
  }
}
