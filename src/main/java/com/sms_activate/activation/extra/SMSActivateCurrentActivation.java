package com.sms_activate.activation.extra;

import org.jetbrains.annotations.NotNull;

public class SMSActivateCurrentActivation {
  /**
   * Id activation.
   */
  private int id;

  /**
   * Activation is forward.
   */
  private int forward;

  /**
   * Phone number.
   */
  private String number;

  /**
   * Short service name.
   */
  private String serviceName;

  /**
   * Country id.
   */
  private int countryId;

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
    return forward == 1;
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
