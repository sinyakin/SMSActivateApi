package com.sms_activate;

import com.google.gson.reflect.TypeToken;
import com.sms_activate.activation.*;
import com.sms_activate.activation.extra.*;
import com.sms_activate.activation.set_status.SMSActivateAccessStatus;
import com.sms_activate.activation.set_status.SMSActivateSetStatusRequest;
import com.sms_activate.activation.set_status.SMSActivateSetStatusResponse;
import com.sms_activate.error.SMSActivateBannedException;
import com.sms_activate.error.SMSActivateUnknownException;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.base.SMSActivateBaseTypeError;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.qiwi.SMSActivateGetQiwiRequisitesResponse;
import com.sms_activate.rent.SMSActivateGetRentListResponse;
import com.sms_activate.rent.SMSActivateGetRentServicesAndCountriesResponse;
import com.sms_activate.rent.SMSActivateGetRentStatusResponse;
import com.sms_activate.rent.extra.SMSActivateGetRentNumber;
import com.sms_activate.rent.set_rent_status.SMSActivateRentStatus;
import com.sms_activate.rent.set_rent_status.SMSActivateSetRentStatusRequest;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>The class is a high-level API for interacting with the SMS-Activate API.
 * API capabilities allow you to work with the service through your software.
 * Before starting work, you must have an API key and a referral link to use all the API capabilities.</p>
 *
 * <p>All methods and constructor for this class throw SMSActivateWrongParameterException</p>
 * <p>Before request set referral link</p>
 *
 * @see SMSActivateWrongParameterException
 * @see SMSActivateBaseException
 * @see SMSActivateWrongParameter
 * @see SMSActivateBaseTypeError
 */
public class SMSActivateApi {
  /**
   * The minimal rent time.
   */
  public static final int MINIMAL_RENT_TIME = 4;

  /**
   * The maximum count string in batch.
   */
  public static final int MAX_COUNT_STRING_IN_BATCH = 10;

  /**
   * Regular expression for numbers.
   */
  private static final Pattern patternDigit = Pattern.compile("\\d+(?:[\\.,]\\d+)?");

  /**
   * Special validator for server responses.
   */
  private final SMSActivateValidator validator = new SMSActivateValidator();

  /**
   * Api key from site.
   */
  private final String apiKey;

  /**
   * Personal referral link (it's a numbers).
   */
  private String ref = null;

  /**
   * Constructor API sms-activate with API key.
   *
   * @param apiKey API key (not be null).
   * @throws SMSActivateWrongParameterException if api-key is incorrect.
   */
  public SMSActivateApi(@NotNull String apiKey) throws SMSActivateWrongParameterException {
    if (apiKey.isEmpty()) {
      throw new SMSActivateWrongParameterException(SMSActivateWrongParameter.EMPTY_KEY);
    }

    this.apiKey = apiKey;
  }

  /**
   * Returns the API key.
   *
   * @return apiKey API key (not be null).
   */
  @NotNull
  public String getApiKey() {
    return apiKey;
  }

  /**
   * Sets the personal referral links.
   *
   * @param ref personal referral links.
   */
  public void setRef(@NotNull String ref) {
    this.ref = ref;
  }

  /**
   * Returns the referral link.
   *
   * @return referral link
   */
  @Nullable
  public String getRef() {
    return ref;
  }

  /**
   * Returns the current account balance.
   *
   * @return current account balance.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                              <ul>
   *                                                <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                            Wrong parameter error types in this method:
   *                                              <ul>
   *                                                <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                              </ul>
   *                                            </p>
   *                                            </p>
   */
  @NotNull
  public BigDecimal getBalance() throws SMSActivateBaseException {
    return getBalanceByAction(SMSActivateAction.GET_BALANCE);
  }

