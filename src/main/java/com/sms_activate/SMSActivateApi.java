package com.sms_activate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class SMSActivateApi {
    private final String BASE_URL = "https://sms-activate.ru/stubs/handler_api.php?";
    private String apiKey;

    /**
     * Конструктор API sms-activate с API ключ
     * @param apiKey - API ключ
     */
    public SMSActivateApi(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Устанавливает значение <strong><em>apiKey</em></strong>
     * @param apiKey ключ API
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Возвращает API ключ
     * @return apiKey - API ключ
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Получ
     * @return
     * @throws IOException
     */
    public String getBalance() throws IOException {
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
    public String getBalanceAndCashBack() throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();

        String url = BASE_URL + buildHttpUrl(new HashMap<>(){{
            put("api_key", apiKey);
            put("action", action);
        }});

        return getDataByUrl(new URL(url));
    }

    /**
     *
     * @param country
     * @param operator
     * @return
     * @throws IOException
     */
    public String getNumbersStatus(String country, String operator) throws IOException {
        String action = new Object(){}.getClass().getEnclosingMethod().getName();
        String url = BASE_URL + buildHttpUrl(new HashMap<>() {{
            put("api_key", apiKey);
            put("action", action);
            put("country", country);
            put("operator", operator);
        }});

        return getDataByUrl(new URL(url));
    }

    /**
     *
     * @param service
     * @param ref
     * @param country
     * @return
     * @throws IOException
     */
    public String getNumber(String service, String ref, String country) throws IOException {
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
     *
     * @param id
     * @return
     * @throws IOException
     */
    public String setStatus(String id) throws IOException {
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
     * @return
     * @throws IOException
     */
    public String getStatus(String id) throws IOException {
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
     * @return
     * @throws IOException
     */
    public String getFullSms(String id) throws IOException {
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
    public String getPrices(String service, String country) throws IOException {
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
    public String getRentServicesAndCountries(String time, String operator, String country) throws IOException {
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
    public String getRentNumber(String service, String time, String operator, String country, String urlWebhook) throws IOException {
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
    public String getRentStatus(String id) throws IOException {
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
    public String setRentStatus(String id, String status) throws IOException {
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
    public String getRentList() throws IOException {
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
    public String getCountries() throws IOException {
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
    private String getDataByUrl(URL url) throws IOException {
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
    private String buildHttpUrl(Map<String, String> params) {
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
