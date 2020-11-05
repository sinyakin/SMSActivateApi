package com.sms_activate;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sms_activate.activation.AccessStatus;
import com.sms_activate.activation.State;
import com.sms_activate.activation.Status;
import com.sms_activate.error.BannedException;
import com.sms_activate.error.SQLServerException;
import com.sms_activate.error.WrongParameter;
import com.sms_activate.error.WrongParameterException;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO:
 *  QIWI request
 *  Refactoring with exception
 *  Add rent methods
 */
public final class SMSActivateApi {
    private final String BASE_URL = "https://sms-activate.ru/stubs/handler_api.php?";
    private String apiKey;
    private final Pattern digitPattern = Pattern.compile("\\d.*");
    private final Gson gson = new Gson();

    /**
     * Constructor API sms-activate with API key.
     * @param apiKey API key (not be null).
     */
    public SMSActivateApi(@NotNull String apiKey) {
        this.apiKey = apiKey;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.setLength(10);
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
    public @NotNull String getApiKey() {
        return apiKey;
    }

    /**
     * Returns the current account balance.
     * @return current account balance.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if parameter is incorrect.
     */
    public float getBalance() throws IOException, WrongParameterException {
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

        return Float.parseFloat(matcher.group());
    }

    /**
     * Returns the current account balance plus cashBack.
     * @return current account balance plus cashBack.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if parameter is incorrect.
     */
    public float getBalanceAndCashBack() throws IOException, WrongParameterException {
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

        return Float.parseFloat(matcher.group());
    }

    /**
     * Returns a list counts of available services.
     * @return list counts of available services.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if parameter is incorrect.
     */
    public @NotNull List<ServiceForward> getNumbersStatus() throws IOException, WrongParameterException {
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
    public @NotNull List<ServiceForward> getNumbersStatus(int countryNumber, @NotNull String operator) throws IOException, WrongParameterException {
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
     * @return object phone
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     */
    public @NotNull Phone getNumber(
            @NotNull String service,
            @NotNull String ref,
            int countryNumber
    ) throws IOException, WrongParameterException, BannedException, SQLServerException {
        return getNumber(service, ref, countryNumber, "", "", false);
    }

    /**
     * Returns the phone number by service, ref, countryNumber, phoneException, operator, forward
     * @param service service short name.
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
            @NotNull String service,
            @NotNull String ref,
            int countryNumber,
            @NotNull String phoneException,
            @NotNull String operator,
            boolean forward
    ) throws IOException, WrongParameterException, BannedException, SQLServerException {

        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>() {{
            put("api_key", apiKey);
            put("action", action);
            put("ref", ref);
            put("service", service);
            put("country", countryNumber + "");
            put("phoneException", phoneException);
            put("operator", operator);
            put("forward", (forward ? 1 : 0) + "");
        }});

        String data = getDataByUrl(new URL(url), "POST");
        if (!data.contains("ACCESS")) {
            if (data.contains("BAD")) {
                WrongParameter parameter = WrongParameter.valueOf(WrongParameter.class, data);
                throw new WrongParameterException(parameter.getMessage());
            } else if (data.equalsIgnoreCase("banned")) {
                String date = ":".split(data)[1];
                throw new BannedException("Акаунт забанен на " + date);
            } else if (data.contains("SQL")) {
                throw new SQLServerException("Ошибка SQL-сервера.");
            }
        }

        String[] parts = data.split(":");

        return new Phone(
            parts[2],
            Integer.parseInt(parts[1]),
            forward
        );
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
    public List<Phone> getMultiServiceNumber(
            @NotNull String multiService,
            @NotNull String ref,
            int countryCode,
            @NotNull String multiForward,
            @NotNull String operator
    ) throws IOException, WrongParameterException, BannedException, SQLServerException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String trimMultiForward = multiForward.trim();
        String trimMultiService = multiService.trim();

        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("multiService", trimMultiService);
            put("countryCode", countryCode + "");
            put("multiForward", trimMultiForward);
            put("operator", operator.trim());
        }});

        String data = getDataByUrl(new URL(url), "POST");

        try {
            List<Map<String, Object>> phoneMapList = gson.fromJson(data, List.class);
            List<Phone> phoneList = new ArrayList<>();
            int indexForwardPhoneNumber = Arrays.asList(multiForward.split(",")).indexOf("1"); //index phone where need forwarding

            for (int i = 0; i < phoneMapList.size(); i++) {
                Map<String, Object> phoneMap = phoneMapList.get(i);
                String activation = phoneMap.get("activation").toString();

                phoneList.add(new Phone(
                    (String)phoneMap.get("phone"),
                    activation,
                    i == indexForwardPhoneNumber
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
     * @param id id operation (not be null).
     * @param status value to establish (not be null).
     * @return access activation
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws SQLServerException if error happened on SQL-server.
     */
    public AccessStatus setStatus(
            @NotNull String id,
            @NotNull Status status
    ) throws IOException, SQLServerException, WrongParameterException {
        return setStatus(id, status, false);
    }

    /**
     * Sets the status activation.
     * @param id id operation (not be null).
     * @param status value to establish (not be null).
     * @param forward number is forwarding.
     * @return access activation.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws SQLServerException if error happened on SQL-server.
     */
    public AccessStatus setStatus(
            @NotNull String id,
            @NotNull Status status,
            boolean forward
    ) throws IOException, SQLServerException, WrongParameterException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("status", status.getId() + "");
            put("id", id);
            put("forward", !forward ? "" : "1");
        }});

