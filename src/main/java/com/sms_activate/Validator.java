package com.sms_activate;

import com.sms_activate.error.NoBalanceException;
import com.sms_activate.error.NoNumberException;
import com.sms_activate.error.common.SQLServerException;
import com.sms_activate.error.common.WrongParameterException;
import com.sms_activate.error.rent.RentException;
import com.sms_activate.error.rent.TimeOutRentException;
import com.sms_activate.error.type.RentError;
import com.sms_activate.error.type.Shortage;
import com.sms_activate.error.type.WrongParameter;
import com.sms_activate.rent.StateRentResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

class Validator {
  /**
   * Throws WrongParameterException if name contains in wrong parameter.
   *
   * @param name name parameter.
   * @throws WrongParameterException if one of parameters is incorrect.
   */
  public void throwWrongParameterExceptionByName(@NotNull String name) throws WrongParameterException {
    WrongParameter wrongParameter = WrongParameter.getWrongParameterByName(name);

    if (wrongParameter != WrongParameter.UNKNOWN) {
      throw new WrongParameterException(wrongParameter.getEnglishMessage(), wrongParameter.getRussianMessage());
    }
  }

  /**
   * Throw NoNumbersException or NoBalanceException
   *
   * @param name name exception (no_numbers or no_balance).
   * @throws NoBalanceException if no numbers.
   * @throws NoNumberException  if in account balance is zero.
   */
  public void throwNoNumbersOrNoBalanceExceptionByName(@NotNull String name) throws NoNumberException, NoBalanceException {
    Shortage shortage = Shortage.getErrorByName(name);

    if (shortage == Shortage.NO_NUMBERS || shortage == Shortage.ACCOUNT_INACTIVE) {
      throw new NoNumberException();
    } else if (shortage == Shortage.NO_BALANCE) {
      throw new NoBalanceException();
    }
  }

  /**
   * Throw error by name.
   *
   * @param name name error.
   *             unknown if wrong parameter not contains in enum (it's may be SQLServerException), else throw WrongParameter.
   * @throws WrongParameterException if one of parameters is incorrect.
   * @throws SQLServerException      if error happened on SQL-server.
   */
  public void throwCommonExceptionByName(@NotNull String name)
      throws WrongParameterException, SQLServerException {
    throwWrongParameterExceptionByName(name);

    if (name.contains("SQL")) {
      throw new SQLServerException();
    }
  }

  /**
   * Throws rent error by name.
   *
   * @param name name error
   * @throws RentException        if rent is cancel or finish.
   * @throws TimeOutRentException if rent has been canceled, finish or it is no longer possible to cancel the rental.
   */
  public void throwRentExceptionsByName(@NotNull String name) throws RentException, TimeOutRentException {
    RentError rentError = RentError.getErroByName(name);

    if (rentError == RentError.CANT_CANCEL || rentError == RentError.ALREADY_CANCEL || rentError == RentError.ALREADY_FINISH) {
      throw new TimeOutRentException(rentError.getEnglishMessage(), rentError.getRussianMessage());
    } else {
      throw new RentException(rentError.getEnglishMessage(), rentError.getRussianMessage());
    }
  }

  /**
   * Validate rent status on error.
   *
   * @param status  status rent response.
   * @param message message on error.
   * @throws RentException if rent is cancel or finish.
   */
  public void validateRentStateResponse(@NotNull Object status, @Nullable Object message) throws RentException {
    StateRentResponse stateRentResponse = StateRentResponse.getStateRentByName(status.toString());

    if (stateRentResponse != StateRentResponse.SUCCESS) {
      this.throwRentExceptionsByName(String.valueOf(message));
    }
  }
}
