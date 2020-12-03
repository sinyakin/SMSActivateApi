package com.sms_activate.rent;

import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.rent.extra.SMSActivateRentNumber;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SMSActivateGetRentListResponse {
  /**
   * Map current rents where key is id rent.
   */
  private Map<Integer, SMSActivateRentNumber> values;

  private SMSActivateGetRentListResponse() {
  }

  /**
   * Returns the rent by index.
   *
   * @param idRent index rent.
   * @return rent object.
   * @throws SMSActivateBaseException if index incorrect.
   */
  @NotNull
  public SMSActivateRentNumber get(int idRent) throws SMSActivateBaseException {
    for (SMSActivateRentNumber activateRentNumber : values.values()) {
      if (activateRentNumber.getId() == idRent) {
        return activateRentNumber;
      }
    }

    throw new SMSActivateBaseException("Rent id is incorrect.", "Некорректный индентификатор аренды");
  }

  /**
   * Returns the set ids rent.
   *
   * @return set ids rent.
   */
  @NotNull
  public SortedSet<Integer> getIdSet() {
    SortedSet<Integer> idSet = new TreeSet<>();

    for (SMSActivateRentNumber activateRentNumber : values.values()) {
      idSet.add(activateRentNumber.getId());
    }

    return idSet;
  }

  /**
   * Returns the list current rents.
   *
   * @return the list current rents.
   */
  @NotNull
  public List<SMSActivateRentNumber> getSmsActivateGetRentResponseList() {
    return new ArrayList<>(values.values());
  }
}
