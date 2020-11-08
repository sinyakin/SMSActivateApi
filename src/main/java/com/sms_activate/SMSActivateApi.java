package com.sms_activate;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.sms_activate.error.BannedException;
import com.sms_activate.error.NoBalanceException;
import com.sms_activate.error.NoNumberException;
import com.sms_activate.error.SQLServerException;
import com.sms_activate.error.WrongParameter;
import com.sms_activate.error.WrongParameterException;
import com.sms_activate.phone.Phone;
import com.sms_activate.phone.PhoneRent;
import com.sms_activate.activation.AccessStatusActivation;
import com.sms_activate.activation.StateActivation;
import com.sms_activate.activation.StatusActivation;
import com.sms_activate.country.Country;
import com.sms_activate.country.CountryInformation;
import com.sms_activate.error.rent.RentException;
import com.sms_activate.error.rent.TimeOutRentException;
import com.sms_activate.qiwi.QiwiResponse;
import com.sms_activate.qiwi.QiwiStatus;
import com.sms_activate.error.rent.StateErrorRent;
import com.sms_activate.rent.StatusRent;
import com.sms_activate.rent.StateRent;
import com.sms_activate.rent.StatusRentNumber;
import com.sms_activate.service.Service;
import com.sms_activate.service.ServiceCost;
import com.sms_activate.service.ServiceForward;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO:
 *  Refactoring with exception
 *  Add rent methods
 *  Change money volume to BigDecimal
 *  Refactoring getDataByUrl
 *  methods with API -> SMSActivateUrls
 *  keySet -> Entry
 *  condition on gzip
 *  countryNumber -> countryId
 *  change method POST -> GET
 *  create parent LOCALE_Exception
 *  validate data from getting.
 */
public final class SMSActivateApi {
    /**
     * API url.
     */
    private static final String BASE_URL = "https://sms-activate.ru/stubs/handler_api.php?";

    /**
     * Regular expression for digit. Use for select balance and checking on matchers.
     */
    private static final Pattern digitPattern = Pattern.compile("\\d.*");

    /**
     * Json deserializer and serializer.
     */
    private static final Gson gson = new Gson();

    /**
     * Api key from personal
     */
    private String apiKey;

