package com.sms_activate.activation;

import com.sms_activate.activation.extra.SMSActivateCurrentActivation;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SMSActivateGetCurrentActivationsResponse {
  /**
   * Map current Activation where key is id activation.
   */
  private final List<SMSActivateCurrentActivation> smsActivateCurrentActivationList;

  /**
   * Mark to re-request.
   */
  private final boolean existNext;

  /**
   * Total count activations.
   */
  private final int totalCount;

  /**
   * Constructor response getCurrentActivation with data from server.
   *
   * @param smsActivateCurrentActivationList map current activation where key is idActivation.
   * @param totalCount                       total count activations.
   * @param existNext                        mark to re-request.
   */
  public SMSActivateGetCurrentActivationsResponse(
    @NotNull List<SMSActivateCurrentActivation> smsActivateCurrentActivationList,
    boolean existNext,
    int totalCount
  ) {
    this.smsActivateCurrentActivationList = smsActivateCurrentActivationList;
    this.existNext = existNext;
    this.totalCount = totalCount;
  }

  /**
   * Returns the total count activations.
   *
   * @return total count activations.
   */
  public int getTotalCount() {
    return totalCount;
  }

  /**
   * Returns the true if you can send request again else false.
   *
   * @return true if you can send request again else false.
   */
  public boolean isExistNextBatch() {
    return existNext;
  }

  /**
   * Returns the current activation by id.
   *
   * @return current activation.
   * @throws SMSActivateWrongParameterException if index is incorrect.
   */
  @NotNull
  public SMSActivateCurrentActivation get(int index) throws SMSActivateWrongParameterException {
    if (index >= smsActivateCurrentActivationList.size()) {
      throw new SMSActivateWrongParameterException("Index exceeds the number of your activations.", "Индекс превышает число ваших активаций.");
    }

    return smsActivateCurrentActivationList.get(index);
  }

  /**
   * Returns the all current activations.
   *
   * @return all current activations
   */
  @NotNull
  public List<SMSActivateCurrentActivation> getAllActivation() {
    return this.smsActivateCurrentActivationList;
  }

  /**
   * Returns the true if activations is exists else false.
   *
   * @return true if activations is exists else false.
   */
  public boolean isExistActivation() {
    return !smsActivateCurrentActivationList.isEmpty();
  }
}
