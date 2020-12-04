package com.sms_activate.respone.rent;

import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.respone.rent.extra.SMSActivateRentNumber;
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
   * @return rent.
   * @throws SMSActivateBaseException if index incorrect.
   */
  @NotNull
  public SMSActivateRentNumber get(int idRent) throws SMSActivateBaseException {
    for (SMSActivateRentNumber activateRentNumber : values.values()) {
      if (activateRentNumber.getId() == idRent) {
        return activateRentNumber;
      }
    }

    throw new SMSActivateBaseException("Rent id is incorrect.", "Некорректный индентификатор аренды.");
  }

  /**
   * Returns the set id rents.
   *
   * @return set id rents.
   */
  @NotNull
  public SortedSet<Integer> getRentSet() {
    SortedSet<Integer> idRentSet = new TreeSet<>();

    for (SMSActivateRentNumber activateRentNumber : values.values()) {
      idRentSet.add(activateRentNumber.getId());
    }

    return idRentSet;
  }

  /**
   * Returns the list current rents.
   *
   * @return the list current rents.
   */
  @NotNull
  public List<SMSActivateRentNumber> getRentNumberList() {
    return new ArrayList<>(values.values());
  }
}
