package com.sms_activate;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sms_activate.activation.*;
import com.sms_activate.activation.balance.*;
import com.sms_activate.activation.country.*;
import com.sms_activate.activation.current_activation.*;
import com.sms_activate.activation.get_prices.SMSActivateGetPriceResponse;
import com.sms_activate.activation.get_prices.SMSActivateGetPricesResponse;
import com.sms_activate.activation.get_status.*;
import com.sms_activate.activation.numbers_status.*;
import com.sms_activate.activation.set_status.*;
import com.sms_activate.error.*;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.*;
import com.sms_activate.main_response.*;
import com.sms_activate.qiwi.*;
import com.sms_activate.rent.*;
import com.sms_activate.rent.get_rent_list.*;
import com.sms_activate.rent.get_rent_services_and_countries.*;
import com.sms_activate.rent.get_rent_sms.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
  private static final SMSActivateValidator validator = new SMSActivateValidator();

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
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getBalance() throws IOException, SMSActivateBaseException {
    BigDecimal balance = getBalance(SMSActivateAction.GET_BALANCE);
    return (T) new SMSActivateGetBalanceResponse(balance);
  }

  /**
   * Returns the current account balance plus cashBack.
   *
   * @return current account balance plus cashBack.
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getBalanceAndCashBack()
      throws IOException, SMSActivateBaseException {
    BigDecimal balance = ((SMSActivateGetBalanceResponse) getBalance()).getBalance();
    BigDecimal balanceAndCashBack = getBalance(SMSActivateAction.GET_BALANCE_AND_CASHBACK);

    return (T) new SMSActivateGetBalanceAndCashBackResponse(balance, balanceAndCashBack.subtract(balance));
  }

  /**
   * Returns a list counts of available services.
   *
   * @return list counts of available services.
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getNumbersStatus()
      throws IOException, SMSActivateBaseException {
    return getNumbersStatus(null, null);
  }

  /**
   * Return a list counts of available services by country and operator.
   *
   * @param countryId id country.
   * @param operator  name operator mobile network (not be null).
   * @return list counts of available services by county and operator (not be null).
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getNumbersStatus(@Nullable Integer countryId, @Nullable String operator)
      throws IOException, SMSActivateBaseException {
    if (countryId != null && countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_NUMBERS_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.COUNTRY, (countryId == null) ? null : String.valueOf(countryId))
        .append(SMSActivateURLKey.OPERATOR, operator);

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    Map<String, String> serviceMap = gson.fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
    Map<String, SMSActivateGetNumberStatusResponse> smsActivateGetNumbersStatusResponseMap = new HashMap<>();

    serviceMap.forEach((key, value) -> {
      String[] partsKey = key.split("_");

      smsActivateGetNumbersStatusResponseMap.put(partsKey[0], new SMSActivateGetNumberStatusResponse(
          Boolean.parseBoolean(partsKey[1]),
          Integer.parseInt(value),
          partsKey[0]
      ));
    });

    return (T) new SMSActivateGetNumbersStatusResponse(smsActivateGetNumbersStatusResponseMap);
  }

  /**
   * Returns the id by service, ref, countryId.
   *
   * @param service   service name for activation.
   * @param countryId id country.
   * @return id activation for activation.
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error not documented.
   */
  @NotNull
  public  getNumber(@NotNull String service, int countryId) throws IOException, SMSActivateBaseException {
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
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateUnknownException        if error not documented.
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

    String data = SMSActivateWebClient.get(smsActivateURLBuilder.build());
    validator.throwExceptionWithBan(data);


    // TODO: throw exception
    if (!data.contains("ACCESS")) {
      SMSActivateMainStatusResponse smsActivateMainStatusResponse = SMSActivateMainStatusResponse.getStatusByName(data);

      if (smsActivateMainStatusResponse != SMSActivateMainStatusResponse.UNKNOWN) {
        return  new SMSActivateMainResponse(smsActivateMainStatusResponse);
      } else {
        throw new SMSActivateUnknownException(data);
      }
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
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateGetMultiServiceNumberResponse getMultiServiceNumber(@NotNull Set<String> multiService, int countryId)
      throws IOException, SMSActivateBaseException {
    return getMultiServiceNumber(multiService, countryId, null, null);
  }

  /**
   * Returns the list id by countryId, multiService, ref.<br/>
   * Separator for multiService, multiForward and operator is commas. <br/>
   *
   * @param multiService services for ordering (not be null).
   * @param countryId    id country.
   * @param multiForward is it necessary to request a number with forwarding.
   * @param operator     mobile operator.
   * @return list id.
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public SMSActivateGetMultiServiceNumberResponse getMultiServiceNumber(
      @NotNull Set<String> multiServiceSet,
      int countryId,
      @Nullable List<Boolean> multiForwardList,
      @Nullable Set<String> operatorSet
  ) throws IOException, SMSActivateBaseException {
    multiServiceSet.removeIf(x -> x == null || x.isEmpty());

    String strMultiService = String.join(",", multiServiceSet);
    String strOperators = null;
    String strMultiForward = null;

    if (multiForwardList != null && multiForwardList.size() != 0) {
      multiForwardList.removeIf(Objects::isNull);
      strMultiForward = multiForwardList.stream().map(x -> x ? "1" : "0").collect(Collectors.joining(","));
    } if (operatorSet != null && operatorSet.size() != 0) {
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

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);
    validator.throwExceptionWithBan(data);

    try {
      List<Map<String, Object>> activationMapList = gson.fromJson(data, new TypeToken<List<Map<String, Object>>>() {}.getType());
      List<SMSActivateActivation> phoneList = new ArrayList<>();

      int indexForwardPhoneNumber = (multiForwardList == null) ? -1 : multiForwardList.indexOf(true); //index id where need forwarding

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
    } catch (JsonSyntaxException ignored) {
      // TODO: throw exceptions;
      //return getMainResponseByStatusNameOrThrowException(data);
    }
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
   * @param id     id to set activation status (not be null).
   * @param status value to establish (not be null).
   * @return access activation.
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  setStatus(int id, @NotNull SMSActivateSetStatusRequest status)
      throws IOException, SMSActivateBaseException {
    return setStatus(id, status, false);
  }

  /**
   * Sets the status activation.
   *
   * @param id      id to set activation status (not be null).
   * @param status  value to establish (not be null).
   * @param forward number is forwarding.
   * @return access activation.
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  setStatus(
      int id,
      @NotNull SMSActivateSetStatusRequest status,
      boolean forward
  ) throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.SET_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.STATUS, String.valueOf(status.getId()))
        .append(SMSActivateURLKey.ID, String.valueOf(id))
        .append(SMSActivateURLKey.FORWARD, forward ? "1" : "0");

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);
    SMSActivateAccessStatus smsActivateAccessStatus = SMSActivateAccessStatus.getStatusByName(data);

    if (smsActivateAccessStatus != SMSActivateAccessStatus.UNKNOWN) {
      return (T) new SMSActivateSetStatusResponse(smsActivateAccessStatus);
    } else {
      return (T) getMainResponseByStatusNameOrThrowException(data);
    }
  }

  /**
   * Returns the state id activation.
   *
   * @param id id id to get activation state (not be null).
   * @return state activation.
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getStatus(int id)
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

    if (status != SMSActivateGetStatus.UNKNOWN) {
      return (T) new SMSActivateGetStatusResponse(status, code);
    } else {
      return (T) getMainResponseByStatusNameOrThrowException(data);
    }
  }

  /**
   * Returns the full text sms.
   *
   * @param id id activation.
   * @return full text sms.
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getFullSms(int id)
      throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_FULL_SMS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(id));

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    if (data.contains("FULL")) {
      return (T) new SMSActivateGetFillSmsResponse(data.split(":")[1]);
    } else {
      return (T) getMainResponseByStatusNameOrThrowException(data);
    }
  }

  /**
   * Returns the all actual prices by country.
   *
   * @return price list country.
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getPrices() throws IOException, SMSActivateBaseException {
    return getPrices(null, null);
  }

  /**
   * Returns the actual rent prices by country.
   *
   * @param service   service for needed price list (default null).
   *                  <pre>{@code null, null -> all service and all country.}</pre>
   * @param countryId id number (default null).
   * @return price list country.
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getPrices(@Nullable String service, @Nullable Integer countryId)
      throws IOException, SMSActivateBaseException {
    if (countryId != null && countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_PRICES);
    smsActivateURLBuilder.append(SMSActivateURLKey.SERVICE, service)
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

    return (T) new SMSActivateGetPricesResponse(smsActivateGetPriceMapList);
  }

  /**
   * Returns the country information.
   *
   * @return country information.
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getCountries() throws IOException, SMSActivateBaseException {
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

    return (T) new SMSActivateGetCountriesResponse(countryList);
  }

  /**
   * Returns the qiwi response with data on wallet.
   *
   * @return qiwi response with data on wallet
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getQiwiRequisites() throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_QIWI_REQUISITES);
    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);
    return (T) gson.fromJson(data, new TypeToken<SMSActivateGetQiwiRequisitesResponse>() {}.getType());
  }

  /**
   * Returns the id activation for additional service by forwarding.
   *
   * @param id id activation.
   * @return id activation for additional service by forwarding
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getAdditionalService(int id, @NotNull String serviceName)
      throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_ADDITIONAL_SERVICE);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(id))
        .append(SMSActivateURLKey.SERVICE, serviceName);

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    if (data.contains("ADDITIONAL")) {
      String[] parts = data.split(":");
      String number = parts[2];
      id = new BigDecimal(parts[1]).intValue();

      return (T) new SMSActivateActivation(id, number, serviceName, false);
    } else {
      return (T) getMainResponseByStatusNameOrThrowException(data);
    }
  }

  /**
   * Returns the list current activation.
   *
   * @return list with first 10 activation.
   * @throws IOException                        if an I/O exception occurs.
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
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getCurrentActivations(int start, int length)
      throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_CURRENT_ACTIVATION);
    smsActivateURLBuilder.append(SMSActivateURLKey.START, String.valueOf(start))
        .append(SMSActivateURLKey.LENGTH, String.valueOf(length))
        .append(SMSActivateURLKey.ORDER, SMSActivateURLKey.ID.getName())
        .append(SMSActivateURLKey.ORDER_BY, "asc");

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    Map<String, Object> responseMap = gson.fromJson(data, new TypeToken<Map<String, Object>>() {}.getType());

    if (responseMap.get("status").toString().equalsIgnoreCase("fail")) {
      return (T) new SMSActivateGetCurrentActivationsResponse(new HashMap<>());
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

    return (T) new SMSActivateGetCurrentActivationsResponse(smsActivateGetCurrentActivationResponseMap);
  }

  /**
   * Returns the rent object with countries supported rent and accessed services by country.
   *
   * @return rent object with countries supported rent and accessed services by country.
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getRentServicesAndCountries()
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
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getRentServicesAndCountries(int countryId, @Nullable String operator, int time)
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

    for (Object countryCode : countryMap.values()) {
      countryIdList.add(new BigDecimal(countryCode.toString()).intValue());
    }

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

    return (T) new SMSActivateGetRentServicesAndCountriesResponse(
        operatorNameList, countryIdList, smsActivateRentServiceMap
    );
  }

  /**
   * Returns the rent id.
   *
   * @param service service to which you need to get a number.
   * @return rent id.
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getRentNumber(@NotNull String service)
      throws IOException, SMSActivateBaseException {
    return getRentNumber(service, 0, null, 1, null);
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
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getRentNumber(
      @NotNull String service,
      int countryId,
      @Nullable String operator,
      int time,
      @Nullable String urlWebhook
  ) throws IOException, SMSActivateBaseException {
    if (countryId < 0) {
      throw new SMSActivateWrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }
    if (time <= 0) {
      throw new SMSActivateWrongParameterException("The rental time cannot be less than 1.", "Время аренды не может быть меньше чем 1.");
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_NUMBER);
    smsActivateURLBuilder.append(SMSActivateURLKey.RENT_TIME, String.valueOf(time))
        .append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId))
        .append(SMSActivateURLKey.OPERATOR, operator)
        .append(SMSActivateURLKey.URL, urlWebhook)
        .append(SMSActivateURLKey.SERVICE, service);


    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);
    Map<String, Object> responseMap = gson.fromJson(data, new TypeToken<Map<String, Object>>() {}.getType());

    String status = String.valueOf(responseMap.get("status"));

    if (!status.equalsIgnoreCase("success")) {
      return (T) getMainResponseByStatusNameOrThrowException(String.valueOf(responseMap.get("message")));
    }

    Map<String, Object> phoneMap = (Map<String, Object>) responseMap.get("phone");

    String number = phoneMap.get("number").toString();
    int id = new BigDecimal(phoneMap.get("id").toString()).intValue();
    String endDate = phoneMap.get("endDate").toString();

    return (T) new SMSActivateGetRentNumberResponse(id, number, endDate, service);
  }

  /**
   * Returns the list sms.
   *
   * @param id id received in response when ordering a number.
   * @return list sms.
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  getRentStatus(int id)
      throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(id));

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);
    Map<String, Object> responseMap = gson.fromJson(data, new TypeToken<Map<String, Object>>() {}.getType());

    String status = String.valueOf(responseMap.get("status"));

    if (!status.equalsIgnoreCase("success")) {
      return (T) getMainResponseByStatusNameOrThrowException(String.valueOf(responseMap.get("message")));
    }

    Map<String, Map<String, Object>> valuesMap = (Map<String, Map<String, Object>>) responseMap.get("values");
    List<SMSActivateSMS> smsList = new ArrayList<>();

    for (Map<String, Object> phoneMap : valuesMap.values()) {
      String number = phoneMap.get("phoneFrom").toString();
      String text = phoneMap.get("text").toString();
      String serviceShortName = phoneMap.get("service").toString();
      String date = phoneMap.get("date").toString();

      smsList.add(new SMSActivateSMS(number, serviceShortName, text, date));
    }

    return (T) new SMSActivateGetRentStatusResponse((int) responseMap.get("quantity"), smsList);
  }

  /**
   * Sets the status on rent.
   *
   * @param id     id activation for set status rent.
   * @param status status rent.
   * @return state rent.
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public  setRentStatus(int id, @NotNull SMSActivateSetStatusRequest status)
      throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.SET_RENT_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(id))
        .append(SMSActivateURLKey.STATUS, String.valueOf(status.getId()));

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    Map<String, String> responseMap = gson.fromJson(data, new TypeToken<Map<String, String>>() {}.getType());
    SMSActivateMainStatusResponse smsActivateMainStatusResponse = SMSActivateMainStatusResponse.getStatusByName(responseMap.get("status"));

    if (smsActivateMainStatusResponse != SMSActivateMainStatusResponse.SUCCESS) {
      return (T) new SMSActivateMainResponse(SMSActivateMainStatusResponse.SUCCESS);
    } else {
      return (T) getMainResponseByStatusNameOrThrowException(responseMap.get("message"));
    }
  }

  /**
   * Returns the list rent numbers.
   *
   * @return list rent ids numbers.
   * @throws IOException                        if an I/O exception occurs.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.l
   */
  @NotNull
  public  getRentList()
      throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_LIST);

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);
    Map<String, Object> responseMap = gson.fromJson(data, new TypeToken<Map<String, Object>>() {}.getType());

    String status = String.valueOf(responseMap.get("status"));

    if (!status.equalsIgnoreCase("success")) {
      return (T) getMainResponseByStatusNameOrThrowException(String.valueOf(responseMap.get("message")));
    }

    Map<String, Map<String, Object>> valuesMap = (Map<String, Map<String, Object>>) responseMap.get("values");
    List<SMSActivateGetRentResponse> smsActivateGetRentResponseList = new ArrayList<>();

    for (Map<String, Object> phoneMap : valuesMap.values()) {
      String number = phoneMap.get("phone").toString();
      int id = new BigDecimal(phoneMap.get("id").toString()).intValue();

      smsActivateGetRentResponseList.add(new SMSActivateGetRentResponse(id, number));
    }

    return (T) new SMSActivateGetRentListResponse(smsActivateGetRentResponseList);
  }

  /**
   * Returns the current account balance by specific action.
   *
   * @param smsActivateAction name specific action.
   * @return current account balance.
   * @throws IOException                        if an I/O exception occurs.
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

  /**
   * Returns the main response if status name exists in enum else throw UnknownException.
   *
   * @param statusName name status from server.
   * @return main response if status name exists in enum else throw UnknownException.
   * @throws SMSActivateUnknownException if error not documented.
   */
  @NotNull
  private SMSActivateMainResponse getMainResponseByStatusNameOrThrowException(@NotNull String statusName)
      throws SMSActivateUnknownException {
    SMSActivateMainStatusResponse smsActivateMainStatusResponse = SMSActivateMainStatusResponse.getStatusByName(statusName);

    if (smsActivateMainStatusResponse != SMSActivateMainStatusResponse.UNKNOWN) {
      return new SMSActivateMainResponse(smsActivateMainStatusResponse);
    } else {
      throw new SMSActivateUnknownException(statusName);
    }
  }
}
