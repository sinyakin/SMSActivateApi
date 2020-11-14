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
  private boolean forward = false;

  /**
   * Service for phone number.
   */
  private final Service service;

  /**
   * Constructor Phone with number, service.
   *
   * @param number  number phone (not be null).
   * @param service service for phone number (can be null).
   */
  public Phone(@NotNull String number, @Nullable Service service) {
    this.number = number;
    this.service = service;
  }

  /**
   * Constructor Phone with number, id, forward, service.
   *
   * @param number  number phone (not be null).
   * @param service service for phone number (can be null).
   * @param id      id operation.
   * @param forward forward phone number.
   */
  public Phone(@NotNull String number, @Nullable Service service, int id, boolean forward) {
    this.number = number;
    this.service = service;
    this.id = id;
    this.forward = forward;
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
   * Returns the service for phone number.
   *
   * @return service for phone number (not be null).
   */
  @Nullable
  public Service getService() {
    return service;
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
}
