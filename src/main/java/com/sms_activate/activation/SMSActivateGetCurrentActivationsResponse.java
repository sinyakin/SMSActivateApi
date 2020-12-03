package com.sms_activate.activation;

import com.sms_activate.activation.extra.SMSActivateCurrentActivation;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetCurrentActivationsResponse {
  /**
   * Map current Activation where key is id activation.
   */
  private List<SMSActivateCurrentActivation> data;

  /**
   * Mark to re-request.
   */
  private boolean exitNext;

  /**
   * Total count activations.
   */
  private int quant;

  private SMSActivateGetCurrentActivationsResponse() {
  }

  /**
   * Returns the total count activations.
   *
   * @return total count activations.
   */
  public int getTotalCount() {
    return quant;
  }

  /**
   * Returns the true if you can send request again else false.
   *
   * @return true if you can send request again else false.
   */
  public boolean isExistNextBatch() {
    return exitNext;
  }

  /**
   * Returns the current activation by id.
   *
   * @param idActivation id activation
   * @return current activation.
   * @throws SMSActivateWrongParameterException if index is incorrect.
   */
  @NotNull
  public SMSActivateCurrentActivation get(int idActivation) throws SMSActivateWrongParameterException {
    for (SMSActivateCurrentActivation activateCurrentActivation : this.data) {
      if (activateCurrentActivation.getId() == idActivation) {
        return activateCurrentActivation;
      }
    }

    throw new SMSActivateWrongParameterException("Index exceeds the number of your activations.", "Индекс превышает число ваших активаций.");
  }

  /**
   * Returns the all current activations.
   *
   * @return all current activations
   */
  @NotNull
  public List<SMSActivateCurrentActivation> getCurrentActivationList() {
    return this.data;
  }

  /**
   * Returns the true if activations is exists else false.
   *
   * @return true if activations is exists else false.
   */
  public boolean isExistActivation() {
    return !data.isEmpty();
  }
}
