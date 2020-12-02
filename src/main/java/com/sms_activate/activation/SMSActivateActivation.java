package com.sms_activate.activation;

import org.jetbrains.annotations.NotNull;

public class SMSActivateActivation {
  /**
   * Id activation.
   */
  private final int id;

  /**
   * Is forward number.
   */
  private final boolean forward;

  /**
   * Phone number.
   */
  private final String number;

  /**
   * The name of the service for which the activation was purchased.
   */
  private final String serviceName;

  /**
   * Constructor activation
   *
   *
   * @param id          id activation.
   * @param number      phone number.
   * @param serviceName name of the service for which the activation was purchased.
   * @param forward     is forward.
   */
  public SMSActivateActivation(
    int id,
    @NotNull String number,
    @NotNull String serviceName,
    boolean forward
  ) {
    this.id = id;
    this.number = number;
    this.serviceName = serviceName;
    this.forward = forward;
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
   * Returns the is forward activation.
   *
   * @return is forward activation.
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
   * Returns the name of the service for which the activation was purchased.
   *
   * @return name of the service for which the activation was purchased.
   */
  @NotNull
  public String getShortName() {
    return serviceName;
  }
}