  /**
   * Returns the current account balance and cashBack.
   *
   * @return current account balance and cashBack.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                              <ul>
   *                                                <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                            Wrong parameter error types in this method:
   *                                              <ul>
   *                                                <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                              </ul>
   *                                            </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetBalanceAndCashBackResponse getBalanceAndCashBack() throws SMSActivateBaseException {
    BigDecimal balance = getBalance();
    BigDecimal balanceAndCashBack = getBalanceByAction(SMSActivateAction.GET_BALANCE_AND_CASHBACK);

    return new SMSActivateGetBalanceAndCashBackResponse(balance, balanceAndCashBack.subtract(balance));
  }

  /**
   * Returns the available services from setting site.
   *
   * @return the available services.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                              <ul>
   *                                                <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                            Wrong parameter error types in this method:
   *                                              <ul>
   *                                                <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                              </ul>
   *                                            </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetNumbersStatusResponse getNumbersStatusByDefaultSettingFromSite() throws SMSActivateBaseException {
    return getNumbersStatus(null, null);
  }

  /**
   * Returns the available services by country and operator.
   *
   * @param countryId   id country.
   * @param operatorSet set names operators mobile network.
   * @return available services by county and operator (not be null).
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                              <ul>
   *                                                <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                            Wrong parameter error types in this method:
   *                                              <ul>
   *                                                <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                                <li>WRONG_OPERATOR - if operator or countryId is incorrect.</li>
   *                                              </ul>
   *                                            </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetNumbersStatusResponse getNumbersStatus(@Nullable Integer countryId, @Nullable Set<String> operatorSet)
    throws SMSActivateBaseException {
    if (countryId != null && countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    String operator = null;

    if (operatorSet != null) {
      operatorSet.removeIf(String::isEmpty);

      if (!operatorSet.isEmpty()) {
        operator = String.join(",", operatorSet);
      }
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_NUMBERS_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.OPERATOR, operator);

    if (countryId != null) {
      smsActivateURLBuilder.append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId));
    }

    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);
    SMSActivateJsonParser jsonParser = new SMSActivateJsonParser();

    Map<String, Integer> serviceMap = jsonParser.tryParseJson(data, new TypeToken<Map<String, Integer>>() {
    }.getType(), validator);
    Map<String, SMSActivateServiceInfo> serviceInfoMap = new HashMap<>();

    for (Map.Entry<String, Integer> entry : serviceMap.entrySet()) {
      String serviceName = entry.getKey();
      String[] parts = serviceName.split("_");

      serviceInfoMap.put(serviceName, new SMSActivateServiceInfo(
        parts[0],
        entry.getValue(),
        parts[1].equals("1")
      ));
    }

    return new SMSActivateGetNumbersStatusResponse(serviceInfoMap);
  }

  /**
   * Returns the activation by service, countryId.
   *
   * @param countryId id country.
   * @param service   service name for activation.
   * @return activation.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   * @throws SMSActivateBannedException         if your account has been banned.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                              <ul>
   *                                                <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                                <li>NO_NUMBERS - if currently no numbers;</li>
   *                                                <li>NO_BALANCE - if money in the account;</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                              Wrong parameter type error:
   *                                              <ul>
   *                                                <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                                <li>BAD_SERVICE - if service is incorrect;</li>
   *                                                <li>BANNED - if your account has been banned.</li>
   *                                              </ul>
   *                                            </p>
   */
  @NotNull
  public SMSActivateActivation getNumber(int countryId, @NotNull String service) throws SMSActivateBaseException {
    return getNumber(countryId, service, false);
  }

  /**
   * Returns the activation by service, countryId.
   *
   * @param countryId id country.
   * @param service   service name for activation.
   * @return activation.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   * @throws SMSActivateBannedException         if your account has been banned.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                              <ul>
   *                                                <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                                <li>NO_NUMBERS - if currently no numbers;</li>
   *                                                <li>NO_BALANCE - if money in the account;</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                              Wrong parameter type error:
   *                                              <ul>
   *                                                <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                                <li>BAD_SERVICE - if service is incorrect;</li>
   *                                                <li>BANNED - if your account has been banned.</li>
   *                                              </ul>
   *                                            </p>
   */
  @NotNull
  public SMSActivateActivation getNumber(int countryId, @NotNull String service, boolean forward) throws SMSActivateBaseException {
    return getNumber(countryId, service, forward, null, null);
  }

