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
  private long phone;

  /**
   * Short service name.
   */
  private String service;

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

  private SMSActivateCurrentActivation() {
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
  public long getNumber() {
    return phone;
  }

  /**
   * Returns the short service name.
   *
   * @return short service name.
   */
  @NotNull
  public String getServiceName() {
    return service;
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
