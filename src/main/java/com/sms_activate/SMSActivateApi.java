package com.sms_activate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sms_activate.arch.activation.SMSActivateActivation;
import com.sms_activate.arch.activation.SMSActivateGetFillSmsResponse;
import com.sms_activate.arch.activation.SMSActivateGetMultiServiceNumberResponse;
import com.sms_activate.arch.activation.balance.SMSActivateGetBalanceAndCashBackResponse;
import com.sms_activate.arch.activation.balance.SMSActivateGetBalanceResponse;
import com.sms_activate.arch.activation.country.SMSActivateGetCountriesResponse;
import com.sms_activate.arch.activation.country.SMSActivateGetCountryResponse;
import com.sms_activate.arch.activation.current_activation.SMSActivateGetCurrentActivationResponse;
import com.sms_activate.arch.activation.current_activation.SMSActivateGetCurrentActivationsResponse;
import com.sms_activate.arch.activation.get_prices.SMSActivateGetPriceResponse;
import com.sms_activate.arch.activation.get_prices.SMSActivateGetPricesResponse;
import com.sms_activate.arch.activation.get_status.SMSActivateGetStatus;
import com.sms_activate.arch.activation.get_status.SMSActivateGetStatusResponse;
import com.sms_activate.arch.activation.numbers_status.SMSActivateGetNumberStatusResponse;
import com.sms_activate.arch.activation.numbers_status.SMSActivateGetNumbersStatusResponse;
import com.sms_activate.arch.activation.set_status.SMSActivateAccessStatus;
import com.sms_activate.arch.activation.set_status.SMSActivateSetStatusRequest;
import com.sms_activate.arch.activation.set_status.SMSActivateSetStatusResponse;
import com.sms_activate.arch.main_response.SMSActivateMainResponse;
import com.sms_activate.arch.main_response.SMSActivateMainStatusResponse;
import com.sms_activate.arch.qiwi.SMSActivateGetQiwiRequisitesResponse;
import com.sms_activate.arch.rent.SMSActivateGetRentNumberResponse;
import com.sms_activate.arch.rent.get_rent_list.SMSActivateGetRentListResponse;
import com.sms_activate.arch.rent.get_rent_list.SMSActivateGetRentResponse;
import com.sms_activate.arch.rent.get_rent_services_and_countries.SMSActivateGetRentServicesAndCountriesResponse;
import com.sms_activate.arch.rent.get_rent_services_and_countries.SMSActivateRentService;
import com.sms_activate.arch.rent.get_rent_sms.SMSActivateGetRentStatusResponse;
import com.sms_activate.arch.rent.get_rent_sms.SMSActivateSMS;
import com.sms_activate.arch.error.SMSActivateWrongParameterException;
import com.sms_activate.arch.error.SMSActivateBaseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;


/**
 * TODO:
 *  -refactoring validation:
 *  --activation
 *  --rent
 */


/**
 * <p>The class is a high-level API for interacting with the SMS-Activate API.
 * API capabilities allow you to work with the service through your software.
 * Before starting work, you must have an API key and a referral link to use all the API capabilities.</p>
 *
 * <p>All methods and constructor for this class throw SMSActivateWrongParameterException</p>
 *
 * @see SMSActivateWrongParameterException
 */
public class SMSActivateApi {
  /**
   * Json deserializer and serializer.
   */
  private static final Gson gson = new Gson();

  /**
   * Validator data.
   */
  private static final Validator validator = new Validator();

  /**
   * Api key from personal
   */
  private final String apiKey;