  /**
   * Returns the activation by service, countryId, phoneException, operator, forward
   *
   * @param countryId         id country.
   * @param service           service name for activation.
   * @param operatorSet       set mobile operators if operatorSet is null then .
   * @param phoneExceptionSet set excepted id numbers prefix if phoneExceptionSet is null then.
   * @param forward           is it necessary to request a number with forwarding.
   * @return activation.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   * @throws SMSActivateBannedException         if your account has been banned.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                               <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                                 <li>NO_NUMBERS - if currently no numbers;</li>
   *                                                <li>NO_BALANCE - if money in the account;</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                              Wrong parameter type error:
   *                                              <ul>
   *                                                <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                                <li>WRONG_OPERATOR - if operator or countryId is incorrect;</li>
   *                                                <li>BAD_SERVICE - if service is incorrect;</li>
   *                                                <li>BANNED - if your account has been banned;</li>
   *                                                <li>WRONG_PHONE_EXCEPTION - if one or more numbers prefix is incorrect.</li>
   *                                              </ul>
   *                                            </p>
   */
  @NotNull
  public SMSActivateActivation getNumber(
    int countryId,
    @NotNull String service,
    boolean forward,
    @Nullable Set<String> operatorSet,
    @Nullable Set<String> phoneExceptionSet
  ) throws SMSActivateBaseException {
    if (countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    String phoneException = null;
    String operator = null;

    if (phoneExceptionSet != null) {
      phoneExceptionSet.removeIf(String::isEmpty);

      if (!phoneExceptionSet.isEmpty()) {
        phoneException = String.join(",", phoneExceptionSet);
      }
    }

    if (operatorSet != null) {
      operatorSet.removeIf(String::isEmpty);

      if (!operatorSet.isEmpty()) {
        operator = String.join(",", operatorSet);
      }
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_NUMBER);
    smsActivateURLBuilder.append(SMSActivateURLKey.REF, ref)
      .append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId))
      .append(SMSActivateURLKey.SERVICE, service)
      .append(SMSActivateURLKey.FORWARD, forward ? "1" : "0")
      .append(SMSActivateURLKey.OPERATOR, operator)
      .append(SMSActivateURLKey.PHONE_EXCEPTION, phoneException);

    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);
    validator.throwExceptionWithBan(data);

    if (!data.startsWith(SMSActivateMagicKey.ACCESS)) {
      throw validator.getBaseExceptionByErrorNameOrUnknown(data, null);
    }

    try {
      String[] parts = data.split(":");
      int id = Integer.parseInt(parts[1]);
      long number = Long.parseLong(parts[2]);

      return new SMSActivateActivation(id, number, service);
    } catch (NumberFormatException e) {
      throw new SMSActivateUnknownException(data, "Error formatting to number.");
    }
  }

  /**
   * Returns the specified object id by countryId, multiService.<br/>
   * Separator for multiService is commas. <br/>
   *
   * @param countryId       id country.
   * @param multiServiceSet services for ordering (not be null).
   * @return SMSActivateGetMultiServiceNumberResponse object.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   * @throws SMSActivateBannedException         if your account has been banned.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                               <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                            Wrong parameter error types in this method:
   *                                              <ul>
   *                                                <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                                <li>WRONG_OPERATOR - if operator or countryId is incorrect;</li>
   *                                                <li>BAD_SERVICE - if service is incorrect;</li>
   *                                                <li>BANNED - if your account has been banned;</li>
   *                                                <li>NOT_AVAILABLE  - if country not supported multi-service.</li>
   *                                              </ul>
   *                                            </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetMultiServiceNumberResponse getMultiServiceNumber(int countryId, @NotNull Set<String> multiServiceSet)
    throws SMSActivateBaseException {
    return getMultiServiceNumber(countryId, multiServiceSet, null, null);
  }

  /**
   * Returns the specified object with activations id by countryId, multiService.<br/>
   * Separator for multiService, multiForward and operator is commas. <br/>
   *
   * @param countryId        id country.
   * @param multiServiceSet  services for ordering (not be null).
   * @param operatorSet      mobile operator.
   * @param multiForwardList is it necessary to request a number with forwarding.
   * @return specified object with activations.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   * @throws SMSActivateBannedException         if your account has been banned.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                               <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                            Wrong parameter error types in this method:
   *                                              <ul>
   *                                                <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                                <li>WRONG_OPERATOR - if operator or countryId is incorrect;</li>
   *                                                <li>BAD_SERVICE - if service is incorrect;</li>
   *                                                <li>BANNED - if your account has been banned;</li>
   *                                                <li>NOT_AVAILABLE  - if country not supported multi-service.</li>
   *                                              </ul>
   *                                            </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetMultiServiceNumberResponse getMultiServiceNumber(
    int countryId,
    @NotNull Set<String> multiServiceSet,
    @Nullable Set<String> operatorSet,
    @Nullable List<Boolean> multiForwardList
  ) throws SMSActivateBaseException {
    multiServiceSet.removeIf(String::isEmpty);

    if (multiServiceSet.isEmpty()) {
      throw new SMSActivateBaseException("Set multi-service can't be empty.", "Set мультисервисов не может быть пустой.");
    }

    if (countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    String strMultiService = String.join(",", multiServiceSet);
    String strOperators = null;
    String strMultiForward = null;

    if (multiForwardList != null) {
      strMultiForward = multiForwardList.stream()
        .filter(forward -> !Objects.isNull(forward))
        .map(forward -> forward ? "1" : "0")
        .collect(Collectors.joining(","));
    }

    if (operatorSet != null) {
      operatorSet.removeIf(String::isEmpty);

      if (!operatorSet.isEmpty()) {
        strOperators = String.join(",", operatorSet);
      }
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_MULTI_SERVICE_NUMBER);
    smsActivateURLBuilder.append(SMSActivateURLKey.REF, ref)
      .append(SMSActivateURLKey.MULTI_SERVICE, strMultiService)
      .append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId))
      .append(SMSActivateURLKey.MULTI_FORWARD, strMultiForward)
      .append(SMSActivateURLKey.OPERATOR, strOperators);

    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);
    SMSActivateJsonParser jsonParser = new SMSActivateJsonParser();

    validator.throwExceptionWithBan(data);
    List<SMSActivateActivation> smsActivateActivationList = jsonParser.tryParseJson(data, new TypeToken<List<SMSActivateActivation>>() {
    }.getType(), validator);

    return new SMSActivateGetMultiServiceNumberResponse(smsActivateActivationList);
  }

  /**
   * Sets the status activation.
   * <p>
   * Get number using getNumber method after that the following actions are available:<br/>
   * <em>CANCEL</em> - Cancel activation (if the number did not suit you)<br/>
   * <em>SEND_READY_NUMBER</em> - Report that SMS has been sent (optional)
   * </p>
   * <p>
   * To activate with status READY:<br/>
   * <em>CANCEL</em> - Cancel activation
   * </p>
   * <p>
   * Immediately after receiving the code:<br/>
   * <em>REQUEST_ONE_MORE_CODE</em> - Request another SMS<br/>
   * <em>FINISH</em> - Confirm SMS code and complete activation
   * </p>
   * <p>
   * To activate with status RETRY_GET:<br/>
   * <em>FINISH</em> - Confirm SMS code and complete activation
   * </p>
   *
   * @param idActivation     id to set activation status (not be null).
   * @param status value to establish (not be null).
   * @return access activation.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Base type error in this method:
   *                                              <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server.</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                              Wrong parameter error types in this method:
   *                                               <ul>
   *                                                 <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                                 <li>BAD_STATUS - if service is incorrect;</li>
   *                                                 <li>NO_ACTIVATION - if activation is not exist.</li>
   *                                               </ul>
   *                                            </p>
   */
  @NotNull
  public SMSActivateSetStatusResponse setStatus(int idActivation, @NotNull SMSActivateSetStatusRequest status) throws SMSActivateBaseException {
    return setStatusWithForwardPhone(idActivation, status, null);
  }

  /**
   * Sets the status activation.
   * <p>
   * Get number using getNumber method after that the following actions are available:<br/>
   * <em>CANCEL</em> - Cancel activation (if the number did not suit you)<br/>
   * <em>SEND_READY_NUMBER</em> - Report that SMS has been sent (optional)
   * </p>
   * <p>
   * To activate with status READY:<br/>
   * <em>CANCEL</em> - Cancel activation
   * </p>
   * <p>
   * Immediately after receiving the code:<br/>
   * <em>REQUEST_ONE_MORE_CODE</em> - Request another SMS<br/>
   * <em>FINISH</em> - Confirm SMS code and complete activation
   * </p>
   * <p>
   * To activate with status RETRY_GET:<br/>
   * <em>FINISH</em> - Confirm SMS code and complete activation
   * </p>
   *
   * @param idActivation           id to set activation status (not be null).
   * @param status       value to establish (not be null).
   * @param forwardPhone number phone for forward.
   * @return access activation.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Base type error in this method:
   *                                              <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server.</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                              Wrong parameter error types in this method:
   *                                               <ul>
   *                                                 <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                                 <li>BAD_STATUS - if service is incorrect;</li>
   *                                                 <li>NO_ACTIVATION - if activation is not exist.</li>
   *                                               </ul>
   *                                            </p>
   */
  @NotNull
  public SMSActivateSetStatusResponse setStatusWithForwardPhone(
    int idActivation,
    @NotNull SMSActivateSetStatusRequest status,
    @Nullable Long forwardPhone
  ) throws SMSActivateBaseException {
    if (forwardPhone != null && forwardPhone <= 0) {
      throw new SMSActivateWrongParameterException(
        "Phone number for forwarding must be positive.",
        "Телефон для переадресации должен быть положительный."
      );
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.SET_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.STATUS, String.valueOf(status.getId()))
      .append(SMSActivateURLKey.ID, String.valueOf(idActivation))
      .append(SMSActivateURLKey.FORWARD, String.valueOf(forwardPhone));

    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);

    SMSActivateAccessStatus smsActivateAccessStatus = SMSActivateAccessStatus.getStatusByName(data);

    if (smsActivateAccessStatus != SMSActivateAccessStatus.UNKNOWN) {
      return new SMSActivateSetStatusResponse(smsActivateAccessStatus);
    }

    throw validator.getBaseExceptionByErrorNameOrUnknown(data, null);
  }

  /**
   * Returns the state by id activation.
   *
   * @param idActivation id activation to get activation state.
   * @return state activation.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Base type error in this method:
   *                                              <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server.</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                              Wrong parameter error types in this method:
   *                                               <ul>
   *                                                 <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                                 <li>NO_ACTIVATION - if activation is not exist.</li>
   *                                               </ul>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetStatusResponse getStatus(int idActivation) throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(idActivation));

    String code = null;
    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);

    if (data.contains(":")) {
      String[] parts = data.split(":");

      data = parts[0];
      code = parts[1];
    }

    SMSActivateGetStatusActivation status = SMSActivateGetStatusActivation.getStatusByName(data);

    if (status != SMSActivateGetStatusActivation.UNKNOWN) {
      return new SMSActivateGetStatusResponse(status, code);
    }

    throw validator.getBaseExceptionByErrorNameOrUnknown(data, null);
  }

  /**
   * Returns the specified object from server with text sms.
   *
   * @param idActivation id activation.
   * @return full text sms with status:
   * <p>
   *   <ul>
   *    <li>if SMS arrived, then the answer will be with the FULL_SMS status</li>
   *    <li>if expected then WAIT_CODE</li>
   *    <li>if canceled then CANCEL</li>
   *   </ul>
   * </p>
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                               <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server.</li>
   *                                               </ul>
   *                                             </p>
   *                                             <p>
   *                                              Wrong parameter error types in this method:
   *                                               <ul>
   *                                                 <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                                 <li>NO_ACTIVATION - if activation is not exist.</li>
   *                                               </ul>
   *                                             </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetFullSmsResponse getFullSms(int idActivation) throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_FULL_SMS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(idActivation));

    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);

    SMSActivateStatusNumber smsActivateStatusNumber = SMSActivateStatusNumber.getStatusByName(data);

    if (smsActivateStatusNumber != SMSActivateStatusNumber.UNKNOWN) {
      String message = "";

      if (smsActivateStatusNumber == SMSActivateStatusNumber.FULL_SMS) {
        message = data.split(":", 2)[1];
      }

      return new SMSActivateGetFullSmsResponse(smsActivateStatusNumber, message);
    }

    throw validator.getBaseExceptionByErrorNameOrUnknown(data, null);
  }

  /**
   * Returns the all actual prices in all countries and all services.
   *
   * @return price list country.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                               <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server.</li>
   *                                               </ul>
   *                                             </p>
   *                                             <p>
   *                                              Wrong parameter error types in this method:
   *                                               <ul>
   *                                                 <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                               </ul>
   *                                             </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetPricesResponse getAllPrices() throws SMSActivateBaseException {
    return getPricesByCountryIdAndServiceShortName(null, null);
  }

  /**
   * Returns the all actual price services by country id.
   *
   * @param countryId id country.
   * @return all actual price services by country id.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                               <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server.</li>
   *                                               </ul>
   *                                             </p>
   *                                             <p>
   *                                              Wrong parameter error types in this method:
   *                                               <ul>
   *                                                 <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                               </ul>
   *                                             </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetPricesResponse getPricesAllServicesByCountryId(int countryId) throws SMSActivateBaseException {
    return getPricesByCountryIdAndServiceShortName(countryId, null);
  }

  /**
   * Returns the all actual price services by country id.
   *
   * @param serviceShortName short name service.
   * @return all actual price services by country id.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                               <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server.</li>
   *                                               </ul>
   *                                             </p>
   *                                             <p>
   *                                              Wrong parameter error types in this method:
   *                                               <ul>
   *                                                 <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                               </ul>
   *                                             </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetPricesResponse getPricesAllCountryByServiceShortName(@NotNull String serviceShortName) throws SMSActivateBaseException {
    return getPricesByCountryIdAndServiceShortName(null, serviceShortName);
  }

  /**
   * Returns the actual rent prices by country and service short name.
   *
   * @param countryId        id number (default 0).
   * @param serviceShortName service for needed price list (default null).
   *                         <pre>
   *                                                 {@code null, null -> all service and all country
   *                                                 null, serviceShortName -> all country by service
   *                                                  countryId, null -> all service in country}
   *                                                 </pre>
   * @return price list country.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                               <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server.</li>
   *                                               </ul>
   *                                             </p>
   *                                             <p>
   *                                              Wrong parameter error types in this method:
   *                                               <ul>
   *                                                 <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                                 <li>WRONG_OPERATOR - if country id is incorrect;</li>
   *                                                 <li>WRONG_SERVICE - if service is incorrect;</li>
   *                                               </ul>
   *                                             </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetPricesResponse getPricesByCountryIdAndServiceShortName(@Nullable Integer countryId, @Nullable String serviceShortName)
    throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_PRICES);

    if (serviceShortName != null) {
      if (serviceShortName.length() != 2) {
        throw new SMSActivateWrongParameterException("Wrong service short name.", "Некорректное короткое имя сервиса.");
      }

      smsActivateURLBuilder.append(SMSActivateURLKey.SERVICE, serviceShortName);
    }

    if (countryId != null) {
      if (countryId < 0) {
        throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
      }

      smsActivateURLBuilder.append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId));
    }

    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);
    SMSActivateJsonParser jsonParser = new SMSActivateJsonParser();

    Map<Integer, Map<String, SMSActivateGetPriceInfo>> smsActivateGetPriceMap = jsonParser.tryParseJson(data,
      new TypeToken<Map<Integer, Map<String, SMSActivateGetPriceInfo>>>() {
      }.getType(), validator);

    return new SMSActivateGetPricesResponse(smsActivateGetPriceMap);
  }

  /**
   * Returns the country with information.
   *
   * @return country with information.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                               <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server.</li>
   *                                               </ul>
   *                                             </p>
   *                                             <p>
   *                                              Wrong parameter error types in this method:
   *                                               <ul>
   *                                                 <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                               </ul>
   *                                             </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetCountriesResponse getCountries() throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_COUNTRIES);
    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);
    SMSActivateJsonParser jsonParser = new SMSActivateJsonParser();

    Map<Integer, SMSActivateCountryInfo> countryInformationMap = jsonParser.tryParseJson(data, new TypeToken<Map<Integer, SMSActivateCountryInfo>>() {
    }.getType(), validator);

    return new SMSActivateGetCountriesResponse(new ArrayList<>(countryInformationMap.values()));
  }

  /**
   * Returns the qiwi response with data on wallet.
   *
   * @return qiwi response with data on wallet with status:
   * <p>
   *  <ul>
   *    <li>SUCCESS - all is OK;</li>
   *    <li>FALSE - acceptance of qiwi payments is not possible.</li>
   *  </ul>
   * </p>
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                               <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server.</li>
   *                                               </ul>
   *                                             </p>
   *                                             <p>
   *                                              Wrong parameter error types in this method:
   *                                               <ul>
   *                                                 <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                               </ul>
   *                                             </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetQiwiRequisitesResponse getQiwiRequisites() throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_QIWI_REQUISITES);
    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);
    SMSActivateJsonParser jsonParser = new SMSActivateJsonParser();
    return jsonParser.tryParseJson(data, new TypeToken<SMSActivateGetQiwiRequisitesResponse>() {
    }.getType(), validator);
  }

  /**
   * Returns the id activation for additional service by forwarding.
   *
   * @param id id activation.
   * @return id activation for additional service by forwarding
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                               <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                                 <li>NO_BALANCE  - if error happened in sql-server.</li>
   *                                               </ul>
   *                                             </p>
   *                                             <p>
   *                                              Wrong parameter error types in this method:
   *                                               <ul>
   *                                                 <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                                 <li>WRONG_ADDITIONAL_SERVICE  - if additional service is incorrect;</li>
   *                                                 <li>WRONG_ACTIVATION_ID   - if id parent is incorrect;</li>
   *                                                 <li>WRONG_SECURITY    - if trying to transfer an activation ID without forwarding, or a completed / inactive activation;</li>
   *                                                 <li>REPEAT_ADDITIONAL_SERVICE     - if ordered again service that has already been purchased;</li>
   *                                               </ul>
   *                                             </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateActivation getAdditionalService(int id, @NotNull String service) throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_ADDITIONAL_SERVICE);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(id))
      .append(SMSActivateURLKey.SERVICE, service);

    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);

    try {
      if (data.startsWith(SMSActivateMagicKey.ADDITIONAL)) {
        String[] parts = data.split(":");
        int childId = Integer.parseInt(parts[1]);
        long number = Long.parseLong(parts[2]);

        return new SMSActivateActivation(childId, number, service);
      }

      throw validator.getBaseExceptionByErrorNameOrUnknown(data, null);
    } catch (NumberFormatException e) {
      throw new SMSActivateUnknownException(data, "Error formatting to number.");
    }
  }

  /**
   * Returns the rent object with countries supported rent and accessed services by country.
   *
   * @return rent object with countries supported rent and accessed services by country.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                               <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                               </ul>
   *                                             </p>
   *                                             <p>
   *                                              Wrong parameter error types in this method:
   *                                               <ul>
   *                                                 <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                               </ul>
   *                                             </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetRentServicesAndCountriesResponse getRentServicesAndCountries() throws SMSActivateBaseException {
    return getRentServicesAndCountries(0, null, MINIMAL_RENT_TIME);
  }

  /**
   * Returns the rent object with countries supported rent and accessed services by country.
   *
   * @param countryId   id country (default 0).
   * @param operatorSet mobile operators.
   * @param time        time rent in hours (default 1).
   *                    time >= 1
   * @return the rent object with countries supported rent and accessed services by country.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                               <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                               </ul>
   *                                             </p>
   *                                             <p>
   *                                              Wrong parameter error types in this method:
   *                                               <ul>
   *                                                 <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                               </ul>
   *                                             </p>
   *                                            </p>
   *                                             </p>
   */
  @NotNull
  public SMSActivateGetRentServicesAndCountriesResponse getRentServicesAndCountries(int countryId, @Nullable Set<String> operatorSet, int time)
    throws SMSActivateBaseException {
    if (time < MINIMAL_RENT_TIME) {
      throw new SMSActivateWrongParameterException(
        "Time rent can't be negative or equals " + MINIMAL_RENT_TIME,
        "Время аренды не может быть меньше или равно " + MINIMAL_RENT_TIME
      );
    }
    if (countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    String operator = null;

    if (operatorSet != null) {
      operatorSet.removeIf(String::isEmpty);

      if (!operatorSet.isEmpty()) {
        operator = String.join(",", operatorSet);
      }
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_SERVICES_AND_COUNTRIES);
    smsActivateURLBuilder.append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId))
      .append(SMSActivateURLKey.OPERATOR, operator)
      .append(SMSActivateURLKey.RENT_TIME, String.valueOf(time));

    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);
    SMSActivateJsonParser jsonParser = new SMSActivateJsonParser();

    return jsonParser.tryParseJson(data, new TypeToken<SMSActivateGetRentServicesAndCountriesResponse>() {
    }.getType(), validator);
  }

  /**
   * Returns the object rent on {@value MINIMAL_RENT_TIME}.
   *
   * @param service service to which you need to get a number.
   * @return object rent.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                              <ul>
   *                                                <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                                <li>NO_NUMBERS - if currently no numbers;</li>
   *                                                <li>NO_BALANCE - if money in the account;</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                              Wrong parameter type error:
   *                                              <ul>
   *                                                <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                                <li>BAD_SERVICE - if service is incorrect;</li>
   *                                                <li>ACCOUNT_INACTIVE  - if no free numbers.</li>
   *                                              </ul>
   *                                            </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetRentNumber getRentNumber(@NotNull String service) throws SMSActivateBaseException {
    return getRentNumber(service, 0, null, MINIMAL_RENT_TIME, null);
  }

  /**
   * Returns the object rent on time.
   *
   * @param countryId  id country (default 0 - Russia).
   * @param service    service to which you need to get a number.
   * @param operator   mobile operator.
   * @param time       time rent (default MINIMAL_RENT_TIME hour).
   * @param urlWebhook url for webhook.
   * @return object rent.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                              <ul>
   *                                                <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                                <li>NO_NUMBERS - if currently no numbers;</li>
   *                                                <li>NO_BALANCE - if money in the account;</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                              Wrong parameter type error:
   *                                              <ul>
   *                                                <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                                <li>BAD_SERVICE - if service is incorrect;</li>
   *                                                <li>ACCOUNT_INACTIVE  - if no free numbers.</li>
   *                                              </ul>
   *                                            </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetRentNumber getRentNumber(
    @NotNull String service,
    int countryId,
    @Nullable String operator,
    int time,
    @Nullable String urlWebhook
  ) throws SMSActivateBaseException {
    if (countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    } if (time < MINIMAL_RENT_TIME) {
      throw new SMSActivateWrongParameterException(
        String.format("The rental time cannot be less than %d.", MINIMAL_RENT_TIME),
        String.format("Время аренды не может быть меньше чем %d.", MINIMAL_RENT_TIME)
      );
    } if (operator != null && operator.isEmpty()) {
      throw new SMSActivateWrongParameterException(SMSActivateWrongParameter.WRONG_OPERATOR);
    } if (urlWebhook != null && urlWebhook.isEmpty()) {
      throw new SMSActivateWrongParameterException(
        "Parameter urlwebhook can't be empty.",
        "Параметер urlwebhook can't be empty."
      );
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_NUMBER);
    smsActivateURLBuilder.append(SMSActivateURLKey.RENT_TIME, String.valueOf(time))
      .append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId))
      .append(SMSActivateURLKey.OPERATOR, operator)
      .append(SMSActivateURLKey.URL, urlWebhook)
      .append(SMSActivateURLKey.SERVICE, service);

    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);
    SMSActivateJsonParser jsonParser = new SMSActivateJsonParser();

    if (!validator.containsSuccessStatus(data)) {
      SMSActivateErrorResponse errorResponse = jsonParser.tryParseJson(data, new TypeToken<SMSActivateErrorResponse>() {
      }.getType(), validator);

      throw validator.getBaseExceptionByErrorNameOrUnknown(errorResponse.getMessage(), null);
    }

    SMSActivateGetRentNumberResponse smsActivateGetRentNumberResponse = jsonParser.tryParseJson(data, new TypeToken<SMSActivateGetRentNumberResponse>() {
    }.getType(), validator);
    return smsActivateGetRentNumberResponse.getSMSmsActivateGetRentNumber();
  }

  /**
   * Returns the list sms.
   *
   * @param idRent id received in response when ordering a number.
   * @return list sms.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                              <ul>
   *                                                <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                              Wrong parameter type error:
   *                                              <ul>
   *                                                <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                                <li>NO_ID_RENT   - if is not input.</li>
   *                                                <li>STATUS_WAIT_CODE    - if not sms.</li>
   *                                                <li>STATUS_CANCEL     - if rent is canceled.</li>
   *                                                <li>STATUS_FINISH      - if rent is finished.</li>
   *                                              </ul>
   *                                            </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetRentStatusResponse getRentStatus(int idRent) throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(idRent));

    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);
    SMSActivateJsonParser jsonParser = new SMSActivateJsonParser();

    if (!validator.containsSuccessStatus(data)) {
      SMSActivateErrorResponse errorResponse = jsonParser.tryParseJson(data, new TypeToken<SMSActivateErrorResponse>() {
      }.getType(), validator);
      throw validator.getBaseExceptionByErrorNameOrUnknown(errorResponse.getMessage(), null);
    }

    return jsonParser.tryParseJson(data, new TypeToken<SMSActivateGetRentStatusResponse>() {
    }.getType(), validator);
  }

  /**
   * Sets the status on rent.
   *
   * @param idRent     id activation for set status rent.
   * @param status status rent.
   * @return response status from server.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                              <ul>
   *                                                <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                              Wrong parameter type error:
   *                                              <ul>
   *                                                <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                                <li>NO_ID_RENT - if is not input.</li>
   *                                                <li>INCORRECT_STATUS - if status is incorrect.</li>
   *                                                <li>CANT_CANCEL - if it is impossible to cancel the rent (more than 20 min.).</li>
   *                                                <li>ALREADY_FINISH - if rent is finished.</li>
   *                                                <li>ALREADY_CANCEL - if rent is canceled.</li>
   *                                                <li>INVALID_PHONE - if id is incorrect.</li>
   *                                              </ul>
   *                                            </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateRentStatus setRentStatus(int idRent, @NotNull SMSActivateSetRentStatusRequest status)
    throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.SET_RENT_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(idRent))
      .append(SMSActivateURLKey.STATUS, String.valueOf(status.getId()));

    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);
    SMSActivateJsonParser jsonParser = new SMSActivateJsonParser();

    if (!validator.containsSuccessStatus(data)) {
      SMSActivateErrorResponse response = jsonParser.tryParseJson(data, new TypeToken<SMSActivateErrorResponse>() {
      }.getType(), validator);
      throw validator.getBaseExceptionByErrorNameOrUnknown(response.getMessage(), null);
    }

    return SMSActivateRentStatus.SUCCESS;
  }

  /**
   * Returns the current rents.
   *
   * @return current rents.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                              <ul>
   *                                                <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                                <li>NO_NUMBERS - if no rented rooms numbers.</li>
   *                                              </ul>
   *                                            </p>
   *                                            <p>
   *                                              Wrong parameter type error:
   *                                              <ul>
   *                                                <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                              </ul>
   *                                            </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetRentListResponse getRentList() throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_LIST);
    SMSActivateJsonParser jsonParser = new SMSActivateJsonParser();

    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);

    if (!validator.containsSuccessStatus(data)) {
      SMSActivateErrorResponse smsActivateErrorResponse = jsonParser.tryParseJson(data, new TypeToken<SMSActivateErrorResponse>() {
      }.getType(), validator);
      throw validator.getBaseExceptionByErrorNameOrUnknown(smsActivateErrorResponse.getMessage(), null);
    }

    return jsonParser.tryParseJson(data, new TypeToken<SMSActivateGetRentListResponse>() {
    }.getType(), validator);
  }

  /**
   * Returns the first {@value MAX_COUNT_STRING_IN_BATCH} current activation.
   * If your count of activations is < {@value MAX_COUNT_STRING_IN_BATCH}, then return only the existing ones,
   * else they will return in batches of first {@value MAX_COUNT_STRING_IN_BATCH} activations.
   *
   * @return if you not have activation returns empty list else list with your activations.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                               <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                               </ul>
   *                                             </p>
   *                                             <p>
   *                                              Wrong parameter error types in this method:
   *                                               <ul>
   *                                                 <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                               </ul>
   *                                             </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetCurrentActivationsResponse getCurrentActivations() throws SMSActivateBaseException {
    return getCurrentActivations(1, MAX_COUNT_STRING_IN_BATCH);
  }

  /**
   * Returns the list current activation where contains by {@value MAX_COUNT_STRING_IN_BATCH} activation.
   * If your count of activations is < {@value MAX_COUNT_STRING_IN_BATCH}, then return only the existing ones,
   * else they will return in batches of {@value MAX_COUNT_STRING_IN_BATCH} activations in each response.
   *
   * @param batch              number requested batch (default 1).
   * @param countStringInBatch count string in current batch.
   * @return if you not have activation returns empty list else list with your activations.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error type not documented.
   *                                            <p>
   *                                            Types errors:
   *                                            <p>
   *                                            Base type error in this method:
   *                                               <ul>
   *                                                 <li>ERROR_SQL - if error happened in sql-server;</li>
   *                                               </ul>
   *                                             </p>
   *                                             <p>
   *                                              Wrong parameter error types in this method:
   *                                               <ul>
   *                                                 <li>BAD_KEY - if your api-key is incorrect;</li>
   *                                               </ul>
   *                                             </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateGetCurrentActivationsResponse getCurrentActivations(int batch, int countStringInBatch)
    throws SMSActivateBaseException {
    if (batch <= 0) {
      throw new SMSActivateWrongParameterException("Number page must be positive.", "Номер страницы должен быть положиельным.");
    }
    if (countStringInBatch <= 0 || countStringInBatch > MAX_COUNT_STRING_IN_BATCH) {
      throw new SMSActivateWrongParameterException(
        String.format("The maximum number of requested lines is not more than %d.", MAX_COUNT_STRING_IN_BATCH),
        String.format("Максимальное число запрашиваемых строк не более %d.", MAX_COUNT_STRING_IN_BATCH)
      );
    }

    int len = batch * countStringInBatch;
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_CURRENT_ACTIVATIONS);
    smsActivateURLBuilder.append(SMSActivateURLKey.START, String.valueOf(len - countStringInBatch))
      .append(SMSActivateURLKey.LENGTH, String.valueOf(len));

    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);
    SMSActivateJsonParser jsonParser = new SMSActivateJsonParser();

    return jsonParser.tryParseJson(data, new TypeToken<SMSActivateGetCurrentActivationsResponse>() {
    }.getType(), validator);
  }

  /**
   * Returns the current account balance by specific action.
   *
   * @param smsActivateAction name specific action to get balance.
   * @return current account balance.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  private BigDecimal getBalanceByAction(@NotNull SMSActivateAction smsActivateAction) throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, smsActivateAction);
    String data = new SMSActivateWebClient().getOrThrowCommonException(smsActivateURLBuilder, validator);
    Matcher matcher = patternDigit.matcher(data);

    if (!matcher.find()) {
      throw new SMSActivateBaseException("Error: " + data, "Error: " + data);
    }

    return new BigDecimal(matcher.group());
  }
}
