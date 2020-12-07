package com.sms_activate.respone.activation.set_status;

import org.jetbrains.annotations.NotNull;

/**
 * Activation status that You can send to the sms-activate server
 */
//todo почему статусы, которые идут от клиента находятся в пакете respone? подойди обсудим это
public enum SMSActivateClientStatus {
  MESSAGE_WAS_SENT(1, "Сообщить о готовности номера (смс на номер отправлено).", "Inform about the readiness of the number (SMS sent to the number)."),
  REQUEST_ONE_MORE_CODE(3, "Запросить еще один код (бесплатно).", "Request another code (free)."),
  FINISH(6, "Завершить активацию.", "Finish the activation."),
  CANCEL(8, "Сообщить о том, что номер использован и отменить активацию", "Report that the number has been used and cancel activation."),
  ;

  /**
   * Special id status
   */
  private final int id;

  /**
   * Message on russian language.
   */
  private final String russianMessage;

  /**
   * Message on english language.
   */
  private final String englishMessage;

  /**
   * Constructor status activation.
   *
   * @param id             special id status.
   * @param russianMessage description status on russian language.
   * @param englishMessage description status on english language.
   */
  SMSActivateClientStatus(int id, @NotNull String russianMessage, @NotNull String englishMessage) {
    this.id = id;
    this.russianMessage = russianMessage;
    this.englishMessage = englishMessage;
  }

  /**
   * Returns the message on russian.
   *
   * @return message on russian.
   */
  @NotNull
  public String getRussianMessage() {
    return russianMessage;
  }

  /**
   * Returns the message on english.
   *
   * @return message on english.
   */
  @NotNull
  public String getEnglishMessage() {
    return englishMessage;
  }

  /**
   * Returns the single concat messages.
   *
   * @return single concat messages.
   */
  @NotNull
  public String getMessage() {
    return String.join(" | ", englishMessage, russianMessage);
  }

  /**
   * Return the id status
   *
   * @return id status
   */
  public int getId() {
    return id;
  }
}