package com.sms_activate.error;

import org.jetbrains.annotations.NotNull;

public class BaseSMSActivateException extends Exception {
  /**
   * Message on england language.
   */
  private final String englishMessage;

  /**
   * Message on russian language.
   */
  private final String russianMessage;

  /**
   * Constructor base sms activate exception with sublang.
   *
   * @param englishMessage message on england language.
   * @param russianMessage message on russian language.
   */
  public BaseSMSActivateException(@NotNull String englishMessage, @NotNull String russianMessage) {
    super(englishMessage);

    this.englishMessage = englishMessage;
    this.russianMessage = russianMessage;
  }

  /**
   * Returns the message on england language.
   *
   * @return message on england language.
   */
  @NotNull
  public String getEnglishMessage() {
    return englishMessage;
  }

  /**
   * Returns the message on russian language.
   *
   * @return message on russian language.
   */
  @NotNull
  public String getRussianMessage() {
    return russianMessage;
  }

  /**
   * Returns the concat messages.
   *
   * @return concat messages.
   */
  @Override
  @NotNull
  public String getMessage() {
    return String.join(" / ", englishMessage, russianMessage);
  }
}
