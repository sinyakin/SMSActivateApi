package com.sms_activate;

import com.sms_activate.activation.extra.SMSActivateCurrentActivation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

class SMSActivateGetCurrentActivationTempResponse {
  /**
   * Status request.
   */
  private String status;

  /**
   * Message about error.
   */
  private String msg;

  /**
   * List current activations.
   */
  private List<SMSActivateCurrentActivation> array;

  /**
   * Total count activations.
   */
  private int quant;

  /**
   * Returns the total count current activations.
   *
   * @return total count current activations
   */
  public int getQuant() {
    return quant;
  }

  /**
   * Returns the list current activations.
   *
   * @return list current activations.
   */
  @NotNull
  public List<SMSActivateCurrentActivation> getArray() {
    return array;
  }

  /**
   * Returns the status request.
   *
   * @return status request.
   */
  @NotNull
  public String getStatus() {
    return status;
  }

  /**
   * Returns the message about error.
   *
   * @return message about error.
   */
  @NotNull
  public String getMessage() {
    return msg;
  }
}
