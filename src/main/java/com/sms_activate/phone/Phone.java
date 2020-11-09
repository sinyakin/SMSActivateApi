package com.sms_activate.phone;

import com.sms_activate.service.Service;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Phone {
  /**
   * Phone number
   */
  private final String number;

  /**
   * Id phone
   */
  private int id;

  /**
   * Forward phone number
   */
  private boolean forward;

  /**
   * Service for phone number.
   */
  private final Service service;

  /**
   * Constructor Phone with number, service.
   *
   * @param number  number phone (not be null).
   * @param service service for phone number (not be null).
   */
  public Phone(@NotNull String number, @NotNull Service service) {
    this.number = number;
    this.service = service;
  }

  /**
   * Constructor Phone with number, id, forward, service.
   *
   * @param number  number phone (not be null).
   * @param id      id operation.
   * @param forward forward phone number (not be null).
   * @param service service for phone number (not be null).
   */
  public Phone(@NotNull String number, int id, boolean forward, @Nullable Service service) {
    this.number = number;
    this.id = id;
    this.forward = forward;
    this.service = service;
  }

  /**
   * Returns the phone number.
   *
   * @return phone number (not be null).
   */
  @NotNull
  public String getNumber() {
    return number;
  }

  /**
   * Returns the id operation.
   *
   * @return id operation.
   */
  public int getId() {
    return id;
  }

  /**
   * Returns the phone is forward.
   *
   * @return phone is forward.
   */
  public boolean isForward() {
    return forward;
  }

  /**
   * Returns the service for phone number.
   *
   * @return service for phone number (not be null).
   */
  @Nullable
  public Service getService() {
    return service;
  }
}
