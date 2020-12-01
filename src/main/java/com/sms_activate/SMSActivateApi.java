package com.sms_activate;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sms_activate.activation.SMSActivateActivation;
import com.sms_activate.activation.SMSActivateGetMultiServiceNumberResponse;
import com.sms_activate.activation.balance.SMSActivateGetBalanceAndCashBackResponse;
import com.sms_activate.activation.current_activation.SMSActivateGetCurrentActivationResponse;
import com.sms_activate.activation.current_activation.SMSActivateGetCurrentActivationsResponse;
import com.sms_activate.activation.get_countries.SMSActivateGetCountriesResponse;
import com.sms_activate.activation.get_countries.SMSActivateGetCountryResponse;
import com.sms_activate.activation.get_full_sms.SMSActivateGetFullSmsResponse;
import com.sms_activate.activation.get_full_sms.SMSActivateGetFullTypeResponse;
import com.sms_activate.activation.get_prices.SMSActivateGetPriceResponse;
import com.sms_activate.activation.get_prices.SMSActivateGetPricesResponse;
import com.sms_activate.activation.get_status.SMSActivateGetStatus;
import com.sms_activate.activation.get_status.SMSActivateGetStatusResponse;
import com.sms_activate.activation.numbers_status.SMSActivateGetNumberStatusResponse;
import com.sms_activate.activation.numbers_status.SMSActivateGetNumbersStatusResponse;
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
import com.sms_activate.qiwi.SMSActivateQiwiStatus;
import com.sms_activate.rent.SMSActivateGetRentNumberResponse;
import com.sms_activate.rent.get_rent_list.SMSActivateGetRentListResponse;
import com.sms_activate.rent.get_rent_list.SMSActivateGetRentResponse;
import com.sms_activate.rent.get_rent_services_and_countries.SMSActivateGetRentServicesAndCountriesResponse;
import com.sms_activate.rent.get_rent_services_and_countries.SMSActivateRentService;
import com.sms_activate.rent.get_rent_status.SMSActivateGetRentStatusResponse;
import com.sms_activate.rent.get_rent_status.SMSActivateSMS;
import com.sms_activate.rent.set_rent_status.SMSActivateRentStatus;
import com.sms_activate.rent.set_rent_status.SMSActivateSetRentStatusRequest;
import com.sms_activate.rent.set_rent_status.SMSActivateSetRentStatusResponse;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
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
   * Json deserializer and serializer.
   */
  private static final Gson gson = new Gson();

  /**
   * Special validator for server responses.
   */
  private static final SMSActivateValidator validator = new SMSActivateValidator();

  /**
   * Success value status.
   */
  private static final String SUCCESS_NAME_STATUS = "success";

  /**
   * Numbers reg expression.
   */
  private static final Pattern patternDigit = Pattern.compile("\\d+(?:[\\.,]\\d+)?");

  /**
   * Api key from site.
   */
  private final String apiKey;

  /**
   * Personal referral link (it's a numbers).
   */
  private String ref = null;

  /**
   * Constructor API sms-activate with API key and ref.
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
  public SMSActivateGetBalanceAndCashBackResponse getBalanceAndCashBack()
    throws SMSActivateBaseException {
    BigDecimal balance = getBalance();
    BigDecimal balanceAndCashBack = getBalanceByAction(SMSActivateAction.GET_BALANCE_AND_CASHBACK);

    return new SMSActivateGetBalanceAndCashBackResponse(balance, balanceAndCashBack.subtract(balance));
  }

  /**
   * Returns the available services.
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
  public SMSActivateGetNumbersStatusResponse getNumbersStatus()
    throws SMSActivateBaseException {
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

    if (operatorSet != null && !operatorSet.isEmpty()) {
      operatorSet.removeIf(x -> x == null || x.isEmpty());
      operator = String.join(",", operatorSet);
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_NUMBERS_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.COUNTRY, (countryId == null) ? null : String.valueOf(countryId))
      .append(SMSActivateURLKey.OPERATOR, operator);

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);


    Map<String, String> serviceMap = tryParseJson(data, new TypeToken<Map<String, String>>() {
    }.getType());
    Map<String, SMSActivateGetNumberStatusResponse> smsActivateGetNumbersStatusResponseMap = new HashMap<>();

    serviceMap.forEach((key, value) -> {
      String[] partsKey = key.split("_");

      smsActivateGetNumbersStatusResponseMap.put(partsKey[0], new SMSActivateGetNumberStatusResponse(
        Boolean.parseBoolean(partsKey[1]),
        Integer.parseInt(value),
        partsKey[0]
      ));
    });

    return new SMSActivateGetNumbersStatusResponse(smsActivateGetNumbersStatusResponseMap);
  }

  /**
   * Returns the activation by service, ref, countryId.
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
    return getNumber(countryId, service, null, null, false);
  }

  /**
   * Returns the activation by service, ref, countryId, phoneException, operator, forward
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
    @Nullable Set<String> operatorSet,
    @Nullable Set<String> phoneExceptionSet,
    boolean forward
  ) throws SMSActivateBaseException {
    if (countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    String phoneException = null;
    String operator = null;

    if (phoneExceptionSet != null && phoneExceptionSet.size() != 0) {
      phoneExceptionSet.removeIf(x -> x == null || x.isEmpty());
      phoneException = String.join(",", phoneExceptionSet);
    }
    if (operatorSet != null && operatorSet.size() != 0) {
      operatorSet.removeIf(x -> x == null || x.isEmpty());
      operator = String.join(",", operatorSet);
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_NUMBER);
    smsActivateURLBuilder.append(SMSActivateURLKey.REF, ref)
      .append(SMSActivateURLKey.SERVICE, service)
      .append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId))
      .append(SMSActivateURLKey.PHONE_EXCEPTION, phoneException)
      .append(SMSActivateURLKey.OPERATOR, operator)
      .append(SMSActivateURLKey.FORWARD, forward ? "1" : "0");

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);
    validator.throwExceptionWithBan(data);

    if (!data.contains("ACCESS")) {
      throw validator.getBaseExceptionByErrorNameOrUnknown(data);
    }

    String[] parts = data.split(":");

    String number = parts[2];
    int id = new BigDecimal(parts[1]).intValue();

    return new SMSActivateActivation(id, number, service, forward);
  }

  /**
   * Returns the specified object id by countryId, multiService, ref.<br/>
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
   *                                                <li>NOT_AVAILABLE  - if country not supported multiservice.</li>
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
   * Returns the specified object with activations id by countryId, multiService, ref.<br/>
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
   *                                                <li>NOT_AVAILABLE  - if country not supported multiservice.</li>
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
    multiServiceSet.removeIf(x -> x == null || x.isEmpty());

    String strMultiService = String.join(",", multiServiceSet);
    String strOperators = null;
    String strMultiForward = null;

    if (multiForwardList != null && !multiForwardList.isEmpty()) {
      multiForwardList.removeIf(Objects::isNull);
      strMultiForward = multiForwardList.stream().map(x -> x ? "1" : "0").collect(Collectors.joining(","));
    }
    if (operatorSet != null && !operatorSet.isEmpty()) {
      operatorSet.removeIf(x -> x == null || x.isEmpty());
      strOperators = String.join(",", operatorSet);
    }

    if (countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_MULTI_SERVICE_NUMBER);
    smsActivateURLBuilder.append(SMSActivateURLKey.REF, ref)
      .append(SMSActivateURLKey.MULTI_SERVICE, strMultiService)
      .append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId))
      .append(SMSActivateURLKey.MULTI_FORWARD, strMultiForward)
      .append(SMSActivateURLKey.OPERATOR, strOperators);

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);

    validator.throwExceptionWithBan(data);

    List<Map<String, Object>> activationMapList = tryParseJson(data, new TypeToken<List<Map<String, Object>>>() {
    }.getType());
    List<SMSActivateActivation> phoneList = new ArrayList<>();

    int indexForwardPhoneNumber = (multiForwardList == null) ? -1 : multiForwardList.indexOf(true); //index id where need forwarding

    for (int i = 0; i < activationMapList.size(); i++) {
      Map<String, Object> activationMap = activationMapList.get(i);

      int id = new BigDecimal(String.valueOf(activationMap.get("activation"))).intValue();
      String number = String.valueOf(activationMap.get(SMSActivateJsonKey.PHONE));
      String serviceName = String.valueOf(activationMap.get(SMSActivateJsonKey.SERVICE));

      phoneList.add(new SMSActivateActivation(
        id,
        number,
        serviceName,
        i == indexForwardPhoneNumber
      ));
    }

    return new SMSActivateGetMultiServiceNumberResponse(phoneList);
  }

  /**
   * Sets the status activation.
   *
   * @param id     id to set activation status (not be null).
   * @param status value to establish (not be null).
   *               <p>
   *               Get number using getNumber method after that the following actions are available:<br/>
   *               <em>CANCEL</em> - Cancel activation (if the number did not suit you)<br/>
   *               <em>SEND_READY_NUMBER</em> - Report that SMS has been sent (optional)
   *               </p>
   *               <p>
   *               To activate with status READY:<br/>
   *               <em>CANCEL</em> - Cancel activation
   *               </p>
   *               <p>
   *               Immediately after receiving the code:<br/>
   *               <em>REQUEST_ONE_MORE_CODE</em> - Request another SMS<br/>
   *               <em>FINISH</em> - Confirm SMS code and complete activation
   *               </p>
   *               <p>
   *               To activate with status RETRY_GET:<br/>
   *               <em>FINISH</em> - Confirm SMS code and complete activation
   *               </p>
   * @return access status activation.
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
  public SMSActivateSetStatusResponse setStatus(int id, @NotNull SMSActivateSetStatusRequest status)
    throws SMSActivateBaseException {
    return setStatus(id, status, false);
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
   * @param id      id to set activation status (not be null).
   * @param status  value to establish (not be null).
   * @param forward number is forwarding.
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
  public SMSActivateSetStatusResponse setStatus(
    int id,
    @NotNull SMSActivateSetStatusRequest status,
    boolean forward
  ) throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.SET_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.STATUS, String.valueOf(status.getId()))
      .append(SMSActivateURLKey.ID, String.valueOf(id))
      .append(SMSActivateURLKey.FORWARD, forward ? "1" : "0");

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);

    SMSActivateAccessStatus smsActivateAccessStatus = SMSActivateAccessStatus.getStatusByName(data);

    if (smsActivateAccessStatus != SMSActivateAccessStatus.UNKNOWN) {
      return new SMSActivateSetStatusResponse(smsActivateAccessStatus);
    } else {
      throw validator.getBaseExceptionByErrorNameOrUnknown(data);
    }
  }

  /**
   * Returns the state by id activation.
   *
   * @param id to get activation state.
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
  public SMSActivateGetStatusResponse getStatus(int id)
    throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(id));

    String code = null;
    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);


    if (data.contains(":")) {
      String[] parts = data.split(":");

      data = parts[0];
      code = parts[1];
    }

    SMSActivateGetStatus status = SMSActivateGetStatus.getStatusByName(data);

    if (status != SMSActivateGetStatus.UNKNOWN) {
      return new SMSActivateGetStatusResponse(status, code);
    } else {
      throw validator.getBaseExceptionByErrorNameOrUnknown(data);
    }
  }

  /**
   * Returns the full text sms.
   *
   * @param id id activation.
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
  public SMSActivateGetFullSmsResponse getFullSms(int id)
    throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_FULL_SMS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(id));

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);

    SMSActivateGetFullTypeResponse smsActivateGetFullTypeResponse = SMSActivateGetFullTypeResponse.getStatusByName(data);

    if (smsActivateGetFullTypeResponse != SMSActivateGetFullTypeResponse.UNKNOWN) {
      if (smsActivateGetFullTypeResponse == SMSActivateGetFullTypeResponse.FULL_SMS) {
        return new SMSActivateGetFullSmsResponse(data.split(":")[1], smsActivateGetFullTypeResponse);
      } else {
        return new SMSActivateGetFullSmsResponse("", smsActivateGetFullTypeResponse);
      }
    } else {
      throw validator.getBaseExceptionByErrorNameOrUnknown(data);
    }
  }

  /**
   * Returns the all actual prices by country.
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
  public SMSActivateGetPricesResponse getPrices() throws SMSActivateBaseException {
    return getPrices(0, null);
  }

  /**
   * Returns the actual rent prices by country.
   *
   * @param countryId id number (default 0).
   * @param service   service for needed price list (default null).
   *                  <pre>{@code null, null -> all service and all country.}</pre>
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
  public SMSActivateGetPricesResponse getPrices(int countryId, @Nullable String service)
    throws SMSActivateBaseException {
    if (countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_PRICES);
    smsActivateURLBuilder.append(SMSActivateURLKey.SERVICE, service)
      .append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId));

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);


    Map<String, Map<String, Map<String, Double>>> countryMap = tryParseJson(data,
      new TypeToken<Map<String, Map<String, Map<String, Double>>>>() {
      }.getType());
    Map<Integer, Map<String, SMSActivateGetPriceResponse>> smsActivateGetPriceMapList = new HashMap<>();

    countryMap.forEach((countryCode, serviceMap) -> {
      Map<String, SMSActivateGetPriceResponse> smsActivateGetPriceResponseMap = new HashMap<>();

      serviceMap.forEach((shortName, value) -> smsActivateGetPriceResponseMap.put(shortName, new SMSActivateGetPriceResponse(
        shortName,
        BigDecimal.valueOf(value.get(SMSActivateJsonKey.COST)),
        BigDecimal.valueOf(value.get(SMSActivateJsonKey.COUNT)).intValue()
      )));

      smsActivateGetPriceMapList.put(Integer.valueOf(countryCode), smsActivateGetPriceResponseMap);
    });

    return new SMSActivateGetPricesResponse(smsActivateGetPriceMapList);
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
    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);

    Map<String, Map<String, Object>> countryInformationMap = tryParseJson(data, new TypeToken<Map<String, Map<String, Object>>>() {
    }.getType());
    List<SMSActivateGetCountryResponse> countryList = new ArrayList<>();

    for (Map<String, Object> countryMap : countryInformationMap.values()) {
      int id = new BigDecimal(countryMap.get(SMSActivateJsonKey.ID).toString()).intValue();

      String rus = String.valueOf(countryMap.get(SMSActivateJsonKey.RUS));
      String eng = String.valueOf(countryMap.get(SMSActivateJsonKey.ENG));
      String chn = String.valueOf(countryMap.get(SMSActivateJsonKey.CHN));

      boolean isVisible = Boolean.parseBoolean(String.valueOf(countryMap.get(SMSActivateJsonKey.VISIBLE)));
      boolean isSupportRetry = Boolean.parseBoolean(String.valueOf(countryMap.get(SMSActivateJsonKey.RETRY)));
      boolean isSupportRent = Boolean.parseBoolean(String.valueOf(countryMap.get(SMSActivateJsonKey.RENT)));
      boolean isSupportMultiService = Boolean.parseBoolean(String.valueOf(countryMap.get(SMSActivateJsonKey.MULTI_SERVICE)));

      countryList.add(new SMSActivateGetCountryResponse(id, rus, eng, chn,
        isVisible, isSupportRetry, isSupportRent, isSupportMultiService));
    }

    return new SMSActivateGetCountriesResponse(countryList);
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
    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);

    Map<String, String> response = tryParseJson(data, new TypeToken<Map<String, String>>() {
    }.getType());

    SMSActivateQiwiStatus status = SMSActivateQiwiStatus.getStatusByName(response.get(SMSActivateJsonKey.STATUS));

    if (status == SMSActivateQiwiStatus.UNKNOWN) {
      throw validator.getBaseExceptionByErrorNameOrUnknown(response.get(SMSActivateJsonKey.STATUS));
    }

    return new SMSActivateGetQiwiRequisitesResponse(
      status, response.get(SMSActivateJsonKey.COMMENT),
      response.get(SMSActivateJsonKey.WALLET), response.get(SMSActivateJsonKey.UP_TO_DATE)
    );
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
  public SMSActivateActivation getAdditionalService(int id, @NotNull String service)
    throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_ADDITIONAL_SERVICE);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(id))
      .append(SMSActivateURLKey.SERVICE, service);

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);

    if (data.contains(SMSActivateJsonKey.ADDITIONAL)) {
      String[] parts = data.split(":");
      String number = parts[2];
      id = new BigDecimal(parts[1]).intValue();

      return new SMSActivateActivation(id, number, service, false);
    } else {
      throw validator.getBaseExceptionByErrorNameOrUnknown(data);
    }
  }

  /**
   * Returns the first ten current activation.
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
  public SMSActivateGetCurrentActivationsResponse getCurrentActivations()
    throws SMSActivateBaseException {
    return getCurrentActivations(1, SMSActivateOrderBy.ASC);
  }

  /**
   * Returns the list current activation where contains by 10 activation.
   *
   * @param page number page (default 1).
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
  public SMSActivateGetCurrentActivationsResponse getCurrentActivations(int page, @NotNull SMSActivateOrderBy SMSActivateOrderBy)
    throws SMSActivateBaseException {
    if (page <= 0) {
      throw new SMSActivateWrongParameterException("Number page can be positive.", "Номер страницы должен быть позитивным.");
    }

    int len = page * 10;
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_CURRENT_ACTIVATION);
    smsActivateURLBuilder.append(SMSActivateURLKey.START, String.valueOf(page * 10 - 10))
      .append(SMSActivateURLKey.LENGTH, String.valueOf(len))
      .append(SMSActivateURLKey.ORDER, SMSActivateURLKey.ID.getName())
      .append(SMSActivateURLKey.ORDER_BY, SMSActivateOrderBy.getSortType());

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);
    Map<String, Object> responseMap = tryParseJson(data, new TypeToken<Map<String, Object>>() {
    }.getType());

    if (!responseMap.get(SMSActivateJsonKey.STATUS).toString().equalsIgnoreCase(SUCCESS_NAME_STATUS)) {
      return new SMSActivateGetCurrentActivationsResponse(new HashMap<>(), false);
    }

    List<Map<String, Object>> currentActivationMapList = (List<Map<String, Object>>) responseMap.get(SMSActivateJsonKey.ARRAY);
    Map<Integer, SMSActivateGetCurrentActivationResponse> smsActivateGetCurrentActivationResponseMap = new HashMap<>();

    for (Map<String, Object> currentActivationMap : currentActivationMapList) {
      int id = new BigDecimal(String.valueOf(currentActivationMap.get(SMSActivateJsonKey.ID))).intValue();
      boolean forward = Boolean.parseBoolean(String.valueOf(currentActivationMap.get(SMSActivateJsonKey.FORWARD)));

      String number = String.valueOf(currentActivationMap.get(SMSActivateJsonKey.PHONE));
      String serviceName = String.valueOf(currentActivationMap.get(SMSActivateJsonKey.SERVICE));
      int countryCode = Integer.parseInt(String.valueOf(currentActivationMap.get(SMSActivateJsonKey.COUNTRY)));

      smsActivateGetCurrentActivationResponseMap.put(id, new SMSActivateGetCurrentActivationResponse(
        id, forward, number, countryCode, serviceName));
    }

    int count = new BigDecimal(responseMap.get(SMSActivateJsonKey.QUANT).toString()).intValue();
    return new SMSActivateGetCurrentActivationsResponse(smsActivateGetCurrentActivationResponseMap, len < count);
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
  public SMSActivateGetRentServicesAndCountriesResponse getRentServicesAndCountries()
    throws SMSActivateBaseException {
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
      throw new SMSActivateWrongParameterException("Time can't be negative or equals 0.", "Время не может быть меньше или равно 0");
    }
    if (countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    String operator = null;

    if (operatorSet != null && !operatorSet.isEmpty()) {
      operatorSet.removeIf(x -> x == null || x.isEmpty());
      operator = String.join(",", operatorSet);
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_SERVICES_AND_COUNTRIES);
    smsActivateURLBuilder.append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId))
      .append(SMSActivateURLKey.OPERATOR, operator)
      .append(SMSActivateURLKey.RENT_TIME, String.valueOf(time));

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);
    Map<String, Object> rentCountriesServices = tryParseJson(data, new TypeToken<Map<String, Object>>() {
    }.getType());

    if (rentCountriesServices.containsKey(SMSActivateJsonKey.MESSAGE)) {
      throw validator.getBaseExceptionByErrorNameOrUnknown(rentCountriesServices.get(SMSActivateJsonKey.MESSAGE).toString());
    }

    Map<String, Object> countryMap = (Map<String, Object>) rentCountriesServices.get(SMSActivateJsonKey.COUNTRIES);
    Map<String, Object> operatorMap = (Map<String, Object>) rentCountriesServices.get(SMSActivateJsonKey.OPERATORS);
    Map<String, Object> servicesMap = (Map<String, Object>) rentCountriesServices.get(SMSActivateJsonKey.SERVICES);

    Set<String> operatorNameSet = new HashSet<>();
    Set<Integer> countryIdSet = new HashSet<>();
    Map<String, SMSActivateRentService> smsActivateRentServiceMap = new HashMap<>();

    for (Object countryCode : countryMap.values()) {
      countryIdSet.add(new BigDecimal(countryCode.toString()).intValue());
    }

    for (Object name : operatorMap.values()) {
      operatorNameSet.add(name.toString());
    }

    servicesMap.forEach((shortName, service) -> {
      Map<String, Object> serviceMap = (Map<String, Object>) service;
      int countNumber = new BigDecimal(serviceMap.get(SMSActivateJsonKey.QUANT).toString()).intValue();

      smsActivateRentServiceMap.put(shortName, new SMSActivateRentService(
        shortName,
        new BigDecimal(serviceMap.get(SMSActivateJsonKey.COST).toString()),
        countNumber
      ));
    });

    return new SMSActivateGetRentServicesAndCountriesResponse(
      operatorNameSet, countryIdSet, smsActivateRentServiceMap
    );
  }

  /**
   * Returns the object rent.
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
  public SMSActivateGetRentNumberResponse getRentNumber(@NotNull String service)
    throws SMSActivateBaseException {
    return getRentNumber(0, service, null, MINIMAL_RENT_TIME, null);
  }

  /**
   * Returns the object rent.
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
  public SMSActivateGetRentNumberResponse getRentNumber(
    int countryId,
    @NotNull String service,
    @Nullable String operator,
    int time,
    @Nullable String urlWebhook
  ) throws SMSActivateBaseException {
    if (countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }
    if (time < MINIMAL_RENT_TIME) {
      throw new SMSActivateWrongParameterException(
        String.format("The rental time cannot be less than %d.", MINIMAL_RENT_TIME),
        String.format("Время аренды не может быть меньше чем %d.", MINIMAL_RENT_TIME)
      );
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_NUMBER);
    smsActivateURLBuilder.append(SMSActivateURLKey.RENT_TIME, String.valueOf(time))
      .append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId))
      .append(SMSActivateURLKey.OPERATOR, operator)
      .append(SMSActivateURLKey.URL, urlWebhook)
      .append(SMSActivateURLKey.SERVICE, service);


    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);

    Map<String, Object> responseMap = tryParseJson(data, new TypeToken<Map<String, Object>>() {
    }.getType());

    String status = String.valueOf(responseMap.get(SMSActivateJsonKey.STATUS));

    if (!status.equalsIgnoreCase(SUCCESS_NAME_STATUS)) {
      throw validator.getBaseExceptionByErrorNameOrUnknown(String.valueOf(responseMap.get(SMSActivateJsonKey.MESSAGE)));
    }

    Map<String, Object> phoneMap = (Map<String, Object>) responseMap.get(SMSActivateJsonKey.PHONE);

    String number = phoneMap.get(SMSActivateJsonKey.NUMBER).toString();
    int id = new BigDecimal(phoneMap.get(SMSActivateJsonKey.ID).toString()).intValue();
    String endDate = phoneMap.get(SMSActivateJsonKey.END_DATE).toString();

    return new SMSActivateGetRentNumberResponse(id, number, endDate, service);
  }

  /**
   * Returns the list sms.
   *
   * @param id id received in response when ordering a number.
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
  public SMSActivateGetRentStatusResponse getRentStatus(int id)
    throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(id));

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);

    Map<String, Object> responseMap = tryParseJson(data, new TypeToken<Map<String, Object>>() {
    }.getType());

    String status = String.valueOf(responseMap.get(SMSActivateJsonKey.STATUS));

    if (!status.equalsIgnoreCase(SUCCESS_NAME_STATUS)) {
      throw validator.getBaseExceptionByErrorNameOrUnknown(String.valueOf(responseMap.get(SMSActivateJsonKey.MESSAGE)));
    }

    Map<String, Map<String, Object>> valuesMap = (Map<String, Map<String, Object>>) responseMap.get(SMSActivateJsonKey.VALUES);
    List<SMSActivateSMS> smsList = new ArrayList<>();

    for (Map<String, Object> phoneMap : valuesMap.values()) {
      String number = phoneMap.get(SMSActivateJsonKey.PHONE_FROM).toString();
      String text = phoneMap.get(SMSActivateJsonKey.TEXT).toString();
      String serviceShortName = phoneMap.get(SMSActivateJsonKey.SERVICE).toString();
      String date = phoneMap.get(SMSActivateJsonKey.DATE).toString();

      smsList.add(new SMSActivateSMS(number, text, date, serviceShortName));
    }

    return new SMSActivateGetRentStatusResponse(
      Integer.parseInt(String.valueOf(responseMap.get(SMSActivateJsonKey.QUALITY))), smsList);
  }

  /**
   * Sets the status on rent.
   *
   * @param id     id activation for set status rent.
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
   *                                                <li>INCORECT_STATUS - if status is incorrect.</li>
   *                                                <li>CANT_CANCEL - if it is impossible to cancel the rent (more than 20 min.).</li>
   *                                                <li>ALREADY_FINISH - if rent is finished.</li>
   *                                                <li>ALREADY_CANCEL - if rent is canceled.</li>
   *                                                <li>INVALID_PHONE - if id is incorrect.</li>
   *                                              </ul>
   *                                            </p>
   *                                            </p>
   */
  @NotNull
  public SMSActivateSetRentStatusResponse setRentStatus(int id, @NotNull SMSActivateSetRentStatusRequest status)
    throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.SET_RENT_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(id))
      .append(SMSActivateURLKey.STATUS, String.valueOf(status.getId()));

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);
    Map<String, String> responseMap = tryParseJson(data, new TypeToken<Map<String, String>>() {
    }.getType());
    SMSActivateRentStatus smsActivateRentStatus = SMSActivateRentStatus.getStatusByName(responseMap.get(SMSActivateJsonKey.STATUS));

    if (smsActivateRentStatus == SMSActivateRentStatus.SUCCESS) {
      return new SMSActivateSetRentStatusResponse(smsActivateRentStatus);
    } else {
      throw validator.getBaseExceptionByErrorNameOrUnknown(responseMap.get(SMSActivateJsonKey.MESSAGE));
    }
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
  public SMSActivateGetRentListResponse getRentList()
    throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_LIST);

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);
    Map<String, Object> responseMap = tryParseJson(data, new TypeToken<Map<String, Object>>() {
    }.getType());

    String status = String.valueOf(responseMap.get(SMSActivateJsonKey.STATUS));

    if (!status.equalsIgnoreCase(SUCCESS_NAME_STATUS)) {
      throw validator.getBaseExceptionByErrorNameOrUnknown(String.valueOf(responseMap.get(SMSActivateJsonKey.MESSAGE)));
    }

    Map<String, Map<String, Object>> valuesMap = (Map<String, Map<String, Object>>) responseMap.get(SMSActivateJsonKey.VALUES);
    List<SMSActivateGetRentResponse> smsActivateGetRentResponseList = new ArrayList<>();

    for (Map<String, Object> phoneMap : valuesMap.values()) {
      String number = phoneMap.get(SMSActivateJsonKey.PHONE).toString();
      int id = new BigDecimal(phoneMap.get(SMSActivateJsonKey.ID).toString()).intValue();

      smsActivateGetRentResponseList.add(new SMSActivateGetRentResponse(id, number));
    }

    return new SMSActivateGetRentListResponse(smsActivateGetRentResponseList);
  }

  /**
   * Returns the current account balance by specific action.
   *
   * @param smsActivateAction name specific action to get balance.
   * @return current account balance.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  private BigDecimal getBalanceByAction(@NotNull SMSActivateAction smsActivateAction)
    throws SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, smsActivateAction);
    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder, validator);
    Matcher matcher = patternDigit.matcher(data);

    if (!matcher.find()) {
      throw new SMSActivateBaseException("Error: " + data, "Error: " + data);
    }

    return new BigDecimal(matcher.group());
  }

  /**
   * If string is json object then parsing string to json and cast to type else throw exception.
   *
   * @param data   data in string.
   * @param typeOf type to cast after parse.
   * @param <T>    type to return.
   * @return Object from string.
   * @throws SMSActivateBaseException if response from server not equals success.
   */
  @NotNull
  private <T> T tryParseJson(@NotNull String data, @NotNull Type typeOf) throws SMSActivateBaseException {
    try {
      return gson.fromJson(data, typeOf);
    } catch (JsonSyntaxException e) {
      throw validator.getBaseExceptionByErrorNameOrUnknown(data);
    }
  }
}
