package com.sms_activate.activation;

import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetMultiServiceNumberResponse {
  /**
   * List multi-service activations.
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
   * @throws SMSActivateWrongParameter if id is incorrect.
   */
  @NotNull
  public SMSActivateActivation get(int id) throws SMSActivateWrongParameterException {
    for (SMSActivateActivation activateActivation : this.smsActivateActivationList) {
      if (activateActivation.getId() == id) {
        return activateActivation;
      }
    }

    throw new SMSActivateWrongParameterException(SMSActivateWrongParameter.INVALID_PHONE);
  }

  /**
   * Returns the list multi-service activations.
   *
   * @return list multi-service activations.
   */
  @NotNull
  public List<SMSActivateActivation> getSMSActivateActivationList() {
    return smsActivateActivationList;
  }
}
