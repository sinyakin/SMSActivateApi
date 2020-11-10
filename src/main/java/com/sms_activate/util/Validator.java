package com.sms_activate.util;

import com.sms_activate.error.*;
import com.sms_activate.error.rent.RentException;
import com.sms_activate.error.rent.StateErrorRent;
import com.sms_activate.error.rent.TimeOutRentException;
import org.jetbrains.annotations.NotNull;

public class Validator {
  /**
   * Throws the error by type.
   *
   * @param stateErrorRent error type.
   * @throws SQLServerException if error happened on SQL-server.
   * @throws RentException      if rent is cancel or finish.
   * @throws NoBalanceException if no numbers.
   * @throws NoNumberException  if in account balance is zero.
   */
  public static void throwStateErrorRent(@NotNull StateErrorRent stateErrorRent)
      throws RentException, SQLServerException, NoBalanceException, NoNumberException {
    switch (stateErrorRent) {
      case ALREADY_FINISH:
        throw new RentException(
            StateErrorRent.ALREADY_FINISH.getEnglandMessage(),
            StateErrorRent.ALREADY_FINISH.getRussianMessage()
        );
      case ALREADY_CANCEL:
        throw new RentException(
            StateErrorRent.ALREADY_CANCEL.getEnglandMessage(),
            StateErrorRent.ALREADY_CANCEL.getRussianMessage()
        );
      case NO_ID_RENT:
        throw new RentException(
            StateErrorRent.NO_ID_RENT.getEnglandMessage(),
            StateErrorRent.NO_ID_RENT.getRussianMessage()
        );
      case INCORECT_STATUS:
        throw new RentException(
            StateErrorRent.INCORECT_STATUS.getEnglandMessage(),
            StateErrorRent.INCORECT_STATUS.getRussianMessage()
        );
      case CANT_CANCEL:
        throw new TimeOutRentException(
            StateErrorRent.CANT_CANCEL.getEnglandMessage(),
            StateErrorRent.CANT_CANCEL.getRussianMessage()
        );
      case INVALID_PHONE:
        throw new RentException(
            StateErrorRent.INVALID_PHONE.getEnglandMessage(),
            StateErrorRent.INVALID_PHONE.getRussianMessage()
        );
      case NO_NUMBERS:
        throw new NoNumberException(
            StateErrorRent.NO_NUMBERS.getEnglandMessage(),
            StateErrorRent.NO_NUMBERS.getRussianMessage()
        );
      case SQL_ERROR:
        throw new SQLServerException(
            StateErrorRent.SQL_ERROR.getEnglandMessage(),
            StateErrorRent.SQL_ERROR.getRussianMessage()
        );
      case ACCOUNT_INACTIVE:
        throw new RentException(
            StateErrorRent.ACCOUNT_INACTIVE.getEnglandMessage(),
            StateErrorRent.ACCOUNT_INACTIVE.getRussianMessage()
        );
      default:
        throw new NoBalanceException("There is no money in the account.", "Нет денег на счету.");
    }
  }

  /**
   * Validate the data on error response.
   *
   * @param data data for validate.
   * @throws WrongParameterException if one of parameters is incorrect.
   * @throws BannedException         if account has been banned.
   * @throws SQLServerException      if error happened on SQL-server.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  //todo
  public static void validateData(@NotNull String data)
      throws BannedException, SQLServerException, WrongParameterException, NoBalanceException, NoNumberException {
    if (!data.contains("ACCESS")) {
      if (data.contains("BAD")) {
        WrongParameter parameter = WrongParameter.valueOf(WrongParameter.class, data);
        throw new WrongParameterException(parameter.getEnglandMessage(), parameter.getRussianMessage());
      } else if (data.contains("SQL")) {
        throw new SQLServerException("Error SQL server.", "Ошибка SQL-сервера.");
      } else if (data.equalsIgnoreCase("banned")) {
        String date = ":".split(data)[1];
        throw new BannedException("Account has been banned to " + date, "Акаунт забанен на " + date);
      } else if (data.contains("NO")) {
        if (data.equalsIgnoreCase("no_balance")) {
          throw new NoBalanceException("There is no money in the account.", "Нет денег на счету");
        } else if (data.equalsIgnoreCase("no_numbers")) {
          throw new NoNumberException("No numbers.", "Нет номеров.");
        }
      }
    }
  }
}
