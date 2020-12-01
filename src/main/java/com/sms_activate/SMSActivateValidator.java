package com.sms_activate;

import com.sms_activate.error.SMSActivateBannedException;
import com.sms_activate.error.SMSActivateUnknownException;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.base.SMSActivateBaseTypeError;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import org.jetbrains.annotations.NotNull;

class SMSActivateValidator {
  /**
   * Throws WrongParameterException if name contains in wrong parameter.
   *
   * @param name name parameter.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  public void throwWrongParameterExceptionByName(@NotNull String name) throws SMSActivateWrongParameterException {
    SMSActivateWrongParameter wrongParameter = SMSActivateWrongParameter.getWrongParameterByName(name);

    if (wrongParameter != SMSActivateWrongParameter.UNKNOWN) {
      throw new SMSActivateWrongParameterException(wrongParameter);
    }
  }

  /**
   * Throw error by name.
   *
   * @param name name error.
   *             unknown if wrong parameter not contains in enum (it's may be SQLServerException), else throw WrongParameter.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateBaseException           if error happened on SQL-server.
   */
  public void throwCommonExceptionByName(@NotNull String name)
      throws SMSActivateBaseException {
    throwWrongParameterExceptionByName(name);

    if (name.toUpperCase().contains("SQL")) {
      throw new SMSActivateBaseException("Error SQL-server.", "Ошибка SQL-сервера.");
    }
  }

  /**
   * Throws sms-activate exception by name.
   *
   * @param name name sms-activate exception.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateBannedException         if your account nas been banned.
   * @throws SMSActivateBaseException           if error happened on SQL-server.
   */
  public void throwExceptionWithBan(@NotNull String name) throws SMSActivateBaseException {
    throwCommonExceptionByName(name);

    if (name.toUpperCase().contains("BANNED")) {
      throw new SMSActivateBannedException("Your account has been banned", "Ваш акаунт был забанен", name.split(":")[1]);
    }
  }

  public SMSActivateBaseException getBaseExceptionByErrorNameOrUnknown(@NotNull String errorName) {
    SMSActivateBaseTypeError error = SMSActivateBaseTypeError.getErrorByName(errorName);

    if (error != SMSActivateBaseTypeError.UNKNOWN) {
      return new SMSActivateBaseException(error);
    } else {
      return new SMSActivateUnknownException(errorName);
    }
  }
}
