package com.sms_activate.activation.current_activation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SMSActivateGetCurrentActivationsResponse {
  /**
   * Map currectActivation where key is id activation.
   */
  private final Map<Integer, SMSActivateGetCurrentActivationResponse> smsActivateGetCurrentActivationResponseMap;

  private boolean existNext;

  /**
   * Constructor response getCurrentActivation with data..
   *
   * @param smsActivateGetCurrentActivationResponseMap map currect activation where key is idActivation.
   */
  public SMSActivateGetCurrentActivationsResponse(@NotNull Map<Integer, SMSActivateGetCurrentActivationResponse> smsActivateGetCurrentActivationResponseMap, boolean existNext) {
    this.smsActivateGetCurrentActivationResponseMap = smsActivateGetCurrentActivationResponseMap;
    this.existNext = existNext;
  }

  /**
   * Returns the true if you can send request again else false.
   *
   * @return true if you can send request again else false.
   */
  public boolean isExistNext() {
    return existNext;
  }

  /**
   * Returns the current activation by id.
   *
   * @return current activation.
   */
  @Nullable
  public SMSActivateGetCurrentActivationResponse get(int idActivation) {
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
  public Set<SMSActivateGetCurrentActivationResponse> getSMSActivateGetCurrentActivationResponseSet() {
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
