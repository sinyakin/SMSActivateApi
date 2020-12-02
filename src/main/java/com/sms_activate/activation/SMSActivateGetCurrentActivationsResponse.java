package com.sms_activate.activation;

import com.sms_activate.activation.extra.SMSActivateCurrentActivation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SMSActivateGetCurrentActivationsResponse {
  /**
   * Map current Activation where key is id activation.
   */
  private final Map<Integer, SMSActivateCurrentActivation> smsActivateGetCurrentActivationResponseMap;

  /**
   * True if count activation > 10 else false.
   */
  private final boolean existNext;

  /**
   *
   */
  private final int totalCount;

  /**
   * Constructor response getCurrentActivation with data from server.
   *
   * @param smsActivateGetCurrentActivationResponseMap map current activation where key is idActivation.
   */
  public SMSActivateGetCurrentActivationsResponse(
    @NotNull Map<Integer, SMSActivateCurrentActivation> smsActivateGetCurrentActivationResponseMap,
    boolean existNext,
    int totalCount
  ) {
    this.smsActivateGetCurrentActivationResponseMap = smsActivateGetCurrentActivationResponseMap;
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
   */
  @Nullable
  public SMSActivateCurrentActivation get(int idActivation) {
    return smsActivateGetCurrentActivationResponseMap.get(idActivation);
  }

  /**
   * Returns the all id.
   *
   * @return all id.
   */
  @NotNull
  public Set<Integer> getIdSet() {
    return smsActivateGetCurrentActivationResponseMap.keySet();
  }

  /**
   * Returns the set all current activations.
   *
   * @return set all current activations.
   */
  @NotNull
  public Set<SMSActivateCurrentActivation> getSMSActivateGetCurrentActivationResponseSet() {
    return new HashSet<>(smsActivateGetCurrentActivationResponseMap.values());
  }

  /**
   * Returns the true if activations is exists else false.
   *
   * @return true if activations is exists else false.
   */
  public boolean isExistActivation() {
    return !smsActivateGetCurrentActivationResponseMap.isEmpty();
  }
}
