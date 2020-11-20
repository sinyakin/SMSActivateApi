package com.sms_activate.error;

import com.sms_activate.error.base.SMSActivateBaseException;
import org.jetbrains.annotations.NotNull;

public class SMSActivateBannedException extends SMSActivateBaseException {
  /**
   * End date your bane.
   */
  private final String endDate;

  /**
   * Constructor sms activate banned exception with multilang, endDate.
   * T
   *
   * @param englishMessage message on english language.
   * @param russianMessage message on russian language.
   */
  public SMSActivateBannedException(@NotNull String englishMessage, @NotNull String russianMessage, @NotNull String endDate) {
    super(englishMessage, russianMessage);
    this.endDate = endDate;
  }

  /**
   * Returns the end date of bane.
   *
   * @return end date of bane
   */
  @NotNull
  public String getEndDate() {
    return endDate;
  }

  /**
   * Returns the concatenation messages with date.
   *
   * @return concatenation messages with date
   */
  @Override
  @NotNull
  public String getMessage() {
    String english = String.join(": ", super.getEnglishMessage(), endDate);
    String russian = String.join(": ", super.getRussianMessage(), endDate);
    return String.join("/", english, russian);
  }
}
