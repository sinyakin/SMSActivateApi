package com.sms_activate;

import com.sms_activate.error.*;
import com.sms_activate.error.rent.ErrorRent;
import com.sms_activate.error.rent.RentException;
import com.sms_activate.error.rent.TimeOutRentException;
import org.jetbrains.annotations.NotNull;

class Validator {
  /**
   * Throws the error by type.
   *
   * @param errorRent error type.
   * @throws SQLServerException if error happened on SQL-server.
   * @throws RentException      if rent is cancel or finish.
   * @throws NoBalanceException if no numbers.
   * @throws NoNumberException  if in account balance is zero.
   */
  public static void throwStateErrorRent(@NotNull ErrorRent errorRent)
      throws RentException, SQLServerException, NoBalanceException, NoNumberException {
    switch (errorRent) {
      case ALREADY_FINISH:
        throw new RentException(
            ErrorRent.ALREADY_FINISH.getEnglishMessage(),
            ErrorRent.ALREADY_FINISH.getRussianMessage()
        );
      case ALREADY_CANCEL:
        throw new RentException(
            ErrorRent.ALREADY_CANCEL.getEnglishMessage(),
            ErrorRent.ALREADY_CANCEL.getRussianMessage()
        );
      case NO_ID_RENT:
        throw new RentException(
            ErrorRent.NO_ID_RENT.getEnglishMessage(),
            ErrorRent.NO_ID_RENT.getRussianMessage()
        );
      case INCORECT_STATUS:
        throw new RentException(
            ErrorRent.INCORECT_STATUS.getEnglishMessage(),
            ErrorRent.INCORECT_STATUS.getRussianMessage()
        );
      case CANT_CANCEL:
        throw new TimeOutRentException(
            ErrorRent.CANT_CANCEL.getEnglishMessage(),
            ErrorRent.CANT_CANCEL.getRussianMessage()
        );
      case INVALID_PHONE:
        throw new RentException(
            ErrorRent.INVALID_PHONE.getEnglishMessage(),
            ErrorRent.INVALID_PHONE.getRussianMessage()
        );
      case NO_NUMBERS:
        throw new NoNumberException(
            ErrorRent.NO_NUMBERS.getEnglishMessage(),
            ErrorRent.NO_NUMBERS.getRussianMessage()
        );
      case SQL_ERROR:
        throw new SQLServerException(
            ErrorRent.SQL_ERROR.getEnglishMessage(),
            ErrorRent.SQL_ERROR.getRussianMessage()
        );
      case ACCOUNT_INACTIVE:
        throw new RentException(
            ErrorRent.ACCOUNT_INACTIVE.getEnglishMessage(),
            ErrorRent.ACCOUNT_INACTIVE.getRussianMessage()
        );
      case NO_BALANCE:
        throw new NoBalanceException();
      default:
        throw new RentException("Unknown rent error exception.", "Неизвестная ошибка аренды.");
    }
  }

  /**
   * Throws WrongParameterException if name contains in wrong parameter.
   *
   * @param name name parameter.
   * @return unknown if wrong parameter not contains in enum, else throw WrongParameter.
   * @throws WrongParameterException if one of parameters is incorrect.
   */
  public static void throwWrongParameterException(@NotNull String name) throws WrongParameterException {
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
  public static void throwNoNumbersOrNoBalance(@NotNull String name) throws NoNumberException, NoBalanceException {
    if (name.equalsIgnoreCase("no_numbers")) {
      throw new NoNumberException();
    } else if (name.equalsIgnoreCase("no_balance")) {
      throw new NoBalanceException("There is no money in the account.", "Нет денег на счету.");
    }
  }
}
