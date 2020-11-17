package com.sms_activate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sms_activate.arch.*;
import com.sms_activate.arch.activation.SMSActivateGetCountriesResponse;
import com.sms_activate.arch.activation.SMSActivateGetCurrentActivation;
import com.sms_activate.arch.activation.SMSActivateGetNumbersStatusResponse;
import com.sms_activate.arch.qiwi.SMSActivateGetQiwiRequisitesResponse;
import com.sms_activate.old.Sms;
import com.sms_activate.old.activation.AccessStatusActivation;
import com.sms_activate.old.activation.StateActivationResponse;
import com.sms_activate.old.activation.StatusActivationRequest;
import com.sms_activate.old.country.Country;
import com.sms_activate.old.country.ServiceByCountry;
import com.sms_activate.old.error.BannedException;
import com.sms_activate.old.error.NoBalanceException;
import com.sms_activate.old.error.NoNumberException;
import com.sms_activate.old.error.WrongResponseException;
import com.sms_activate.old.error.common.SMSActivateBaseException;
import com.sms_activate.old.error.common.WrongParameterException;
import com.sms_activate.old.error.rent.RentException;
import com.sms_activate.old.phone.Phone;
import com.sms_activate.old.phone.PhoneRent;
import com.sms_activate.old.rent.Rent;
import com.sms_activate.old.rent.StateRentResponse;
import com.sms_activate.old.rent.StatusRentRequest;
import com.sms_activate.old.service.Service;
import com.sms_activate.old.service.ServiceWithCost;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>The class is a high-level API for interacting with the SMS-Activate API.
 * API capabilities allow you to work with the service through your software.
 * Before starting work, you must have an API key and a referral link to use all the API capabilities.</p>
 *
 * <p>All methods and constructor for this class throw WrongParameterException</p>
 *
 * @see WrongParameterException
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
   * @throws WrongParameterException if api-key is incorrect.
   */
  public SMSActivateApi(@NotNull String apiKey) throws WrongParameterException {
    if (apiKey.isEmpty()) {
      throw new WrongParameterException("API-key can't be empty.", "API ключ не может быть пустым.");
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
   * @throws WrongParameterException if one of parameters is incorrect. 
   */
  @NotNull
  public BigDecimal getBalance() throws IOException , SMSActivateBaseException {
    return getBalance(SMSActivateAction.GET_BALANCE);
  }

  /**
   * Returns the current account balance plus cashBack.
   *
   * @return current account balance plus cashBack.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect. 
   */
  @NotNull
  public BigDecimal getBalanceAndCashBack()
      throws IOException, SMSActivateBaseException {
    return getBalance(SMSActivateAction.GET_BALANCE_AND_CASHBACK);
  }

  /**
   * Returns a list counts of available services.
   *
   * @return list counts of available services.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect. 
   */
  @NotNull
  public List<SMSActivateGetNumbersStatusResponse> getNumbersStatus()
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
   * @throws WrongParameterException if one of parameters is incorrect. 
   */
  @NotNull
  public List<SMSActivateGetNumbersStatusResponse> getNumbersStatus(@Nullable Integer countryId, @Nullable String operator)
      throws IOException, SMSActivateBaseException {
    if (countryId != null && countryId < 0) {
      throw new WrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_NUMBERS_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.COUNTRY, (countryId == null) ? null : String.valueOf(countryId))
        .append(SMSActivateURLKey.OPERATOR, operator);

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    Map<String, String> serviceMap = gson.fromJson(data, new TypeToken<Map<String, String>>(){}.getType());
    List<SMSActivateGetNumbersStatusResponse> serviceList = new ArrayList<>();

    serviceMap.forEach((key, value) -> {
      String[] partsKey = key.split("_");

      serviceList.add(new SMSActivateGetNumbersStatusResponse (
          SMSActivateStatusResponse.SUCCESS,
          Boolean.parseBoolean(partsKey[1]),
          Integer.parseInt(value),
          partsKey[0]
      ));
    });

    return serviceList;
  }

  /**
   * Returns the phone by service, ref, countryId.
   *
   * @param service   service short name.
   * @param countryId id country.
   * @return phone for activation.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect.
   * @throws BannedException         if account has been banned. 
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public Phone getNumber(@NotNull Service service, int countryId) throws IOException, SMSActivateBaseException {
    return getNumber(service, countryId, null, null, false);
  }

  /**
   * Returns the phone number by service, ref, countryId, phoneException, operator, forward
   *
   * @param service        service for activation.
   * @param countryId      id country.
   * @param phoneException excepted phone number prefix. Specify separated by commas.
   *                       <pre>{@code   7918,7900111}</pre>
   * @param operator       mobile operator. May be specify separated by commas.
   * @param forward        is it necessary to request a number with forwarding.
   * @return phone for activation.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect. 
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   * @throws BannedException         if account has been banned.
   */
  @NotNull
  public Phone getNumber(
      @NotNull Service service,
      int countryId,
      @Nullable String phoneException,
      @Nullable String operator,
      boolean forward
  ) throws IOException, SMSActivateBaseException {
    if (countryId < 0) {
      throw new WrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_NUMBER);
    smsActivateURLBuilder.append(SMSActivateURLKey.REF, ref)
        .append(SMSActivateURLKey.SERVICE, service.getShortName())
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

    return new Phone(number, service, id, forward);
  }

  /**
   * Returns the list phone by countryId, multiService, ref.<br/>
   * Separator for multiService is commas. <br/>
   * <pre> multiService -> vk,av,go,tg. </pre>
   *
   * @param multiService services for ordering (not be null).
   * @param countryId    id country.
   * @return list phone.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect.
   * @throws BannedException         if account has been banned. 
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public List<Phone> getMultiServiceNumber(@NotNull String multiService, int countryId)
      throws IOException, SMSActivateBaseException {
    return getMultiServiceNumber(multiService, countryId, null, null);
  }

  /**
   * Returns the list phone by countryId, multiService, ref.<br/>
   * Separator for multiService, multiForward and operator is commas. <br/>
   * <pre>multiService -> vk,av,go,tg<br/>multiForward -> 0,0,1,0; 0,0,0,0 - correct; 0,1,1,0 - incorrect.</pre>
   *
   * @param multiService services for ordering (not be null).
   * @param countryId    id country.
   * @param multiForward is it necessary to request a number with forwarding.
   * @param operator     mobile operator.
   * @return list phone.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect.
   * @throws BannedException         if account has been banned. 
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public List<Phone> getMultiServiceNumber(
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
      throw new WrongParameterException("Wrong ID country.", "Неверный ID страны.");
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

    List<Map<String, Object>> phoneMapList = gson.fromJson(data, new TypeToken<List<Map<String, Object>>>(){}.getType());
    List<Phone> phoneList = new ArrayList<>();

    int indexForwardPhoneNumber = (multiForward == null) ? -1 : Arrays.asList(multiForward.split(",")).indexOf("1"); //index phone where need forwarding

    for (int i = 0; i < phoneMapList.size(); i++) {
      Map<String, Object> phoneMap = phoneMapList.get(i);
      int id = new BigDecimal(phoneMap.get("activation").toString()).intValue();
      String phone = phoneMap.get("phone").toString();
      String serviceName = phoneMap.get("service").toString();

      phoneList.add(new Phone(
          phone,
          new Service(serviceName),
          id,
          i == indexForwardPhoneNumber
      ));
    }

    return phoneList;
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
   * @param phone  phone to set activation status (not be null).
   * @param status value to establish (not be null).
   * @return access activation.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect. 
   */
  @NotNull
  public AccessStatusActivation setStatus(@NotNull Phone phone, @NotNull StatusActivationRequest status)
      throws IOException, SMSActivateBaseException {
    return setStatus(phone, status, false);
  }

  /**
   * Sets the status activation.
   *
   * @param phone   phone to set activation status (not be null).
   * @param status  value to establish (not be null).
   * @param forward number is forwarding.
   * @return access activation.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect. 
   */
  @NotNull
  public AccessStatusActivation setStatus(
      @NotNull Phone phone,
      @NotNull StatusActivationRequest status,
      boolean forward
  ) throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.SET_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.STATUS, String.valueOf(status.getId()))
        .append(SMSActivateURLKey.ID, String.valueOf(phone.getId()))
        .append(SMSActivateURLKey.FORWARD, forward ? "1" : "0");

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    return AccessStatusActivation.getStatusByName(data);
  }

  /**
   * Returns the state phone activation.
   *
   * @param phone phone id to get activation state (not be null).
   * @return state activation.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect. 
   */
  @NotNull
  public StateActivationResponse getStatus(@NotNull Phone phone)
      throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(phone.getId()));

    String code = null;
    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    if (data.contains(":")) {
      String[] parts = data.split(":");

      data = parts[0];
      code = parts[1];
    }

    StateActivationResponse state = StateActivationResponse.getStateByName(data);
    state.setCodeFromSMS(code);

    return state;
  }

  /**
   * Returns the full text sms.
   *
   * @param phone id activation.
   * @return full text sms.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect. 
   * @throws WrongResponseException  if server response is not correct.
   */
  @NotNull
  public String getFullSms(@NotNull Phone phone)
      throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_FULL_SMS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(phone.getId()));

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    if (data.contains("FULL")) {
      return data.split(":")[1];
    } else {
      StateActivationResponse stateActivationResponse = StateActivationResponse.getStateByName(data);

      throw new WrongResponseException(
          stateActivationResponse.getEnglishMessage(),
          stateActivationResponse.getRussianMessage()
      );
    }
  }

  /**
   * Returns the all actual prices by country.
   *
   * @return price list country.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect. 
   */
  @NotNull
  public List<ServiceByCountry> getPrices() throws IOException, SMSActivateBaseException {
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
   * @throws WrongParameterException if one of parameters is incorrect. 
   */
  @NotNull
  public List<ServiceByCountry> getPrices(@Nullable Service service, @Nullable Integer countryId)
      throws IOException, SMSActivateBaseException {
    if (countryId != null && countryId < 0) {
      throw new WrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    String shortNameService = (service == null) ? null : service.getShortName();

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_PRICES);
    smsActivateURLBuilder.append(SMSActivateURLKey.SERVICE, shortNameService)
        .append(SMSActivateURLKey.COUNTRY, (countryId == null) ? null : String.valueOf(countryId));

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    Map<String, Map<String, Map<String, Double>>> countryMap = gson.fromJson(data,
        new TypeToken<Map<String, Map<String, Map<String, Double>>>>(){}.getType());

    List<ServiceByCountry> serviceByCountryList = new ArrayList<>();

    countryMap.forEach((countryCode, serviceMap) -> {
      List<ServiceWithCost> serviceWithCostList = new ArrayList<>();

      serviceMap.forEach((shortName, value) -> serviceWithCostList.add(new ServiceWithCost(
          shortName,
          BigDecimal.valueOf(value.get("count")).intValue(),
          BigDecimal.valueOf(value.get("cost"))
      )));

      serviceByCountryList.add(new ServiceByCountry(
          new Country(Integer.parseInt(countryCode)),
          serviceWithCostList
      ));
    });

    return serviceByCountryList;
  }

  /**
   * Returns the country information.
   *
   * @return country information.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect. 
   */
  @NotNull
  public List<SMSActivateGetCountriesResponse> getCountries() throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_COUNTRIES);

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    Map<String, Map<String, Object>> countryInformationMap = gson.fromJson(data,
        new TypeToken<Map<String, Map<String, Object>>>(){}.getType());

    List<SMSActivateGetCountriesResponse> countryList = new ArrayList<>();

    for (Map<String, Object> countryMap : countryInformationMap.values()) {
      int id = new BigDecimal(countryMap.get("id").toString()).intValue();

      String rus = countryMap.get("rus").toString();
      String eng = countryMap.get("eng").toString();
      String chn = countryMap.get("chn").toString();

      boolean isVisible = Boolean.parseBoolean(countryMap.get("visible").toString());
      boolean isSupportRetry = Boolean.parseBoolean(countryMap.get("retry").toString());
      boolean isSupportRent = Boolean.parseBoolean(countryMap.get("rent").toString());
      boolean isSupportMultiService = Boolean.parseBoolean(countryMap.get("multiService").toString());

      countryList.add(new SMSActivateGetCountriesResponse(SMSActivateStatusResponse.SUCCESS, id, rus, eng, chn,
          isVisible, isSupportRetry, isSupportRent, isSupportMultiService));
    }

    return countryList;
  }

  /**
   * Returns the qiwi response with data on wallet.
   *
   * @return qiwi response with data on wallet
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect. 
   */
  @NotNull
  public SMSActivateGetQiwiRequisitesResponse getQiwiRequisites() throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_QIWI_REQUISITES);
    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);
    return gson.fromJson(data, new TypeToken<SMSActivateGetQiwiRequisitesResponse>(){}.getType());
  }

  /**
   * Returns the phone for additional service by forwarding.
   *
   * @param phone phone activation.
   * @return phone for additional service by forwarding
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect. 
   */
  @NotNull
  public Phone getAdditionalService(@NotNull Phone phone, @NotNull Service service)
      throws IOException, SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_ADDITIONAL_SERVICE);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(phone.getId()))
        .append(SMSActivateURLKey.SERVICE, service.getShortName());

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    String[] parts = data.split(":");
    String number = parts[2];
    int id = new BigDecimal(parts[1]).intValue();

    return new Phone(number, service, id, false);
  }

  /**
   * Returns the list current activation.
   *
   * @return list with first 10 activation.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect.
   */
  @NotNull
  public List<SMSActivateGetCurrentActivation> getCurrentActivations()
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
   * @throws WrongParameterException if one of parameters is incorrect. 
   */
  @NotNull
  public List<SMSActivateGetCurrentActivation> getCurrentActivations(int start, int length)
      throws IOException , SMSActivateBaseException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_CURRENT_ACTIVATION);
    smsActivateURLBuilder.append(SMSActivateURLKey.START, String.valueOf(start))
        .append(SMSActivateURLKey.LENGTH, String.valueOf(length))
        .append(SMSActivateURLKey.ORDER, SMSActivateURLKey.ID.getName())
        .append(SMSActivateURLKey.ORDER_BY, "asc");

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    Map<String, Object> responseMap = gson.fromJson(data, new TypeToken<Map<String, Object>>(){}.getType());

    if (responseMap.get("status").toString().equalsIgnoreCase("fail")) {
      return new ArrayList<>();
    }

    List<Map<String, Object>> currentActivationMapList = (List<Map<String, Object>>) responseMap.get("array");
    List<SMSActivateGetCurrentActivation> smsActivateGetCurrentActivationList = new ArrayList<>();

    for (Map<String, Object> currentActivationMap : currentActivationMapList) {
      int id = new BigDecimal(String.valueOf(currentActivationMap.get("id"))).intValue();
      boolean forward = Boolean.parseBoolean(String.valueOf(currentActivationMap.get("forward")));

      String number = String.valueOf(currentActivationMap.get("phone"));
      String serviceName = String.valueOf(currentActivationMap.get("service"));
      String countryName = String.valueOf(currentActivationMap.get("countryName"));

      smsActivateGetCurrentActivationList.add(new SMSActivateGetCurrentActivation(
          SMSActivateStatusResponse.SUCCESS, id, forward, number, countryName, serviceName));
    }

    return smsActivateGetCurrentActivationList;
  }

  /**
   * Returns the rent object with countries supported rent and accessed services by country.
   *
   * @return rent object with countries supported rent and accessed services by country.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect. 
   */
  @NotNull
  public Rent getRentServicesAndCountries()
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
   * @throws WrongParameterException if one of parameters is incorrect. 
   */
  @NotNull
  public Rent getRentServicesAndCountries(int countryId, @Nullable String operator, int time)
      throws IOException, SMSActivateBaseException {
    if (time <= 0) {
      throw new WrongParameterException("Time can't be negative or equals 0.", "Время не может быть меньше или равно 0");
    }
    if (countryId < 0) {
      throw new WrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_SERVICES_AND_COUNTRIES);
    smsActivateURLBuilder.append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId))
        .append(SMSActivateURLKey.OPERATOR, operator)
        .append(SMSActivateURLKey.TIME, String.valueOf(time));

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    Map<String, Map<String, Object>> rentCountriesServices = gson.fromJson(data,
        new TypeToken<Map<String, Map<String, Object>>>(){}.getType());

    Map<String, Object> countryMap = rentCountriesServices.get("countries");
    Map<String, Object> operatorMap = rentCountriesServices.get("operators");
    Map<String, Object> servicesMap = rentCountriesServices.get("services");

    List<String> operatorNameList = new ArrayList<>();
    List<Integer> countryIdList = new ArrayList<>();
    List<ServiceWithCost> serviceWithCostList = new ArrayList<>();

    for (Object countryCode : countryMap.values())
      countryIdList.add(new BigDecimal(countryCode.toString()).intValue());

    for (Object name : operatorMap.values()) {
      operatorNameList.add(name.toString());
    }

    servicesMap.forEach((shortName, service) -> {
      Map<String, Object> serviceMap = (Map<String, Object>) service;
      int countNumber = new BigDecimal(serviceMap.get("quant").toString()).intValue();

      serviceWithCostList.add(new ServiceWithCost(
          shortName,
          countNumber,
          new BigDecimal(serviceMap.get("cost").toString()))
      );
    });

    Country country = new Country(countryId);

    return new Rent(operatorNameList, new ServiceByCountry(country, serviceWithCostList), countryIdList);
  }

  /**
   * Returns the rent phone.
   *
   * @param service service to which you need to get a number.
   * @return rent phone.
   * @throws IOException             if an I/O exception occurs.
   * @throws RentException           if rent is cancel or finish.
   * @throws WrongParameterException if one of parameters is incorrect. 
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public PhoneRent getRentNumber(@NotNull Service service)
      throws IOException, SMSActivateBaseException, NoBalanceException, NoNumberException {
    return getRentNumber(service, 0, null, 1, null);
  }

  /**
   * Returns the rent phone.
   *
   * @param service    service to which you need to get a number.
   * @param time       time rent (default 1 hour).
   * @param operator   mobile operator.
   * @param countryId  id country (default 0 - Russia).
   * @param urlWebhook url for webhook.
   * @return rent phone.
   * @throws IOException             if an I/O exception occurs.
   * @throws RentException           if rent is cancel or finish.
   * @throws WrongParameterException if one of parameters is incorrect. 
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public PhoneRent getRentNumber(
      @NotNull Service service,
      int countryId,
      @Nullable String operator,
      int time,
      @Nullable String urlWebhook
  ) throws IOException, SMSActivateBaseException, RentException, NoBalanceException, NoNumberException {
    if (countryId < 0) {
      throw new WrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_NUMBER);
    smsActivateURLBuilder.append(SMSActivateURLKey.RENT_TIME, String.valueOf(time))
        .append(SMSActivateURLKey.COUNTRY, String.valueOf(countryId))
        .append(SMSActivateURLKey.OPERATOR, operator)
        .append(SMSActivateURLKey.URL, urlWebhook)
        .append(SMSActivateURLKey.SERVICE, service.getShortName());

    Map<String, Object> responseMap = getRentDataFromJson(smsActivateURLBuilder.build());
    Map<String, Object> phoneMap = (Map<String, Object>) responseMap.get("phone");

    String number = phoneMap.get("number").toString();
    int id = new BigDecimal(phoneMap.get("id").toString()).intValue();
    String endDate = phoneMap.get("endDate").toString();

    return new PhoneRent(number, service, id, false, endDate);
  }

  /**
   * Returns the list sms.
   *
   * @param phone phone received in response when ordering a number.
   * @return list sms.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect. 
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public List<Sms> getRentStatus(@NotNull Phone phone)
      throws IOException, SMSActivateBaseException, NoBalanceException, NoNumberException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(phone.getId()));

    Map<String, Object> responseMap = getRentDataFromJson(smsActivateURLBuilder.build());

    Map<String, Map<String, Object>> valuesMap = (Map<String, Map<String, Object>>) responseMap.get("values");
    List<Sms> smsList = new ArrayList<>();

    for (Map<String, Object> phoneMap : valuesMap.values()) {
      String number = phoneMap.get("phoneFrom").toString();
      String text = phoneMap.get("text").toString();
      String serviceShortName = phoneMap.get("service").toString();
      String date = phoneMap.get("date").toString();

      smsList.add(new Sms(new Phone(number, new Service(serviceShortName)), text, date));
    }

    return smsList;
  }

  /**
   * Sets the status on rent.
   *
   * @param phone  phone for set status rent.
   * @param status status rent.
   * @return state rent.
   * @throws IOException             if an I/O exception occurs.
   * @throws RentException           if rent is cancel or finish.
   * @throws WrongParameterException if one of parameters is incorrect. 
   */
  @NotNull
  public StateRentResponse setRentStatus(@NotNull Phone phone, @NotNull StatusRentRequest status)
      throws IOException, SMSActivateBaseException, RentException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.SET_RENT_STATUS);
    smsActivateURLBuilder.append(SMSActivateURLKey.ID, String.valueOf(phone.getId()))
        .append(SMSActivateURLKey.STATUS, String.valueOf(status.getId()));

    String data = SMSActivateWebClient.getOrThrowCommonException(smsActivateURLBuilder.build(), validator);

    Map<String, String> responseMap = gson.fromJson(data, new TypeToken<Map<String, String>>(){}.getType());
    validator.validateRentStateResponse(responseMap.get("status"), responseMap.get("message"));

    return StateRentResponse.SUCCESS;
  }

  /**
   * Returns the list rent phones.
   *
   * @return list rent phones
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect. 
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public List<Phone> getRentList()
      throws IOException, SMSActivateBaseException, NoBalanceException, NoNumberException {
    SMSActivateURLBuilder smsActivateURLBuilder = new SMSActivateURLBuilder(apiKey, SMSActivateAction.GET_RENT_LIST);

    Map<String, Object> responseMap = getRentDataFromJson(smsActivateURLBuilder.build());

    Map<String, Map<String, Object>> valuesMap = (Map<String, Map<String, Object>>) responseMap.get("values");
    List<Phone> phoneList = new ArrayList<>();

    for (Map<String, Object> phoneMap : valuesMap.values()) {
      String number = phoneMap.get("phone").toString();
      int id = new BigDecimal(phoneMap.get("id").toString()).intValue();

      phoneList.add(new Phone(number, null, id, false));
    }

    return phoneList;
  }

  /**
   * Returns rental data from json after checking for errors.
   *
   * @param url address to load data.
   * @return data in map after checking for errors.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect. 
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  private Map<String, Object> getRentDataFromJson(@NotNull URL url)
      throws  IOException , SMSActivateBaseException, NoBalanceException, NoNumberException {
    String data = SMSActivateWebClient.getOrThrowCommonException(url, validator);

    Map<String, Object> responseMap = gson.fromJson(data, new TypeToken<Map<String, Object>>(){}.getType());

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
   * @throws WrongParameterException if one of parameters is incorrect. 
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
