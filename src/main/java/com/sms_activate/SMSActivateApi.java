package com.sms_activate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sms_activate.activation.AccessStatusActivation;
import com.sms_activate.activation.StateActivationResponse;
import com.sms_activate.activation.StatusActivationRequest;
import com.sms_activate.country.Country;
import com.sms_activate.country.ServiceByCountry;
import com.sms_activate.error.BannedException;
import com.sms_activate.error.NoBalanceException;
import com.sms_activate.error.NoNumberException;
import com.sms_activate.error.WrongResponseException;
import com.sms_activate.error.common.BaseSMSActivateException;
import com.sms_activate.error.common.SQLServerException;
import com.sms_activate.error.common.WrongParameterException;
import com.sms_activate.error.rent.RentException;
import com.sms_activate.phone.Phone;
import com.sms_activate.phone.PhoneRent;
import com.sms_activate.qiwi.QiwiResponse;
import com.sms_activate.qiwi.QiwiStatus;
import com.sms_activate.rent.Rent;
import com.sms_activate.rent.StateRentResponse;
import com.sms_activate.rent.StatusRentRequest;
import com.sms_activate.service.Service;
import com.sms_activate.service.ServiceWithCost;
import com.sms_activate.service.ServiceWithForward;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

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
   * Api key from personal
   */
  private final String apiKey;

  /**
   * Personal referral link.
   */
  private final String ref;

  /**
   * Constructor API sms-activate with API key.
   *
   * @param apiKey API key (not be null).
   * @throws WrongParameterException if api-key is incorrect.
   */
  public SMSActivateApi(@NotNull String apiKey, @NotNull String ref) throws WrongParameterException {
    if (apiKey.length() != 32) {
      throw new WrongParameterException("API-key of wrong length.", "API неправильной длины");
    }

    this.apiKey = apiKey;
    this.ref = ref;
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
   * Returns the referral link.
   *
   * @return referral link
   */
  @NotNull
  public String getRef() {
    return ref;
  }

  /**
   * Returns the current account balance.
   *
   * @return current account balance.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect.
   * @throws SQLServerException      if error happened on SQL-server.
   */
  @NotNull
  public BigDecimal getBalance()
      throws IOException, WrongParameterException, SQLServerException {
    URLBuilder URLBuilder = new URLBuilder(new HashMap<String, Object>() {{
      put(URLKey.API_KEY.getName(), apiKey);
      put(URLKey.ACTION.getName(), "getBalance");
    }});

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);

    String[] parts = data.split(":");

    return new BigDecimal(parts[1]);
  }

  /**
   * Returns the current account balance plus cashBack.
   *
   * @return current account balance plus cashBack.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect.
   * @throws SQLServerException      if error happened on SQL-server.
   */
  @NotNull
  public BigDecimal getBalanceAndCashBack()
      throws IOException, WrongParameterException, SQLServerException {
    URLBuilder URLBuilder = new URLBuilder(new HashMap<String, Object>() {{
      put(URLKey.API_KEY.getName(), apiKey);
      put(URLKey.ACTION.getName(), "getBalanceAndCashBack");
    }});

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);

    String[] parts = data.split(":");

    return new BigDecimal(parts[1]);
  }

  /**
   * Returns a list counts of available services.
   *
   * @return list counts of available services.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect.
   * @throws SQLServerException      if error happened on SQL-server.
   */
  @NotNull
  public List<ServiceWithForward> getNumbersStatus()
      throws IOException, WrongParameterException, SQLServerException {
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
   * @throws SQLServerException      if error happened on SQL-server.
   */
  @NotNull
  public List<ServiceWithForward> getNumbersStatus(@Nullable Integer countryId, @Nullable String operator)
      throws IOException, WrongParameterException, SQLServerException {
    if (countryId != null && countryId < 0) {
      throw new WrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    URLBuilder URLBuilder = new URLBuilder(URLKey.API_KEY.getName(), apiKey);
    URLBuilder.append(URLKey.ACTION.getName(), "getNumbersStatus")
        .append(URLKey.COUNTRY.getName(), countryId)
        .append(URLKey.OPERATOR.getName(), operator);

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);

    Type type = new TypeToken<Map<String, String>>() {
    }.getType();

    Map<String, String> serviceMap = gson.fromJson(data, type);
    List<ServiceWithForward> serviceList = new ArrayList<>();

    serviceMap.forEach((key, value) -> {
      String[] partsKey = key.split("_");

      serviceList.add(new ServiceWithForward(
          partsKey[0],
          Integer.parseInt(value),
          Boolean.parseBoolean(partsKey[1])
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
   * @throws SQLServerException      if error happened on SQL-server.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public Phone getNumber(
      @NotNull Service service,
      int countryId
  ) throws IOException, WrongParameterException, BannedException, SQLServerException, NoBalanceException, NoNumberException {
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
   * @throws SQLServerException      if error happened on SQL-server.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   * @throws BannedException         if account has been banned.
   */
  @NotNull
  public Phone getNumber(@NotNull Service service, int countryId, String phoneException,
                         String operator, boolean forward)
      throws IOException, SQLServerException, WrongParameterException, NoBalanceException, NoNumberException, BannedException {
    if (countryId < 0) {
      throw new WrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    URLBuilder URLBuilder = new URLBuilder(URLKey.API_KEY.getName(), apiKey);
    URLBuilder.append(URLKey.ACTION.getName(), "getNumber")
        .append(URLKey.REF.getName(), ref)
        .append(URLKey.SERVICE.getName(), service.getShortName())
        .append(URLKey.COUNTRY.getName(), countryId)
        .append(URLKey.PHONE_EXCEPTION.getName(), phoneException)
        .append(URLKey.OPERATOR.getName(), operator)
        .append(URLKey.FORWARD.getName(), forward ? "1" : "0");

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);
    Validator.throwNoNumbersOrNoBalanceExceptionByName(data);

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
   * @throws SQLServerException      if error happened on SQL-server.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public List<Phone> getMultiServiceNumber(@NotNull String multiService, int countryId)
      throws IOException, WrongParameterException, BannedException, SQLServerException, NoBalanceException, NoNumberException {
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
   * @throws SQLServerException      if error happened on SQL-server.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public List<Phone> getMultiServiceNumber(@NotNull String multiService, int countryId,
                                           @Nullable String multiForward, @Nullable String operator
  ) throws IOException, WrongParameterException, BannedException, SQLServerException, NoBalanceException, NoNumberException {
    String trimMultiService = multiService.replace("\\s", "");

    if (multiForward != null) {
      multiForward = multiForward.replace("\\s", "");
    } else if (countryId < 0) {
      throw new WrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    URLBuilder URLBuilder = new URLBuilder(URLKey.API_KEY.getName(), apiKey);
    URLBuilder.append(URLKey.ACTION.getName(), "getMultiServiceNumber")
        .append(URLKey.REF.getName(), ref)
        .append(URLKey.MULTI_SERVICE.getName(), trimMultiService)
        .append(URLKey.COUNTRY.getName(), countryId)
        .append(URLKey.MULTI_FORWARD.getName(), multiForward)
        .append(URLKey.OPERATOR.getName(), operator);

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);
    Validator.throwNoNumbersOrNoBalanceExceptionByName(data);

    if (data.contains("BANNED")) {
      String date = data.split(":")[1];
      throw new BannedException(date);
    }

    Type type = new TypeToken<List<Map<String, Object>>>() {
    }.getType();

    List<Map<String, Object>> phoneMapList = gson.fromJson(data, type);
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
   *
   * @param phone  phone to set activation status (not be null).
   * @param status value to establish (not be null).
   * @return access activation.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect.
   * @throws SQLServerException      if error happened on SQL-server.
   */
  @NotNull
  public AccessStatusActivation setStatus(@NotNull Phone phone, @NotNull StatusActivationRequest status)
      throws IOException, WrongParameterException, SQLServerException {
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
   * @throws SQLServerException      if error happened on SQL-server.
   */
  @NotNull
  public AccessStatusActivation setStatus(@NotNull Phone phone, @NotNull StatusActivationRequest status, boolean forward)
      throws IOException, WrongParameterException, SQLServerException {
    URLBuilder URLBuilder = new URLBuilder(URLKey.API_KEY.getName(), apiKey);
    URLBuilder.append(URLKey.ACTION.getName(), "setStatus")
        .append(URLKey.STATUS.getName(), status.getId())
        .append(URLKey.ID.getName(), phone.getId())
        .append(URLKey.FORWARD.getName(), forward ? "1" : "0");

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);

    return AccessStatusActivation.getStatusByName(data);
  }

  /**
   * Returns the state phone activation.
   *
   * @param phone phone id to get activation state (not be null).
   * @return state activation.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect.
   * @throws SQLServerException      if error happened on SQL-server.
   */
  @NotNull
  public StateActivationResponse getStatus(@NotNull Phone phone)
      throws IOException, WrongParameterException, SQLServerException {
    URLBuilder URLBuilder = new URLBuilder(URLKey.API_KEY.getName(), apiKey);
    URLBuilder.append(URLKey.ACTION.getName(), "getStatus")
        .append(URLKey.ID.getName(), phone.getId());

    String data = WebClient.get(URLBuilder.build());
    String code = null;

    Validator.throwCommonExceptionByName(data);

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
   * @throws SQLServerException      if error happened on SQL-server.
   * @throws WrongResponseException  if server response is not correct.
   */
  @NotNull
  public String getFullSms(@NotNull Phone phone)
      throws IOException, SQLServerException, WrongParameterException, WrongResponseException {
    URLBuilder URLBuilder = new URLBuilder(URLKey.API_KEY.getName(), apiKey);
    URLBuilder.append(URLKey.ACTION.getName(), "getFullSms")
        .append(URLKey.ID.getName(), phone.getId());

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);

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
   * @throws SQLServerException      if error happened on SQL-server.
   */
  @NotNull
  public List<ServiceByCountry> getPrices() throws SQLServerException, IOException, WrongParameterException {
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
   * @throws SQLServerException      if error happened on SQL-server.
   */
  @NotNull
  public List<ServiceByCountry> getPrices(@Nullable Service service, @Nullable Integer countryId)
      throws IOException, SQLServerException, WrongParameterException {
    if (countryId != null && countryId < 0) {
      throw new WrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    String shortNameService = (service == null) ? null : service.getShortName();

    URLBuilder URLBuilder = new URLBuilder(URLKey.API_KEY.getName(), apiKey);
    URLBuilder.append(URLKey.ACTION.getName(), "getPrices")
        .append(URLKey.SERVICE.getName(), shortNameService)
        .append(URLKey.COUNTRY.getName(), countryId);

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);

    Type type = new TypeToken<Map<String, Map<String, Map<String, Double>>>>() {
    }.getType();

    Map<String, Map<String, Map<String, Double>>> countryMap = gson.fromJson(data, type);
    List<ServiceByCountry> serviceByCountryList = new ArrayList<>();

    countryMap.forEach((countryCode, serviceMap) -> {
      List<ServiceWithCost> serviceWithCostList = new ArrayList<>();

      serviceMap.forEach((shortName, value) -> {
        serviceWithCostList.add(new ServiceWithCost(
            shortName,
            BigDecimal.valueOf(value.get("count")).intValue(),
            BigDecimal.valueOf(value.get("cost"))
        ));
      });

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
   * @throws SQLServerException      if error happened on SQL-server.
   */
  @NotNull
  public List<Country> getCountries()
      throws IOException, WrongParameterException, SQLServerException {
    URLBuilder URLBuilder = new URLBuilder(new HashMap<String, Object>() {{
      put(URLKey.API_KEY.getName(), apiKey);
      put(URLKey.ACTION.getName(), "getCountries");
    }});

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);

    Type type = new TypeToken<Map<String, Map<String, Object>>>() {
    }.getType();

    Map<String, Map<String, Object>> countryInformationMap = gson.fromJson(data, type);
    List<Country> countryList = new ArrayList<>();

    for (Map<String, Object> countryMap : countryInformationMap.values()) {
      int id = new BigDecimal(countryMap.get("id").toString()).intValue();

      String rus = countryMap.get("rus").toString();
      String eng = countryMap.get("eng").toString();
      String chn = countryMap.get("chn").toString();

      boolean isVisible = Boolean.parseBoolean(countryMap.get("visible").toString());
      boolean isSupportRetry = Boolean.parseBoolean(countryMap.get("retry").toString());
      boolean isSupportRent = Boolean.parseBoolean(countryMap.get("rent").toString());
      boolean isSupportMultiService = Boolean.parseBoolean(countryMap.get("multiService").toString());

      countryList.add(new Country(id, rus, eng, chn,
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
   * @throws SQLServerException      if error happened on SQL-server.
   */
  @NotNull
  public QiwiResponse getQiwiRequisites()
      throws IOException, SQLServerException, WrongParameterException {
    URLBuilder URLBuilder = new URLBuilder(new HashMap<String, Object>() {{
      put(URLKey.API_KEY.getName(), apiKey);
      put(URLKey.ACTION.getName(), "getQiwiRequisites");
    }});

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);

    Type type = new TypeToken<Map<String, String>>() {
    }.getType();
    Map<String, String> qiwiMap = gson.fromJson(data, type);

    QiwiStatus qiwiStatus = QiwiStatus.getStatusByName(qiwiMap.get("status").toUpperCase());

    return new QiwiResponse(qiwiStatus, qiwiMap.get("wallet"), qiwiMap.get("comment"));
  }

  /**
   * Returns the phone for additional service by forwarding.
   *
   * @param phone phone activation.
   * @return phone for additional service by forwarding
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect.
   * @throws SQLServerException      if error happened on SQL-server.
   */
  @NotNull
  public Phone getAdditionalService(@NotNull Phone phone, @NotNull Service service)
      throws IOException, SQLServerException, WrongParameterException {
    URLBuilder URLBuilder = new URLBuilder(URLKey.API_KEY.getName(), apiKey);
    URLBuilder.append(URLKey.ACTION.getName(), "getAdditionalService")
        .append(URLKey.ID.getName(), phone.getId())
        .append(URLKey.SERVICE.getName(), service.getShortName());

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);

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
   * @throws SQLServerException      if error happened on SQL-server.
   */
  @NotNull
  public List<Phone> getCurrentActivationsDataTables()
      throws IOException, BaseSMSActivateException {
    return getCurrentActivationsDataTables(0, 10);
  }

  /**
   * Returns the list current activation.
   *
   * @param start  (default 0).
   * @param length (default 10).
   * @return returns the list activation.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect.
   * @throws SQLServerException      if error happened on SQL-server.
   * @throws WrongResponseException  if response incorrect.
   */
  @NotNull
  public List<Phone> getCurrentActivationsDataTables(int start, int length)
      throws IOException, WrongResponseException, BaseSMSActivateException {
    URLBuilder URLBuilder = new URLBuilder(URLKey.API_KEY.getName(), apiKey);
    URLBuilder.append(URLKey.ACTION.getName(), "getCurrentActivationsDataTables")
        .append(URLKey.START.getName(), start)
        .append(URLKey.LENGTH.getName(), length)
        .append(URLKey.ORDER.getName(), URLKey.ID.getName())
        .append(URLKey.ORDER_BY.getName(), "asc");

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);

    Type type = new TypeToken<Map<String, Object>>() {
    }.getType();

    Map<String, Object> responseMap = gson.fromJson(data, type);

    if (responseMap.get("status").toString().equalsIgnoreCase("fail")) {
      throw new BaseSMSActivateException("No activations.", "Нет активаций.");
    }

    List<Map<String, Object>> currentPhoneActivationList = (List<Map<String, Object>>) responseMap.get("array");
    List<Phone> phoneList = new ArrayList<>();

    for (Map<String, Object> currentPhoneActivationMap : currentPhoneActivationList) {
      int id = new BigDecimal(currentPhoneActivationMap.get("id").toString()).intValue();
      boolean forward = Boolean.parseBoolean(currentPhoneActivationMap.get("forward").toString());

      String number = String.valueOf(currentPhoneActivationMap.get("phone"));
      Service service = new Service(currentPhoneActivationMap.get("service").toString());

      phoneList.add(new Phone(number, service, id, forward));
    }

    return phoneList;
  }

  /**
   * Returns the rent object with countries supported rent and accessed services by country.
   *
   * @return rent object with countries supported rent and accessed services by country.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect.
   * @throws SQLServerException      if error happened on SQL-server.
   */
  @NotNull
  public Rent getRentServicesAndCountries()
      throws IOException, SQLServerException, WrongParameterException {
    return getRentServicesAndCountries(1, null, 0);
  }

  /**
   * Returns the rent object with countries supported rent and accessed services by country.
   *
   * @param time      time rent in hours (default 1).
   *                  time >= 1
   * @param operator  mobile operator.
   * @param countryId id country (default 0).
   * @return the rent object with countries supported rent and accessed services by country.
   * @throws IOException             if an I/O exception occurs.
   * @throws WrongParameterException if one of parameters is incorrect.
   * @throws SQLServerException      if error happened on SQL-server.
   */
  @NotNull
  public Rent getRentServicesAndCountries(int time, String operator, int countryId)
      throws IOException, SQLServerException, WrongParameterException {
    if (time <= 0) {
      throw new WrongParameterException("Time can't be negative or equals 0.", "Время не может быть меньше или равно 0");
    } else if (countryId < 0) {
      throw new WrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    URLBuilder URLBuilder = new URLBuilder(URLKey.API_KEY.getName(), apiKey);
    URLBuilder.append(URLKey.ACTION.getName(), "getRentServicesAndCountries")
        .append(URLKey.COUNTRY.getName(), countryId)
        .append(URLKey.OPERATOR.getName(), operator)
        .append(URLKey.TIME.getName(), time);

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);

    Type type = new TypeToken<Map<String, Map<String, Object>>>() {
    }.getType();

    Map<String, Map<String, Object>> rentCountriesServices = gson.fromJson(data, type);

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
   * @throws SQLServerException      if error happened on SQL-server.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public PhoneRent getRentNumber(@NotNull Service service)
      throws IOException, SQLServerException, RentException, WrongParameterException, NoBalanceException, NoNumberException {
    return getRentNumber(service, 1, null, 0, null);
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
   * @throws SQLServerException      if error happened on SQL-server.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public PhoneRent getRentNumber(
      @NotNull Service service, int time,
      @Nullable String operator, int countryId,
      @Nullable String urlWebhook
  ) throws IOException, SQLServerException, WrongParameterException, RentException, NoBalanceException, NoNumberException {
    if (countryId < 0) {
      throw new WrongParameterException("Wrong ID country.", "Неверный ID страны.");
    }

    URLBuilder URLBuilder = new URLBuilder(URLKey.API_KEY.getName(), apiKey);
    URLBuilder.append(URLKey.ACTION.getName(), "getRentNumber")
        .append(URLKey.RENT_TIME.getName(), time)
        .append(URLKey.COUNTRY.getName(), countryId)
        .append(URLKey.OPERATOR.getName(), operator)
        .append(URLKey.URL.getName(), urlWebhook)
        .append(URLKey.SERVICE.getName(), service.getShortName());

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);
    Validator.throwNoNumbersOrNoBalanceExceptionByName(data);

    Type type = new TypeToken<Map<String, Object>>() {
    }.getType();

    Map<String, Object> phoneMap = gson.fromJson(data, type);
    StateRentResponse statusRent = StateRentResponse.getStateRentByName(phoneMap.get("status").toString().toUpperCase());

    if (statusRent == StateRentResponse.ERROR) {
      String error = phoneMap.get("message").toString();
      Validator.throwRentExceptionsByName(error);
    }

    phoneMap = (Map<String, Object>) phoneMap.get("phone");

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
   * @throws RentException           if rent is cancel or finish.
   * @throws WrongParameterException if one of parameters is incorrect.
   * @throws SQLServerException      if error happened on SQL-server.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public List<Sms> getRentStatus(@NotNull Phone phone)
      throws IOException, SQLServerException, WrongParameterException, RentException, NoBalanceException, NoNumberException {
    URLBuilder URLBuilder = new URLBuilder(URLKey.API_KEY.getName(), apiKey);
    URLBuilder.append(URLKey.ACTION.getName(), "getRentStatus").
        append(URLKey.ID.getName(), phone.getId());

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);
    Validator.throwNoNumbersOrNoBalanceExceptionByName(data);

    Type type = new TypeToken<Map<String, Object>>() {
    }.getType();

    Map<String, Object> responseMap = gson.fromJson(data, type);
    StateRentResponse stateRentResponse = StateRentResponse.getStateRentByName(responseMap.get("status").toString());

    if (stateRentResponse == StateRentResponse.ERROR) {
      String error = responseMap.get("message").toString();
      Validator.throwRentExceptionsByName(error);
    }

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
   * @throws SQLServerException      if error happened on SQL-server.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public StateRentResponse setRentStatus(@NotNull Phone phone, @NotNull StatusRentRequest status)
      throws IOException, SQLServerException, WrongParameterException, RentException, NoBalanceException, NoNumberException {
    URLBuilder URLBuilder = new URLBuilder(URLKey.API_KEY.getName(), apiKey);
    URLBuilder.append(URLKey.ACTION.getName(), "setRentStatus")
        .append(URLKey.ID.getName(), phone.getId())
        .append(URLKey.STATUS.getName(), status.getId());

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);

    Type type = new TypeToken<Map<String, String>>() {
    }.getType();

    Map<String, String> stateMap = gson.fromJson(data, type);
    StateRentResponse stateRentResponse = StateRentResponse.getStateRentByName(stateMap.get("status").toUpperCase());

    if (stateRentResponse == StateRentResponse.ERROR) {
      String error = stateMap.get("message");
      Validator.throwRentExceptionsByName(error);
    }

    return StateRentResponse.SUCCESS;
  }

  /**
   * Returns the list rent phones.
   *
   * @return list rent phones
   * @throws IOException             if an I/O exception occurs.
   * @throws RentException           if rent is cancel or finish.
   * @throws WrongParameterException if one of parameters is incorrect.
   * @throws SQLServerException      if error happened on SQL-server.
   * @throws NoBalanceException      if no numbers.
   * @throws NoNumberException       if in account balance is zero.
   */
  @NotNull
  public List<Phone> getRentList()
      throws IOException, SQLServerException, WrongParameterException, RentException, NoBalanceException, NoNumberException {
    URLBuilder URLBuilder = new URLBuilder(new HashMap<String, Object>() {{
      put(URLKey.API_KEY.getName(), apiKey);
      put(URLKey.ACTION.getName(), "getRentList");
    }});

    String data = WebClient.get(URLBuilder.build());

    Validator.throwCommonExceptionByName(data);
    Validator.throwNoNumbersOrNoBalanceExceptionByName(data);

    Type type = new TypeToken<Map<String, Object>>() {
    }.getType();

    Map<String, Object> responseMap = gson.fromJson(data, type);
    StateRentResponse stateRentResponse = StateRentResponse.getStateRentByName(responseMap.get("status").toString().toUpperCase());

    if (stateRentResponse != StateRentResponse.SUCCESS) {
      String error = responseMap.get("message").toString();
      Validator.throwRentExceptionsByName(error);
    }

    Map<String, Map<String, Object>> valuesMap = (Map<String, Map<String, Object>>) responseMap.get("values");
    List<Phone> phoneList = new ArrayList<>();

    for (Map<String, Object> phoneMap : valuesMap.values()) {
      String number = phoneMap.get("phone").toString();
      int id = new BigDecimal(phoneMap.get("id").toString()).intValue();

      phoneList.add(new Phone(number, null, id, false));
    }

    return phoneList;
  }
}