        String data = getDataByUrl(new URL(url), "POST");

        try {
            return AccessStatus.valueOf(AccessStatus.class, data);
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
     * Returns the state activation.
     * @param id id activation (not be null).
     * @return state activation.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws SQLServerException if error happened on SQL-server.
     */
    public State getStatus(@NotNull String id) throws IOException, WrongParameterException, SQLServerException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("id", id);
        }});

        String data = getDataByUrl(new URL(url), "GET");

        try {
            return State.valueOf(State.class, data);
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
    public @NotNull String getFullSms(@NotNull String id) throws IOException, SQLServerException, WrongParameterException {
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
                return State.valueOf(State.class, data).getMessage();
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
     * @param service
     * @param countryNumber
     * @return
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws SQLServerException if error happened on SQL-server.
     */
    public List<Country> getPrices(@NotNull String service, int countryNumber) throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);

            if (!service.isEmpty()) {
                put("service", service);
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
                        value.get("cost")
                    ));
                });

                countryList.add(new Country(
                    new CountryInformation(Integer.parseInt(countryCode)),
                    serviceCostList
                ));
            });

            return countryList;
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * Returns the country information.
     * @return country information.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws SQLServerException if error happened on SQL-server.
     */
    public List<CountryInformation> getCountries() throws IOException, WrongParameterException, SQLServerException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
        }});

        String data = getDataByUrl(new URL(url), "GET");

        try {
            Map<String, CountryInformation> countryInformationMap = gson.fromJson(data, Map.class);
            return new ArrayList<>(countryInformationMap.values());
        } catch (JsonSyntaxException ignored) {
            if (data.contains("SQL")) {
                throw new SQLServerException();
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
    public QiwiResponse getQiwiRequisites() throws IOException, SQLServerException, WrongParameterException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
        }});
        String data = getDataByUrl(new URL(url), "GET");

        try {

            return null;
        } catch (JsonSyntaxException ignored) {
            if (data.contains("SQL")) {
                throw new SQLServerException();
            }

            WrongParameter parameter = WrongParameter.valueOf(data);
            throw new WrongParameterException(parameter.getMessage());
        }
    }

    public Phone getAdditionalService(int parentId) throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("parentId", parentId + "");
        }});

        String data = getDataByUrl(new URL(url), "POST");
        String[] parts = data.split(":");

        return new Phone(parts[2], parts[1], false);
    }

    /**
     *
     * @param time
     * @param operator
     * @param country
     * @return
     * @throws IOException
     */
    public String getRentServicesAndCountries(
            @NotNull String time,
            @NotNull String operator,
            @NotNull String country
    ) throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("time", time);
            put("operator", operator);
            put("country", country);
        }});

        return getDataByUrl(new URL(url), "POST");
    }

    /**
     *
     * @param service
     * @param time
     * @param operator
     * @param country
     * @param urlWebhook
     * @return
     * @throws IOException
     */
    public String getRentNumber(
            @NotNull String service,
            @NotNull String time,
            @NotNull String operator,
            @NotNull String country,
            @NotNull String urlWebhook
    ) throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("service", service);
            put("time", time);
            put("operator", operator);
            put("country", country);
            put("url", urlWebhook);
        }});

        return getDataByUrl(new URL(url), "POST");
    }

    /**
     *
     * @param id
     * @return
     * @throws IOException
     */
    public String getRentStatus(@NotNull String id) throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("id", id);
        }});

        return getDataByUrl(new URL(url), "POST");
    }

    /**
     *
     * @param id
     * @param status
     * @return
     * @throws IOException
     */
    public String setRentStatus(String id, String status) throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("id", id);
            put("status", status);
        }});

        return getDataByUrl(new URL(url), "POST");
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public String getRentList() throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
        }});

        return getDataByUrl(new URL(url), "GET");
    }
    
    /**
     * Returns data by url.
     * @param url url address.
     * @return data.
     * @throws IOException if an I/O exception occurs.
     */
    private String getDataByUrl(@NotNull URL url, @NotNull String method) throws IOException {
        String data = "";
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
