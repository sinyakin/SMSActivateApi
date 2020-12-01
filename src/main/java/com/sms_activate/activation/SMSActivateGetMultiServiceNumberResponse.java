package com.sms_activate.activation;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetMultiServiceNumberResponse {
  /**
   * List multiservice activations.
   */
  private final List<SMSActivateActivation> smsActivateActivationList;

  /**
   * Constructor the getMultiServiceNumber response with activations.
   *
   * @param smsActivateActivationList list activations.
   */
  public SMSActivateGetMultiServiceNumberResponse(@NotNull List<SMSActivateActivation> smsActivateActivationList) {
    this.smsActivateActivationList = smsActivateActivationList;
  }

  /**
   * Returns the activations by specified Id.
   *
   * @param id id activation.
   * @return activation.
   */
  @NotNull
  public SMSActivateActivation get(int id) {
    return this.smsActivateActivationList.get(id);
  }

  /**
   * Returns the list multiservice activations.
   *
   * @return list multiservice activations.
   */
  @NotNull
  public List<SMSActivateActivation> getSMSActivateActivationList() {
    return smsActivateActivationList;
  }
}