    /**
     * Constructor API sms-activate with API key.
     * @param apiKey API key (not be null).
     */
    public SMSActivateApi(@NotNull String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Sets the value apiKey.
     * @param apiKey API key (not be null).
     */
    public void setApiKey(@NotNull String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Returns the API key.
     * @return apiKey API key (not be null).
     */
    @NotNull
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Returns the current account balance.
     * @return current account balance.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if parameter is incorrect.
     */
    @NotNull
    public BigDecimal getBalance() throws IOException, WrongParameterException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
        }});

        String data = getDataByUrl(new URL(url), "GET");

        Matcher matcher = digitPattern.matcher(data);

        if (!matcher.find()) {
            if (data.contains("BAD")) {
                WrongParameter parameter = WrongParameter.valueOf(WrongParameter.class, data);
                throw new WrongParameterException(parameter.getMessage());
            } else {
                throw new IOException();
            }
        }

        return new BigDecimal(matcher.group());
    }

    /**
     * Returns the current account balance plus cashBack.
     * @return current account balance plus cashBack.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if parameter is incorrect.
     */
    @NotNull
    public BigDecimal getBalanceAndCashBack() throws IOException, WrongParameterException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();

        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
        }});

        String data = getDataByUrl(new URL(url), "GET");
        Matcher matcher = digitPattern.matcher(data);

        if (!matcher.find()) {
            WrongParameter parameter = WrongParameter.valueOf(WrongParameter.class, data);
            throw new WrongParameterException(parameter.getMessage());
        }

        return new BigDecimal(matcher.group());
    }

    /**
     * Returns a list counts of available services.
     * @return list counts of available services.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if parameter is incorrect.
     */
    @NotNull
    public List<ServiceForward> getNumbersStatus() throws IOException, WrongParameterException {
        return getNumbersStatus(-1, "");
    }

    /**
     * Return a list counts of available services by country and operator.
     * @param countryNumber id country.
     * @param operator name operator mobile network (not be null).
     * @return list counts of available services by county and operator (not be null).
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if parameter is incorrect.
     */
    @NotNull
    public List<ServiceForward> getNumbersStatus(int countryNumber, @NotNull String operator) throws IOException, WrongParameterException {
        String action = new Object() {}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>() {{
            put("api_key", apiKey);
            put("action", action);
            put("country", countryNumber == -1 ? "" : countryNumber + "");
            put("operator", operator);
        }});

        String data = getDataByUrl(new URL(url), "GET");

        try {
            Map<String, String> serviceMap = gson.fromJson(data, HashMap.class);
            List<ServiceForward> serviceList = new ArrayList<>();

            for (String key : serviceMap.keySet()) {
                String[] partsKey = key.split("_");

                serviceList.add(new ServiceForward(
                    "",
                    partsKey[0],
                    Integer.parseInt(serviceMap.get(key)),
                    Boolean.parseBoolean(partsKey[1])
                ));
            }

            return serviceList;
        } catch (JsonSyntaxException e) {
            WrongParameter parameter = WrongParameter.valueOf(WrongParameter.class, data);
            throw new WrongParameterException(parameter.getMessage());
        }
    }

    /**
     * Returns the phone by service, ref, countryNumber.
     * @param service service short name.
     * @param ref referral link.
     * @param countryNumber number country.
     * @return phone for activation.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws RentException
     * @throws NoBalanceException
     * @throws NoNumberException
     */
    @NotNull
    public Phone getNumber(
            @NotNull Service service,
            @NotNull String ref,
            int countryNumber
    ) throws IOException, WrongParameterException, BannedException, SQLServerException, RentException, NoBalanceException, NoNumberException {
        return getNumber(service, ref, countryNumber, "", "", false);
    }

    /**
     * Returns the phone number by service, ref, countryNumber, phoneException, operator, forward
     * @param service service for activation.
     * @param ref referral link.
     * @param countryNumber number country.
     * @param phoneException excepted phone number prefix. Specify separated by commas.
     * <pre>{@code   7918,7900111}</pre>
     * @param operator mobile operator. May be specify separated by commas.
     * @param forward is it necessary to request a number with forwarding.
     * @return object phone
     * @throws IOException if an I/O occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     */
    public @NotNull Phone getNumber(
            @NotNull Service service,
            @NotNull String ref,
            int countryNumber,
            @NotNull String phoneException,
            @NotNull String operator,
            boolean forward
    ) throws IOException, WrongParameterException, BannedException, SQLServerException, RentException, NoBalanceException, NoNumberException {

        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>() {{
            put("api_key", apiKey);
            put("action", action);
            put("ref", ref);
            put("service", service.getShortName());
            put("country", countryNumber + "");
            put("phoneException", phoneException);
            put("operator", operator);
            put("forward", (forward ? 1 : 0) + "");
        }});

        String data = getDataByUrl(new URL(url), "POST");
        validateData(data);
        
        String[] parts = data.split(":");

        String number = parts[2];
        int id = (int)Math.round(Double.parseDouble(parts[1]));

        return new Phone(number, id, forward, service);
    }

    /**
     * Returns the list phone by countryCode, multiService, ref.<br/>
     * Separator for multiService is commas. <br/>
     * <pre> multiService -> vk,av,go,tg. </pre>
     * @param multiService services for ordering (not be null).
     * @param ref referral link (not be null).
     * @param countryCode code country.
     * @return list phone.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if parameter is incorrect.
     * @throws BannedException if account is banned.
     * @throws SQLServerException if error happened on SQL-server.
     */
    @NotNull
    public List<Phone> getMultiServiceNumber(
            @NotNull String multiService,
            @NotNull String ref,
            int countryCode
    ) throws IOException, WrongParameterException, BannedException, SQLServerException {
        return getMultiServiceNumber(multiService, ref, countryCode, "", "");
    }

    /**
     * Returns the list phone by countryCode, multiService, ref.<br/>
     * Separator for multiService, multiForward and operator is commas. <br/>
     * <pre>multiService -> vk,av,go,tg<br/>multiForward -> 0,0,1,0; 0,0,0,0 - correct; 0,1,1,0 - incorrect.</pre>
     * @param multiService services for ordering (not be null).
     * @param ref referral link (not be null).
     * @param countryCode code country.
     * @param multiForward is it necessary to request a number with forwarding (not be null).
     * @param operator mobile operator (not be null).
     * @return list phone.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if parameter is incorrect.
     * @throws BannedException if account is banned.
     * @throws SQLServerException if error happened on SQL-server.
     */
    @NotNull
    public List<Phone> getMultiServiceNumber(
            @NotNull String multiService,
            @NotNull String ref,
            int countryCode,
            @NotNull String multiForward,
            @NotNull String operator
    ) throws IOException, WrongParameterException, BannedException, SQLServerException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String trimMultiForward = multiForward.replace(" ", "");
        String trimMultiService = multiService.replace(" ", "");

        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("ref", ref);
            put("multiService", trimMultiService);
            put("countryCode", countryCode + "");
            put("multiForward", trimMultiForward);
            put("operator", operator.replace(" ", ""));
        }});

        String data = getDataByUrl(new URL(url), "POST");

        try {
            List<Map<String, Object>> phoneMapList = gson.fromJson(data, List.class);
            List<Phone> phoneList = new ArrayList<>();
            int indexForwardPhoneNumber = Arrays.asList(multiForward.split(",")).indexOf("1"); //index phone where need forwarding

            for (int i = 0; i < phoneMapList.size(); i++) {
                Map<String, Object> phoneMap = phoneMapList.get(i);
                int id = (int) Math.round((Double)phoneMap.get("activation"));
                String phone = phoneMap.get("phone").toString();
                String serviceName = phoneMap.get("service").toString();

                phoneList.add(new Phone(
                    phone,
                    id,
                    i == indexForwardPhoneNumber,
                    new Service("" , serviceName)
                ));
            }

            return phoneList;
        } catch (JsonSyntaxException e) {
            if (data.equalsIgnoreCase("banned")) {
                String date = ":".split(data)[1];
                throw new BannedException("Акаунт забанен на " + date);
            } else if (data.contains("SQL")) {
                throw new SQLServerException("Ошибка SQL-сервера.");
            } else if (data.contains("NO")) {
                return null;
            }

            WrongParameter parameter = WrongParameter.valueOf(WrongParameter.class, data);
            throw new WrongParameterException(parameter.getMessage());
        }
    }

    /**
     * Sets the status activation.
     * @param phone phone to set activation status (not be null).
     * @param status value to establish (not be null).
     * @return access activation
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws SQLServerException if error happened on SQL-server.
     */
    @NotNull
    public AccessStatusActivation setStatus(
            @NotNull Phone phone,
            @NotNull StatusActivation status
    ) throws IOException, SQLServerException, WrongParameterException {
        return setStatus(phone, status, false);
    }

    /**
     * Sets the status activation.
     * @param phone phone to set activation status (not be null).
     * @param status value to establish (not be null).
     * @param forward number is forwarding.
     * @return access activation.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws SQLServerException if error happened on SQL-server.
     */
    @NotNull
    public AccessStatusActivation setStatus(
            @NotNull Phone phone,
            @NotNull StatusActivation status,
            boolean forward
    ) throws IOException, SQLServerException, WrongParameterException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("status", status.getId() + "");
            put("id", phone.getId() + "");

            if (forward) {
                put("forward", "1");
            }
        }});

        String data = getDataByUrl(new URL(url), "POST");

        try {
            return AccessStatusActivation.valueOf(AccessStatusActivation.class, data);
        } catch (Exception e) {
            if (data.contains("SQL")) {
                throw new SQLServerException();
            } else if (data.contains("NO")) {
                return null;
            }

            WrongParameter wrongParameter = WrongParameter.valueOf(WrongParameter.class, data);
            throw new WrongParameterException(wrongParameter.getMessage());
        }
    }

    /**
     * Returns the state phone activation.
     * @param phone phone id to get activation state (not be null).
     * @return state activation.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws SQLServerException if error happened on SQL-server.
     */
    @NotNull
    public StateActivation getStatus(@NotNull Phone phone) throws IOException, WrongParameterException, SQLServerException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("id", phone.getId() + "");
        }});

        String data = getDataByUrl(new URL(url), "GET");

        try {
            String name = data;
            String code = null;

            if (data.contains(":")) {
                String[] parts = data.split(":");

                name = parts[0];
                code = parts[1];
            }

            StateActivation state = StateActivation.valueOf(StateActivation.class, data);
            state.setCode(code);

            return state;
        } catch (Exception e) {
            if (data.contains("NO")) {
                return null;
            } else if (data.contains("SQL")) {
                throw new SQLServerException();
            }

            WrongParameter parameter = WrongParameter.valueOf(WrongParameter.class, data);
            throw new WrongParameterException(parameter.getMessage());
        }
    }

    /**
     * Returns the full text sms.
     * @param id id activation.
     * @return full text sms.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws SQLServerException if error happened on SQL-server.
     */
    @NotNull
    public String getFullSms(@NotNull String id) throws IOException, SQLServerException, WrongParameterException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("id", id);
        }});

        String data = getDataByUrl(new URL(url), "GET");

        try {
            if (data.contains("FULL")) {
                return data;
            } else {
                return StateActivation.valueOf(StateActivation.class, data).getMessage();
            }
        } catch (Exception e) {
            if (data.contains("NO")) {
                return null;
            } else if (data.contains("SQL")) {
                throw new SQLServerException();
            }

            WrongParameter parameter = WrongParameter.valueOf(WrongParameter.class, data);
            throw new WrongParameterException(parameter.getMessage());
        }
    }

    /**
     * Returns the actual prices by country.
     * @param service service for needed price list.
     * <pre>{@code new Service("full") -> all service,
     * new Service(""), -1 -> all service and all country.}</pre>
     * @param countryNumber country number.
     * @return price list country.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws SQLServerException if error happened on SQL-server.
     */
    @NotNull
    public List<Country> getPrices(@NotNull Service service, int countryNumber) throws IOException, SQLServerException, WrongParameterException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String shortNameService = service.getShortName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);

            if (!shortNameService.isEmpty()) {
                put("service", shortNameService);
            } if (countryNumber != -1) {
                put("country", countryNumber + "");
            }
        }});

        String data = getDataByUrl(new URL(url), "GET");

        try {
            Map<String, Map<String, Map<String, Double>>> countryMap = gson.fromJson(data, Map.class);
            List<Country> countryList = new ArrayList<>();

            countryMap.forEach((countryCode, serviceMap) -> {
                List<ServiceCost> serviceCostList = new ArrayList<>();

                serviceMap.forEach((shortName, value) -> {
                    serviceCostList.add(new ServiceCost(
                        "",
                        shortName,
                        (int)Math.round(value.get("count")), 
                        BigDecimal.valueOf(value.get("cost"))
                    ));
                });

                countryList.add(new Country(
                    new CountryInformation(Integer.parseInt(countryCode)),
                    serviceCostList
                ));
            });

            return countryList;
        } catch (Exception e) {
            if (data.contains("SQL")) {
                throw new SQLServerException();
            }

            WrongParameter parameter = WrongParameter.valueOf(WrongParameter.class, data);
            throw new WrongParameterException(parameter.getMessage());
        }
    }

    /**
     * Returns the country information.
     * @return country information.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws SQLServerException if error happened on SQL-server.
     */
    @NotNull
    public List<CountryInformation> getCountries() throws IOException, WrongParameterException, SQLServerException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
        }});

        String data = getDataByUrl(new URL(url), "GET");

        try {
            Map<String, Map<String, Object>> countryInformationMap = gson.fromJson(data, Map.class);
            List<CountryInformation> countryInformationList = new ArrayList<>();

            for (Map<String, Object> countryMap : countryInformationMap.values()) {
                int id = (int)Math.round(Double.parseDouble(countryMap.get("id") + ""));
                String rus = countryMap.get("rus") + "";
                String eng = countryMap.get("eng") + "";
                String chn = countryMap.get("chn") + "";
                boolean isVisible = Boolean.parseBoolean(countryMap.get("visible") + "");
                boolean isSupportRetry = Boolean.parseBoolean(countryMap.get("retry") + "");
                boolean isSupportRent = Boolean.parseBoolean(countryMap.get("rent") + "");
                boolean isSupportMultiService = Boolean.parseBoolean(countryMap.get("multiService") + "");

                countryInformationList.add(new CountryInformation(id, rus, eng, chn,
                        isVisible, isSupportRetry, isSupportRent, isSupportMultiService));
            }

            return countryInformationList;
        } catch (JsonSyntaxException ignored) {
            if (data.contains("SQL")) {
                throw new SQLServerException();
            } else if (data.contains("NO")) {
                return null;
            }

            WrongParameter parameter = WrongParameter.valueOf(WrongParameter.class, data);
            throw new WrongParameterException(parameter.getMessage());
        }
    }

    /**
     * Returns the qiwi response with data on wallet.
     * @return qiwi response with data on wallet
     * @throws IOException if an I/O exception occurs.
     * @throws SQLServerException if error happened on sql server.
     * @throws WrongParameterException if parameter is incorrect.
     */
    @NotNull
    public QiwiResponse getQiwiRequisites() throws IOException, SQLServerException, WrongParameterException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
        }});
        String data = getDataByUrl(new URL(url), "GET");

        try {
            Map<String, String> qiwiMap = gson.fromJson(data, Map.class);
            QiwiStatus qiwiStatus = QiwiStatus.valueOf(QiwiStatus.class, qiwiMap.get("status").toUpperCase());

            return new QiwiResponse(qiwiStatus, qiwiMap.get("wallet"), qiwiMap.get("comment"));
        } catch (JsonSyntaxException ignored) {
            if (data.contains("SQL")) {
                throw new SQLServerException();
            }

            WrongParameter parameter = WrongParameter.valueOf(data);
            throw new WrongParameterException(parameter.getMessage());
        }
    }

    /**
     * Returns the phone for additional service by forwarding.
     * @param phone phone activation.
     * @return phone for additional service by forwarding
     * @throws IOException if an I/O exception occurs.
     */
    @NotNull
    public Phone getAdditionalService(Phone phone) throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("parentId", phone.getId() + "");
        }});

        String data = getDataByUrl(new URL(url), "POST");
        String[] parts = data.split(":");

        String number = parts[2];
        int id = (int)Math.round(Double.parseDouble(parts[1]));

        return new Phone(number, id, false, phone.getService());
    }





    /**
     *
     * @return
     * @throws IOException
     */
    public String getRentServicesAndCountries() throws IOException {
        return getRentServicesAndCountries(1, "", 0);
    }

    /**
     *
     * @param time
     * @param operator
     * @param countryId
     * @return
     * @throws IOException
     */
    public String getRentServicesAndCountries(
            int time,
            @NotNull String operator,
            int countryId
    ) throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("time", time + "");
            put("county", countryId + "");

            if (!operator.isEmpty()) {
                put("operator", operator);
            }
        }});



        return getDataByUrl(new URL(url), "POST");
    }





    /**
     * Returns the rent phone.
     * @param service service to which you need to get a number.
     * @return rent phone.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    public PhoneRent getRentNumber(@NotNull Service service) throws IOException, SQLServerException, RentException, WrongParameterException, NoBalanceException, NoNumberException {
       return getRentNumber(service, 1, "", "", "");
    }

    /**
     * Returns the rent phone.
     * @param service service to which you need to get a number.
     * @param time time rent (default 1 hour).
     * @param operator mobile operator.
     * @param country countryCode.
     * @param urlWebhook url for webhook.
     * @return rent phone.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    public PhoneRent getRentNumber(
            @NotNull Service service,
            int time,
            @NotNull String operator,
            @NotNull String country,
            @NotNull String urlWebhook
    ) throws IOException, SQLServerException, WrongParameterException, RentException, NoBalanceException, NoNumberException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            String serviceShortName = service.getShortName();

            put("api_key", apiKey);
            put("action", action);
            put("time", time + "");

            if (!serviceShortName.isEmpty()) {
                put("service", serviceShortName);
            } if (!operator.isEmpty()){
                put("operator", operator);
            } if (!country.isEmpty()) {
                put("country", country);
            } if (!urlWebhook.isEmpty()) {
                put("url", urlWebhook);
            }
        }});

        String data = getDataByUrl(new URL(url), "POST");

        try {
            Map<String, Object> phoneMap = gson.fromJson(data, Map.class);
            StatusRentNumber statusRent = StatusRentNumber.valueOf(StatusRentNumber.class, phoneMap.get("status").toString().toUpperCase());

            if (statusRent == StatusRentNumber.ERROR) {
                String errorMessage = phoneMap.get("message").toString();
                StateErrorRent stateErrorRent = StateErrorRent.valueOf(StateErrorRent.class, errorMessage);

                throwStateErrorRent(stateErrorRent);
            }

            phoneMap = (Map<String, Object>) phoneMap.get("phone");

            String number = phoneMap.get("number").toString();
            int id = (int)Math.round(Double.parseDouble(phoneMap.get("id").toString()));
            String endDate = phoneMap.get("endDate").toString();

            return new PhoneRent(number, id, false, service, endDate);
        } catch (JsonSyntaxException e) {
            throwErrorByName(data);

            WrongParameter parameter = WrongParameter.valueOf(WrongParameter.class, data);
            throw new WrongParameterException(parameter.getMessage());
        }
    }

    /**
     * Returns the list sms.
     * @param phone phone received in response when ordering a number.
     * @return list sms.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws RentException if rent is cancel or finish.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    public List<Sms> getRentStatus(@NotNull Phone phone) throws IOException, SQLServerException, WrongParameterException, RentException, NoBalanceException, NoNumberException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("id", phone.getId() + "");
        }});

        String data = getDataByUrl(new URL(url), "POST");

        try {
            Map<String, Object> responseMap = gson.fromJson(data, Map.class);
            StateRent stateRent = StateRent.valueOf(StateRent.class, responseMap.get("status").toString());

            if (stateRent == StateRent.ERROR) {
                StateErrorRent stateErrorRent = StateErrorRent.valueOf(StateErrorRent.class, responseMap.get("message").toString());
                throwStateErrorRent(stateErrorRent);
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
        } catch (JsonSyntaxException ignored) {
            throwErrorByName(data);

            WrongParameter parameter = WrongParameter.valueOf(WrongParameter.class, data);
            throw new WrongParameterException(parameter.getMessage());
        }
    }

    /**
     * Sets the status on rent.
     * @param phone phone for set status rent.
     * @param status status rent.
     * @return state rent.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws RentException if rent is cancel or finish.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    public StateRent setRentStatus(Phone phone, @NotNull StatusRent status)
            throws IOException, SQLServerException, WrongParameterException, RentException, NoBalanceException, NoNumberException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("id", phone.getId() + "");
            put("status", status.getId() + "");
        }});

        String data = getDataByUrl(new URL(url), "POST");
        try {
            Map<String, String> stateMap = gson.fromJson(data, Map.class);
            StateRent stateRent = StateRent.valueOf(StateRent.class, stateMap.get("status").toUpperCase());

            if (stateRent == StateRent.ERROR) {
                StateErrorRent stateErrorRent = StateErrorRent.valueOf(StateErrorRent.class, stateMap.get("message"));
                throwStateErrorRent(stateErrorRent);
            }

            return StateRent.SUCCESS;
        } catch (JsonSyntaxException ignored) {
            throwErrorByName(data);

            WrongParameter parameter = WrongParameter.valueOf(WrongParameter.class, data);
            throw new WrongParameterException(parameter.getMessage());
        }
    }

    /**
     * Returns the list rent phones.
     * @return list rent phones
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    public List<Phone> getRentList()
            throws IOException, SQLServerException, WrongParameterException, RentException, NoBalanceException, NoNumberException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
        }});

        String data = getDataByUrl(new URL(url), "GET");

        try {
            Map<String, Object> responseMap = gson.fromJson(data, Map.class);
            StateRent stateRent = StateRent.valueOf(StateRent.class, responseMap.get("status").toString().toUpperCase());

            if (stateRent == StateRent.SUCCESS) {
                Map<String, Map<String, Object>> valuesMap = (Map<String, Map<String, Object>>)responseMap.get("values");
                List<Phone> phoneList = new ArrayList<>();

                for (Map<String, Object> phoneMap : valuesMap.values()) {
                    String number = phoneMap.get("phone").toString();
                    int id = (int)Math.round(Double.parseDouble(phoneMap.get("id").toString()));

                    phoneList.add(new Phone(number, id, false, new Service("")));
                }

                return phoneList;
            }

            StateErrorRent stateErrorRent = StateErrorRent.valueOf(StateErrorRent.class,
                    responseMap.get("message").toString().toUpperCase());

            if (stateErrorRent == StateErrorRent.NO_NUMBERS) {
                throw new NoNumberException(StateErrorRent.NO_NUMBERS.getMessage());
            }

            throw new SQLServerException();
        } catch (JsonSyntaxException ignored) {
            throwErrorByName(data);

            WrongParameter parameter = WrongParameter.valueOf(WrongParameter.class, data);
            throw new WrongParameterException(parameter.getMessage());
        }
    }

    /**
     * Throws error by name
     * @param name name error.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws RentException if rent is cancel or finish.
     */
    private void throwErrorByName(@NotNull String name)
            throws SQLServerException, RentException, NoNumberException, NoBalanceException {
        if (name.contains("SQL")) {
            throw new SQLServerException();
        } else if (name.contains("NO")) {
            if (name.equalsIgnoreCase("no_balance")) {
                throw new NoBalanceException();
            }

            StateErrorRent stateErrorRent = StateErrorRent.valueOf(StateErrorRent.class, name);

            if (stateErrorRent == StateErrorRent.NO_NUMBERS) {
                throw new NoNumberException(stateErrorRent.getMessage());
            }

            throw new RentException(stateErrorRent.getMessage());
        }
    }

    /**
     * Throws the error by type.
     * @param stateErrorRent error type.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws RentException if rent is cancel or finish.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    private void throwStateErrorRent(@NotNull StateErrorRent stateErrorRent)
            throws RentException, SQLServerException, NoBalanceException, NoNumberException {
        switch (stateErrorRent) {
            case ALREADY_FINISH: throw new RentException(StateErrorRent.ALREADY_FINISH.getMessage());
            case ALREADY_CANCEL: throw new RentException(StateErrorRent.ALREADY_CANCEL.getMessage());
            case NO_ID_RENT: throw new RentException(StateErrorRent.NO_ID_RENT.getMessage());
            case INCORECT_STATUS:throw new RentException(StateErrorRent.INCORECT_STATUS.getMessage());
            case CANT_CANCEL: throw new TimeOutRentException();
            case INVALID_PHONE: throw new RentException(StateErrorRent.INVALID_PHONE.getMessage());
            case NO_NUMBERS: throw new NoNumberException(StateErrorRent.NO_NUMBERS.getMessage());
            case SQL_ERROR: throw new SQLServerException();
            case ACCOUNT_INACTIVE: throw new RentException(StateErrorRent.ACCOUNT_INACTIVE.getMessage());
            default: throw new NoBalanceException("Нет денег на счету.");
        }
    }

    /**
     * Returns data by url.
     * @param url url address.
     * @return data.
     * @throws IOException if an I/O exception occurs.
     */
    private String getDataByUrl(@NotNull URL url, @NotNull String method) throws IOException {
        String data;
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod(method);

        try (BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
            StringBuilder response = new StringBuilder();

            while ((data = r.readLine()) != null) {
                response.append(data);
            }

            data = response.toString();
        }
        return data;
    }

    private void validateData(@NotNull String data)
            throws BannedException, SQLServerException, WrongParameterException, NoBalanceException, RentException, NoNumberException {
        if (!data.contains("ACCESS")) {
            if (data.contains("BAD")) {
                WrongParameter parameter = WrongParameter.valueOf(WrongParameter.class, data);
                throw new WrongParameterException(parameter.getMessage());
            } else if (data.equalsIgnoreCase("banned")) {
                String date = data.split(":")[1];
                throw new BannedException("Акаунт забанен на " + date);
            } else if (data.contains("SQL")) {
                throw new SQLServerException("Ошибка SQL-сервера.");
            }
        }

        throwErrorByName(data);

    }

    /**
     * Builds the http query string.
     * @param params url params.
     * @return http query string.
     */
    private String buildHttpUrl(@NotNull Map<String, String> params) {
        StringBuilder urlParams = new StringBuilder();

        for (String param : params.keySet()) {
            urlParams.append(param)
                    .append("=")
                    .append(params.get(param))
                    .append("&");
        }

        return urlParams.replace(urlParams.lastIndexOf("&"), urlParams.length(), "").toString();
    }
}
