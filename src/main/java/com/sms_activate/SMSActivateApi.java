package com.sms_activate;

import com.sms_activate.response.ErrorResponse;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO:
 *  Decompose logic getting data
 *  Think about throws exceptions by levels
 */
public final class SMSActivateApi {
    private final String BASE_URL = "https://sms-activate.ru/stubs/handler_api.php?";
    private String apiKey;
    private final Pattern digitPattern = Pattern.compile("\\d.*");
    private final Gson gson = new Gson();

    /**
     * Constructor API sms-activate witch API key.
     * @param apiKey - API key (not be null).
     */
    public SMSActivateApi(@NotNull String apiKey) {
        this.apiKey = apiKey;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.setLength(10);
    }

    /**
     * Sets the value apiKey.
     * @param apiKey - API key (not be null).
     */
    public void setApiKey(@NotNull String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Returns the API key.
     * @return apiKey - API key (not be null).
     */
    public @NotNull String getApiKey() {
        return apiKey;
    }

    /**
     * Returns the current account balance.
     * @return current account balance.
     * @throws IOException - if an I/O exception occurs.
     * @throws RequestException
     */
    public float getBalance() throws IOException, RequestException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
        }});

        String data = getDataByUrl(new URL(url));
        Matcher matcher = digitPattern.matcher(data);

        if (!matcher.find()) {
            throw new RequestException(ErrorResponse.valueOf(ErrorResponse.class, data));
        }

        return Float.parseFloat(matcher.group());
    }

    /**
     * Returns the current account balance plus cashBack.
     * @return current account balance plus cashBack.
     * @throws IOException - if an I/O exception occurs.
     * @throws RequestException
     */
    public float getBalanceAndCashBack() throws IOException, RequestException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();

        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
        }});

        String data = getDataByUrl(new URL(url));
        Matcher matcher = digitPattern.matcher(data);

        if (!matcher.find()) {
            throw new RequestException(ErrorResponse.valueOf(ErrorResponse.class, data));
        }

        return Float.parseFloat(matcher.group());
    }

    /**
     * Returns a list counts of available services.
     * @return list counts of available services.
     * @throws IOException - if an I/O exception occurs.
     */
    public @NotNull List<Service> getNumbersStatus() throws IOException {
        return getNumbersStatus(Integer.MIN_VALUE, "");
    }

    /**
     * Return a list counts of available services by country and operator.
     * @param country - id country
     * @param operator - name operator mobile network
     * @return list counts of available services by county and operator
     * @throws IOException - if an I/O exception occurs.
     */
    public @NotNull List<Service> getNumbersStatus(int country, @NotNull String operator) throws IOException {
        String action = new Object() {}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>() {{
            put("api_key", apiKey);
            put("action", action);
            put("country", country == Integer.MIN_VALUE ? "" : country + "");
            put("operator", operator);
        }});

        String data = getDataByUrl(new URL(url));
        Map<String, String> serviceMap = gson.fromJson(data, HashMap.class);
        List<Service> serviceList = new ArrayList<>();

        for (String key : serviceMap.keySet()) {
            String[] partsKey = key.split("_");

            serviceList.add(new Service(
                "",
                partsKey[0],
                Boolean.parseBoolean(partsKey[1]),
                Integer.parseInt(serviceMap.get(key))
            ));
        }

        return serviceList;
    }

    /**
     *
     * @param service
     * @param ref
     * @param country
     * @return
     * @throws IOException
     */
    public @NotNull String getNumber(
            @NotNull String service,
            @NotNull String ref,
            @NotNull String country
    ) throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("ref", ref);
            put("country", country);
            put("service", service);
        }});

        return getDataByUrl(new URL(url));
    }

    /**
     * Sets status
     * @param id
     * @return
     * @throws IOException
     */
    public @NotNull String setStatus(int id) throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("id", id + "");
        }});

        return getDataByUrl(new URL(url));
    }

    /**
     *
     * @param id
     * @return
     * @throws IOException
     */
    public @NotNull String getStatus(int id) throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("id", id + "");
        }});

        return getDataByUrl(new URL(url));
    }

    /**
     *
     * @param id
     * @return
     * @throws IOException
     */
    public @NotNull String getFullSms(@NotNull String id) throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("id", id);
        }});

        return getDataByUrl(new URL(url));
    }

    /**
     *
     * @param service
     * @param country
     * @return
     * @throws IOException
     */
    public @NotNull String getPrices(
            @NotNull String service,
            @NotNull String country
    ) throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("service", service);
            put("country", country);
        }});

        return getDataByUrl(new URL(url));
    }

    /**
     *
     * @param time
     * @param operator
     * @param country
     * @return
     * @throws IOException
     */
    public @NotNull String getRentServicesAndCountries(
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

        return getDataByUrl(new URL(url));
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
    public @NotNull String getRentNumber(
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

        return getDataByUrl(new URL(url));
    }

    /**
     *
     * @param id
     * @return
     * @throws IOException
     */
    public @NotNull String getRentStatus(@NotNull String id) throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("id", id);
        }});

        return getDataByUrl(new URL(url));
    }

    /**
     *
     * @param id
     * @param status
     * @return
     * @throws IOException
     */
    public @NotNull String setRentStatus(String id, String status) throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
            put("id", id);
            put("status", status);
        }});

        return getDataByUrl(new URL(url));
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public @NotNull String getRentList() throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
        }});

        return getDataByUrl(new URL(url));
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public @NotNull String getCountries() throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
        }});

        return getDataByUrl(new URL(url));
    }
    
    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    private String getDataByUrl(@NotNull URL url) throws IOException {
        String data = "";
        URLConnection urlConnection = url.openConnection();

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
     *
     * @param params
     * @return
     */
    private String buildHttpUrl(@NotNull Map<@NotNull String, @NotNull String> params) {
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