package com.sms_activate.error.rent;

import org.jetbrains.annotations.NotNull;

public class TimeOutRentException extends RentException {
  /**
   * Constructor time out rent exception with default messages.
   */
  public TimeOutRentException() {
    this("It is impossible to cancel the lease (more than 20 minutes).", "Невозможно отменить аренду (более 20 мин.).");
  }

  /**
   * Constructor sms activate exception with multilang.
   *
   * @param englishMessage message on england language.
   * @param russianMessage message on russian language.
   */
  public TimeOutRentException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage, russianMessage);
  }
}