  /**
   * Personal referral link.
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
      throw new SMSActivateWrongParameterException("API-key can't be empty.", "API ключ не может быть пустым.");
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
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateGetBalanceResponse getBalance() throws IOException, SMSActivateBaseException {
    BigDecimal balance = getBalance(SMSActivateAction.GET_BALANCE);
    return new SMSActivateGetBalanceResponse(balance);
  }

  /**
   * Returns the current account balance plus cashBack.
   *
   * @return current account balance plus cashBack.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateGetBalanceAndCashBackResponse getBalanceAndCashBack()
      throws IOException, SMSActivateBaseException {
    BigDecimal balance = getBalance().getBalance();
    BigDecimal balanceAndCashBack = getBalance(SMSActivateAction.GET_BALANCE_AND_CASHBACK);

    return new SMSActivateGetBalanceAndCashBackResponse(balance, balanceAndCashBack.subtract(balance));
  }

  /**
   * Returns a list counts of available services.
   *
   * @return list counts of available services.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateGetNumbersStatusResponse getNumbersStatus()
      throws IOException, SMSActivateBaseException {
    return getNumbersStatus(null, null);
  }

  /**
   * Return a list counts of available services by country and operator.
   *
   * @param countryId id country.
   * @param operator  name operator mobile network (not be null).
   * @return list counts of available services by county and operator (not be null).
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateGetNumbersStatusResponse getNumbersStatus(@Nullable Integer countryId, @Nullable String operator)
      throws IOException, SMSActivateBaseException {
    if (countryId != null && countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_NUMBERS_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.COUNTRY, (countryId == null) ? null : String.valueOf(countryId))
        .append(SMSActivateURLKey.OPERATOR, operator);

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    Map<String, String> serviceMap = gson.fromJson(data, new TypeToken<Map<String, String>>() {
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
   * Returns the id by service, ref, countryId.
   *
   * @param service   service name for activation.
   * @param countryId id country.
   * @return id activation for activation.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws BannedException         if account has been banned.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public SMSActivateActivation getNumber(@NotNull String service, int countryId) throws IOException, SMSActivateBaseException {
    return getNumber(service, countryId, null, null, false);
  }

  /**
   * Returns the id number by service, ref, countryId, phoneException, operator, forward
   *
   * @param service        service name for activation.
   * @param countryId      id country.
   * @param phoneException excepted id number prefix. Specify separated by commas.
   *                       <pre>{@code   7918,7900111}</pre>
   * @param operator       mobile operator. May be specify separated by commas.
   * @param forward        is it necessary to request a number with forwarding.
   * @return id activation for activation.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   * @throws BannedException         if account has been banned.
   */
  @NotNull
  public SMSActivateActivation getNumber(
      @NotNull String service,
      int countryId,
      @Nullable String phoneException,
      @Nullable String operator,
      boolean forward
  ) throws IOException, SMSActivateBaseException {
    if (countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_NUMBER);
    smsActivateURLBuilder.append(SMSActivateURLKey.REF, ref)
        .append(SMSActivateURLKey.SERVICE, service)
        .append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId))
        .append(SMSActivateURLKey.PHONE_EXCEPTION, phoneException)
        .append(SMSActivateURLKey.OPERATOR, operator)
        .append(SMSActivateURLKey.FORWARD, forward ? "1" : "0");

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);
    validator.throwNoNumbersOrNoBalanceExceptionByName(data);

    if (data.contains("BANNED")) {
      String date = data.split(":")[1];
      throw new BannedException(date);
    }

    String[] parts = data.split(":");

    String number = parts[2];
    int id = new BigDecimal(parts[1]).intValue();

    return new SMSActivateActivation(id, number, service, forward);
  }

  /**
   * Returns the list id by countryId, multiService, ref.<br/>
   * Separator for multiService is commas. <br/>
   * <pre> multiService -> vk,av,go,tg. </pre>
   *
   * @param multiService services for ordering (not be null).
   * @param countryId    id country.
   * @return list id.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws BannedException         if account has been banned.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public SMSActivateGetMultiServiceNumberResponse getMultiServiceNumber(@NotNull String multiService, int countryId)
      throws IOException, SMSActivateBaseException {
    return getMultiServiceNumber(multiService, countryId, null, null);
  }

  /**
   * Returns the list id by countryId, multiService, ref.<br/>
   * Separator for multiService, multiForward and operator is commas. <br/>
   * <pre>multiService -> vk,av,go,tg<br/>multiForward -> 0,0,1,0; 0,0,0,0 - correct; 0,1,1,0 - incorrect.</pre>
   *
   * @param multiService services for ordering (not be null).
   * @param countryId    id country.
   * @param multiForward is it necessary to request a number with forwarding.
   * @param operator     mobile operator.
   * @return list id.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws BannedException         if account has been banned.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public SMSActivateGetMultiServiceNumberResponse getMultiServiceNumber(
      @NotNull String multiService,
      int countryId,
      @Nullable String multiForward,
      @Nullable String operator
  ) throws IOException, SMSActivateBaseException {
    String trimMultiService = multiService.replace("\\s", "");

    if (multiForward != null) {
      multiForward = multiForward.replace(" ", "");
    }
    if (operator != null) {
      operator = operator.replace(" ", "");
    }

    if (countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_MULTI_SERVICE_NUMBER);
    smsActivateURLBuilder.append(SMSActivateURLKey.REF, ref)
        .append(SMSActivateURLKey.MULTI_SERVICE, trimMultiService)
        .append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId))
        .append(SMSActivateURLKey.MULTI_FORWARD, multiForward)
        .append(SMSActivateURLKey.OPERATOR, operator);

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);
    validator.throwNoNumbersOrNoBalanceExceptionByName(data);

    if (data.contains("BANNED")) {
      String date = data.split(":")[1];
      throw new BannedException(date);
    }

    List<Map<String, Object>> activationMapList = gson.fromJson(data, new TypeToken<List<Map<String, Object>>>() {}.getType());
    List<SMSActivateActivation> phoneList = new ArrayList<>();

    int indexForwardPhoneNumber = (multiForward == null) ? -1 : Arrays.asList(multiForward.split(",")).indexOf("1"); //index id where need forwarding

    for (int i = 0; i < activationMapList.size(); i++) {
      Map<String, Object> activationMap = activationMapList.get(i);

      int id = new BigDecimal(String.valueOf(activationMap.get("activation"))).intValue();
      String number = String.valueOf(activationMap.get("phone"));
      String serviceName = String.valueOf(activationMap.get("service"));

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
   * <p>
   * Get number using getNumber method after that the following actions are available:<br/>
   * <em>CANCEL</em> - Cancel activation (if the number did not suit you)<br/>
   * <em>NUMBER READINESS CONFIRMED</em> - Report that SMS has been sent (optional)
   * </p>
   * <p>
   * To activate with status 1:<br/>
   * <em>CANCEL</em> - Cancel activation
   * </p>
   * <p>
   * Immediately after receiving the code:<br/>
   * <em>REQUEST ONE MORE SMS</em> - Request another SMS<br/>
   * <em>FINISH</em> - Confirm SMS code and complete activation
   * </p>
   * <p>
   * To activate with status 3:<br/>
   * <em>FINISH</em> - Confirm SMS code and complete activation
   * </p>
   *
   * @param id  id to set activation status (not be null).
   * @param status value to establish (not be null).
   * @return access activation.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateSetStatusResponse setStatus(int id, @NotNull SMSActivateSetStatusRequest status)
      throws IOException, SMSActivateBaseException {
    return setStatus(id, status, false);
  }

  /**
   * Sets the status activation.
   *
   * @param id   id to set activation status (not be null).
   * @param status  value to establish (not be null).
   * @param forward number is forwarding.
   * @return access activation.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateSetStatusResponse setStatus(
      int id,
      @NotNull SMSActivateSetStatusRequest status,
      boolean forward
  ) throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.SET_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.STATUS, String.valueOf(status.getId()))
        .append(SMSActivateURLKey.ID, String.valueOf(id))
        .append(SMSActivateURLKey.FORWARD, forward ? "1" : "0");

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    return new SMSActivateSetStatusResponse(SMSActivateAccessStatus.getStatusByName(data));
  }

  /**
   * Returns the state id activation.
   *
   * @param id id id to get activation state (not be null).
   * @return state activation.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateGetStatusResponse getStatus(int id)
      throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(id));

    String code = null;
    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    if (data.contains(":")) {
      String[] parts = data.split(":");

      data = parts[0];
      code = parts[1];
    }

    SMSActivateGetStatus status = SMSActivateGetStatus.getStatusByName(data);

    return new SMSActivateGetStatusResponse(status, code);
  }

  /**
   * Returns the full text sms.
   *
   * @param id id activation.
   * @return full text sms.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws WrongResponseException  if server response is not correct.
   */
  @NotNull
  public SMSActivateGetFillSmsResponse getFullSms(int id)
      throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_FULL_SMS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(id));

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    if (data.contains("FULL")) {
      return new SMSActivateGetFillSmsResponse(data.split(":")[1]);
    } else {
      SMSActivateMainStatusResponse smsActivateMainStatusResponse = SMSActivateMainStatusResponse.getStatusByName(data);

      if (smsActivateMainStatusResponse == SMSActivateMainStatusResponse.UNKNOWN) {
        ;
      }

      return (SMSActivateGetFillSmsResponse) new SMSActivateMainResponse(smsActivateMainStatusResponse);
    }
  }

  /**
   * Returns the all actual prices by country.
   *
   * @return price list country.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateGetPricesResponse getPrices() throws IOException, SMSActivateBaseException {
    return getPrices(null, null);
  }

  /**
   * Returns the actual rent prices by country.
   *
   * @param service   service for needed price list (default null).
   *                  <pre>{@code null, null -> all service and all country.}</pre>
   * @param countryId id number (default null).
   * @return price list country.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateGetPricesResponse getPrices(@Nullable Service service, @Nullable Integer countryId)
      throws IOException, SMSActivateBaseException {
    if (countryId != null && countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    String shortNameService = (service == null) ? null : service.getShortName();

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_PRICES);
    smsActivateURLBuilder.append(SMSActivateURLKey.SERVICE, shortNameService)
        .append(SMSActivateURLKey.COUNTRY, (countryId == null) ? null : String.valueOf(countryId));

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);
    Map<String, Map<String, Map<String, Double>>> countryMap = gson.fromJson(data,
        new TypeToken<Map<String, Map<String, Map<String, Double>>>>() {}.getType());
    List<Map<String, SMSActivateGetPriceResponse>> smsActivateGetPriceMapList = new ArrayList<>();

    countryMap.forEach((countryCode, serviceMap) -> {
      Map<String, SMSActivateGetPriceResponse> smsActivateGetPriceResponseMap = new HashMap<>();

      serviceMap.forEach((shortName, value) -> smsActivateGetPriceResponseMap.put(shortName, new SMSActivateGetPriceResponse(
          shortName,
          BigDecimal.valueOf(value.get("cost")),
          BigDecimal.valueOf(value.get("count")).intValue()
      )));

      smsActivateGetPriceMapList.add(smsActivateGetPriceResponseMap);
    });

    return new SMSActivateGetPricesResponse(smsActivateGetPriceMapList);
  }

  /**
   * Returns the country information.
   *
   * @return country information.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateGetCountriesResponse getCountries() throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_COUNTRIES);
    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);
    Map<String, Map<String, Object>> countryInformationMap = gson.fromJson(data, new TypeToken<Map<String, Map<String, Object>>>() {}.getType());

    List<SMSActivateGetCountryResponse> countryList = new ArrayList<>();

    for (Map<String, Object> countryMap : countryInformationMap.values()) {
      int id = new BigDecimal(countryMap.get("id").toString()).intValue();

      String rus = String.valueOf(countryMap.get("rus"));
      String eng = String.valueOf(countryMap.get("eng"));
      String chn = String.valueOf(countryMap.get("chn"));

      boolean isVisible = Boolean.parseBoolean(String.valueOf(countryMap.get("visible")));
      boolean isSupportRetry = Boolean.parseBoolean(String.valueOf(countryMap.get("retry")));
      boolean isSupportRent = Boolean.parseBoolean(String.valueOf(countryMap.get("rent")));
      boolean isSupportMultiService = Boolean.parseBoolean(String.valueOf(countryMap.get("multiService")));

      countryList.add(new SMSActivateGetCountryResponse(id, rus, eng, chn,
          isVisible, isSupportRetry, isSupportRent, isSupportMultiService));
    }

    return new SMSActivateGetCountriesResponse(countryList);
  }

  /**
   * Returns the qiwi response with data on wallet.
   *
   * @return qiwi response with data on wallet
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateGetQiwiRequisitesResponse getQiwiRequisites() throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_QIWI_REQUISITES);
    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);
    return gson.fromJson(data, new TypeToken<SMSActivateGetQiwiRequisitesResponse>() {}.getType());
  }

  /**
   * Returns the id activation for additional service by forwarding.
   *
   * @param id id activation.
   * @return id activation for additional service by forwarding
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateActivation getAdditionalService(int id, @NotNull String serviceName)
      throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_ADDITIONAL_SERVICE);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(id))
        .append(SMSActivateURLKey.SERVICE, serviceName);

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    String[] parts = data.split(":");
    String number = parts[2];
    id = new BigDecimal(parts[1]).intValue();

    return new SMSActivateActivation(id, number, serviceName, false);
  }

  /**
   * Returns the list current activation.
   *
   * @return list with first 10 activation.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateGetCurrentActivationsResponse getCurrentActivations()
      throws IOException, SMSActivateBaseException {
    return getCurrentActivations(0, 10);
  }

  /**
   * Returns the list current activation.
   *
   * @param start  (default 0).
   * @param length (default 10).
   * @return returns the list activation.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateGetCurrentActivationsResponse getCurrentActivations(int start, int length)
      throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_CURRENT_ACTIVATION);
    smsActivateURLBuilder.append(SMSActivateURLKey.START, String.valueOf(start))
        .append(SMSActivateURLKey.LENGTH, String.valueOf(length))
        .append(SMSActivateURLKey.ORDER, SMSActivateURLKey.ID.getName())
        .append(SMSActivateURLKey.ORDER_BY, "asc");

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    Map<String, Object> responseMap = gson.fromJson(data, new TypeToken<Map<String, Object>>() {
    }.getType());

    if (responseMap.get("status").toString().equalsIgnoreCase("fail")) {
      return new SMSActivateGetCurrentActivationsResponse(new HashMap<>());
    }

    List<Map<String, Object>> currentActivationMapList = (List<Map<String, Object>>) responseMap.get("array");
    Map<Integer, SMSActivateGetCurrentActivationResponse> smsActivateGetCurrentActivationResponseMap = new HashMap<>();

    for (Map<String, Object> currentActivationMap : currentActivationMapList) {
      int id = new BigDecimal(String.valueOf(currentActivationMap.get("id"))).intValue();
      boolean forward = Boolean.parseBoolean(String.valueOf(currentActivationMap.get("forward")));

      String number = String.valueOf(currentActivationMap.get("phone"));
      String serviceName = String.valueOf(currentActivationMap.get("service"));
      String countryName = String.valueOf(currentActivationMap.get("countryName"));

      smsActivateGetCurrentActivationResponseMap.put(id, new SMSActivateGetCurrentActivationResponse(
          id, forward, number, countryName, serviceName));
    }

    return new SMSActivateGetCurrentActivationsResponse(smsActivateGetCurrentActivationResponseMap);
  }

  /**
   * Returns the rent object with countries supported rent and accessed services by country.
   *
   * @return rent object with countries supported rent and accessed services by country.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateGetRentServicesAndCountriesResponse getRentServicesAndCountries()
      throws IOException, SMSActivateBaseException {
    return getRentServicesAndCountries(0, null, 1);
  }

  /**
   * Returns the rent object with countries supported rent and accessed services by country.
   *
   * @param countryId id country (default 0).
   * @param operator  mobile operator.
   * @param time      time rent in hours (default 1).
   *                  time >= 1
   * @return the rent object with countries supported rent and accessed services by country.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateGetRentServicesAndCountriesResponse getRentServicesAndCountries(int countryId, @Nullable String operator, int time)
      throws IOException, SMSActivateBaseException {
    if (time <= 0) {
      throw new SMSActivateWrongParameterException("Time can't be negative or equals 0.", "Время не может быть меньше или равно 0");
    }
    if (countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_SERVICES_AND_COUNTRIES);
    smsActivateURLBuilder.append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId))
        .append(SMSActivateURLKey.OPERATOR, operator)
        .append(SMSActivateURLKey.TIME, String.valueOf(time));

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    Map<String, Map<String, Object>> rentCountriesServices = gson.fromJson(data, new TypeToken<Map<String, Map<String, Object>>>() {}.getType());

    Map<String, Object> countryMap = rentCountriesServices.get("countries");
    Map<String, Object> operatorMap = rentCountriesServices.get("operators");
    Map<String, Object> servicesMap = rentCountriesServices.get("services");

    List<String> operatorNameList = new ArrayList<>();
    List<Integer> countryIdList = new ArrayList<>();
    Map<String, SMSActivateRentService> smsActivateRentServiceMap = new HashMap<>();

    for (Object countryCode : countryMap.values())
      countryIdList.add(new BigDecimal(countryCode.toString()).intValue());

    for (Object name : operatorMap.values()) {
      operatorNameList.add(name.toString());
    }

    servicesMap.forEach((shortName, service) -> {
      Map<String, Object> serviceMap = (Map<String, Object>) service;
      int countNumber = new BigDecimal(serviceMap.get("quant").toString()).intValue();

      smsActivateRentServiceMap.put(shortName, new SMSActivateRentService(
          shortName,
          new BigDecimal(serviceMap.get("cost").toString()),
          countNumber
      ));
    });

    return new SMSActivateGetRentServicesAndCountriesResponse(
        operatorNameList, countryIdList, smsActivateRentServiceMap
    );
  }

  /**
   * Returns the rent id.
   *
   * @param service service to which you need to get a number.
   * @return rent id.
   * @throws IOException             if an I/O exception occurs.
   * @throws RentException           if rent is cancel or finish.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public SMSActivateGetRentNumberResponse getRentNumber(@NotNull String service)
      throws IOException, SMSActivateBaseException, NoBalanceException, NoNumberException {
    return getRentNumber(service, 0, null, 4, null);
  }

  /**
   * Returns the rent id.
   *
   * @param service    service to which you need to get a number.
   * @param countryId  id country (default 0 - Russia).
   * @param operator   mobile operator.
   * @param time       time rent (default 4 hour).
   * @param urlWebhook url for webhook.
   * @return rent id.
   * @throws IOException             if an I/O exception occurs.
   * @throws RentException           if rent is cancel or finish.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public SMSActivateGetRentNumberResponse getRentNumber(
      @NotNull String service,
      int countryId,
      @Nullable String operator,
      int time,
      @Nullable String urlWebhook
  ) throws IOException, SMSActivateBaseException, RentException, NoBalanceException, NoNumberException {
    if (countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_NUMBER);
    smsActivateURLBuilder.append(SMSActivateURLKey.RENT_TIME, String.valueOf(time))
        .append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId))
        .append(SMSActivateURLKey.OPERATOR, operator)
        .append(SMSActivateURLKey.URL, urlWebhook)
        .append(SMSActivateURLKey.SERVICE, service);

    Map<String, Object> responseMap = getRentDataFromJson(smsActivateURLBuilder.build());
    Map<String, Object> phoneMap = (Map<String, Object>) responseMap.get("phone");

    String number = phoneMap.get("number").toString();
    int id = new BigDecimal(phoneMap.get("id").toString()).intValue();
    String endDate = phoneMap.get("endDate").toString();

    return new SMSActivateGetRentNumberResponse(id, number, endDate, service);
  }

  /**
   * Returns the list sms.
   *
   * @param id id received in response when ordering a number.
   * @return list sms.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public SMSActivateGetRentStatusResponse getRentStatus(int id)
      throws IOException, SMSActivateBaseException, NoBalanceException, NoNumberException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(id));

    Map<String, Object> responseMap = getRentDataFromJson(smsActivateURLBuilder.build());

    Map<String, Map<String, Object>> valuesMap = (Map<String, Map<String, Object>>) responseMap.get("values");
    List<SMSActivateSMS> smsList = new ArrayList<>();

    for (Map<String, Object> phoneMap : valuesMap.values()) {
      String number = phoneMap.get("phoneFrom").toString();
      String text = phoneMap.get("text").toString();
      String serviceShortName = phoneMap.get("service").toString();
      String date = phoneMap.get("date").toString();

      smsList.add(new SMSActivateSMS(number, serviceShortName, text, date));
    }

    return new SMSActivateGetRentStatusResponse((int) responseMap.get("quantity"), smsList);
  }

  /**
   * Sets the status on rent.
   *
   * @param id  id activation for set status rent.
   * @param status status rent.
   * @return state rent.
   * @throws IOException             if an I/O exception occurs.
   * @throws RentException           if rent is cancel or finish.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateMainResponse setRentStatus(int id, @NotNull SMSActivateSetStatusRequest status)
      throws IOException, SMSActivateBaseException, RentException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.SET_RENT_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(id))
        .append(SMSActivateURLKey.STATUS, String.valueOf(status.getId()));

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    Map<String, String> responseMap = gson.fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
    validator.validateRentStateResponse(responseMap.get("status"), responseMap.get("message"));

    return new SMSActivateMainResponse(SMSActivateMainStatusResponse.SUCCESS);
  }

  /**
   * Returns the list rent ids.
   *
   * @return list rent ids
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public SMSActivateGetRentListResponse getRentList()
      throws IOException, SMSActivateBaseException, NoBalanceException, NoNumberException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_LIST);

    Map<String, Object> responseMap = getRentDataFromJson(smsActivateURLBuilder.build());

    Map<String, Map<String, Object>> valuesMap = (Map<String, Map<String, Object>>) responseMap.get("values");
    List<SMSActivateGetRentResponse> smsActivateGetRentResponseList = new ArrayList<>();

    for (Map<String, Object> phoneMap : valuesMap.values()) {
      String number = phoneMap.get("phone").toString();
      int id = new BigDecimal(phoneMap.get("id").toString()).intValue();

      smsActivateGetRentResponseList.add(new SMSActivateGetRentResponse(id, number));
    }

    return new SMSActivateGetRentListResponse(smsActivateGetRentResponseList);
  }

  /**
   * Returns rental data from json after checking for errors.
   *
   * @param url address to load data.
   * @return data in map after checking for errors.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  private Map<String, Object> getRentDataFromJson(@NotNull URL url)
      throws IOException, SMSActivateBaseException, NoBalanceException, NoNumberException {
    String data = SMSActivateWebClient.getOrThrowCommonException(url, validator);

    Map<String, Object> responseMap = gson.fromJson(data, new TypeToken<Map<String, Object>>() {
    }.getType());

    String message = String.valueOf(responseMap.get("message"));
    String status = String.valueOf(responseMap.get("status"));

    validator.throwNoNumbersOrNoBalanceExceptionByName(message);
    validator.validateRentStateResponse(status, message);
    return responseMap;
  }

  /**
   * Returns the current account balance by specific action.
   *
   * @param smsActivateAction name specific action.
   * @return current account balance.
   * @throws IOException             if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  private BigDecimal getBalance(@NotNull SMSActivateAction smsActivateAction)
      throws SMSActivateBaseException, IOException {
    SMSActivateURLBuilder SMSActivateURLBuilder = new SMSActivateURLBuilder(apiKey, smsActivateAction);
    String data = SMSActivateWebClient.getOrThrowCommonException(SMSActivateURLBuilder.build(), validator);

    String[] parts = data.split(":");
    return new BigDecimal(parts[1]);
  }
}
