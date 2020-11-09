package com.sms_activate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sms_activate.error.BannedException;
import com.sms_activate.error.BaseSMSActivateException;
import com.sms_activate.error.NoBalanceException;
import com.sms_activate.error.NoNumberException;
import com.sms_activate.error.SQLServerException;
import com.sms_activate.error.WrongParameterException;
import com.sms_activate.phone.Phone;
import com.sms_activate.phone.PhoneRent;
import com.sms_activate.activation.AccessStatusActivation;
import com.sms_activate.activation.StateActivation;
import com.sms_activate.activation.StatusActivation;
import com.sms_activate.country.Country;
import com.sms_activate.country.CountryInformation;
import com.sms_activate.error.rent.RentException;
import com.sms_activate.qiwi.QiwiResponse;
import com.sms_activate.qiwi.QiwiStatus;
import com.sms_activate.error.rent.StateErrorRent;
import com.sms_activate.rent.Rent;
import com.sms_activate.rent.StatusRent;
import com.sms_activate.rent.StateRent;
import com.sms_activate.rent.StatusRentNumber;
import com.sms_activate.service.Service;
import com.sms_activate.service.ServiceWithCost;
import com.sms_activate.service.ServiceForward;
import com.sms_activate.util.QueryStringBuilder;
import com.sms_activate.util.Validator;
import com.sms_activate.util.WebClient;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO:
 *  condition on gzip
 */
public class SMSActivateApi {
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
    private final String apiKey;

    /**
     * Constructor API sms-activate with API key.
     * @param apiKey API key (not be null).
     */
    public SMSActivateApi(@NotNull String apiKey) {
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
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public BigDecimal getBalance()
            throws IOException, WrongParameterException, NoBalanceException, BannedException, NoNumberException, SQLServerException {
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder("api_key", apiKey);
        queryStringBuilder.append("action", "getBalance");

        String url = BASE_URL + queryStringBuilder.build();
        String data = WebClient.get(url);

        Validator.validateData(data);

        Matcher matcher = digitPattern.matcher(data);
        matcher.find();

        return new BigDecimal(matcher.group());
    }

    /**
     * Returns the current account balance plus cashBack.
     * @return current account balance plus cashBack.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public BigDecimal getBalanceAndCashBack()
            throws IOException, WrongParameterException, NoBalanceException, BannedException, NoNumberException, SQLServerException {
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder("api_key", apiKey);
        queryStringBuilder.append("action", "getBalanceAndCashBack");

        String url = BASE_URL + queryStringBuilder.build();

        String data = WebClient.get(url);
        Validator.validateData(data);
        Matcher matcher = digitPattern.matcher(data);
        matcher.find();

        return new BigDecimal(matcher.group());
    }

    /**
     * Returns a list counts of available services.
     * @return list counts of available services.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public List<ServiceForward> getNumbersStatus()
            throws IOException, WrongParameterException, NoBalanceException, BannedException, SQLServerException, NoNumberException {
        return getNumbersStatus(null, null);
    }

    /**
     * Return a list counts of available services by country and operator.
     * @param countryId id country.
     * @param operator name operator mobile network (not be null).
     * @return list counts of available services by county and operator (not be null).
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public List<ServiceForward> getNumbersStatus(Integer countryId, String operator)
            throws IOException, WrongParameterException, NoBalanceException, BannedException, NoNumberException, SQLServerException {
        String action = new Object() {}.getClass().getEnclosingMethod().getName();
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder("api_key", apiKey);
        queryStringBuilder.append("action", "getNumbersStatus")
                .append("country", countryId)
                .append("operator", operator);

        String url = BASE_URL + queryStringBuilder.build();

        String data = WebClient.get(url);
        Validator.validateData(data);

        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> serviceMap = gson.fromJson(data, type);
        List<ServiceForward> serviceList = new ArrayList<>();

        serviceMap.forEach((key, value) -> {
            String[] partsKey = key.split("_");

            serviceList.add(new ServiceForward(
                partsKey[0],
                Integer.parseInt(value),
                Boolean.parseBoolean(partsKey[1])
            ));
        });

        return serviceList;
    }

    /**
     * Returns the phone by service, ref, countryId.
     * @param service service short name.
     * @param ref referral link.
     * @param countryId number country.
     * @return phone for activation.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public Phone getNumber(
            @NotNull Service service,
            @NotNull String ref,
            int countryId
    ) throws IOException, WrongParameterException, BannedException, SQLServerException, NoBalanceException, NoNumberException {
        return getNumber(service, ref, countryId, null, null, false);
    }

    /**
     * Returns the phone number by service, ref, countryId, phoneException, operator, forward
     * @param service service for activation.
     * @param ref referral link.
     * @param countryId number country.
     * @param phoneException excepted phone number prefix. Specify separated by commas.
     * <pre>{@code   7918,7900111}</pre>
     * @param operator mobile operator. May be specify separated by commas.
     * @param forward is it necessary to request a number with forwarding.
     * @return object phone
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public Phone getNumber(
            @NotNull Service service,
            @NotNull String ref,
            int countryId,
            String phoneException,
            String operator,
            boolean forward
    ) throws IOException, WrongParameterException, BannedException, SQLServerException, NoBalanceException, NoNumberException {

        QueryStringBuilder queryStringBuilder = new QueryStringBuilder("api_key", apiKey);
        queryStringBuilder.append("action", "getNumber")
                .append("ref", ref)
                .append("service", service.getShortName())
                .append("country", countryId)
                .append("phoneException", phoneException)
                .append("operator", operator)
                .append("forward", forward ? "1" : "0");

        String url = BASE_URL + queryStringBuilder.build();

        String data = WebClient.get(url);
        Validator.validateData(data);
        
        String[] parts = data.split(":");

        String number = parts[2];
        int id = (int)Math.round(Double.parseDouble(parts[1]));

        return new Phone(number, id, forward, service);
    }

    /**
     * Returns the list phone by countryId, multiService, ref.<br/>
     * Separator for multiService is commas. <br/>
     * <pre> multiService -> vk,av,go,tg. </pre>
     * @param multiService services for ordering (not be null).
     * @param ref referral link (not be null).
     * @param countryId code country.
     * @return list phone.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public List<Phone> getMultiServiceNumber(
            @NotNull String multiService,
            @NotNull String ref,
            int countryId
    ) throws IOException, WrongParameterException, BannedException, SQLServerException, NoBalanceException, NoNumberException {
        return getMultiServiceNumber(multiService, ref, countryId, null, null);
    }

    /**
     * Returns the list phone by countryId, multiService, ref.<br/>
     * Separator for multiService, multiForward and operator is commas. <br/>
     * <pre>multiService -> vk,av,go,tg<br/>multiForward -> 0,0,1,0; 0,0,0,0 - correct; 0,1,1,0 - incorrect.</pre>
     * @param multiService services for ordering (not be null).
     * @param ref referral link (not be null).
     * @param countryId code country.
     * @param multiForward is it necessary to request a number with forwarding.
     * @param operator mobile operator.
     * @return list phone.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public List<Phone> getMultiServiceNumber(
            @NotNull String multiService,
            @NotNull String ref,
            int countryId,
            String multiForward,
            String operator
    ) throws IOException, WrongParameterException, BannedException, SQLServerException, NoBalanceException, NoNumberException {
        String trimMultiService = multiService.replace(" ", "");

        if (multiForward != null) {
            multiForward = multiForward.replace(" ",  "");
        }
        
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder("api_key", apiKey);
        queryStringBuilder.append("action", "getMultiServiceNumber")
                .append("ref", ref)
                .append("multiService", trimMultiService)
                .append("countryId", countryId)
                .append("multiForward", multiForward)
                .append("operator", operator);
        
        String url = BASE_URL + queryStringBuilder.build();

        String data = WebClient.get(url);
        Validator.validateData(data);

        Type type = new TypeToken<List<Map<String, Object>>>(){}.getType();
        List<Map<String, Object>> phoneMapList = gson.fromJson(data, type);
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
                new Service(serviceName)
            ));
        }

        return phoneList;
    }

    /**
     * Sets the status activation.
     * @param phone phone to set activation status (not be null).
     * @param status value to establish (not be null).
     * @return access activation.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public AccessStatusActivation setStatus(@NotNull Phone phone, @NotNull StatusActivation status)
            throws IOException, SQLServerException, WrongParameterException, NoBalanceException, BannedException, NoNumberException {
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
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public AccessStatusActivation setStatus(
            @NotNull Phone phone, 
            @NotNull StatusActivation status,
            boolean forward
    ) throws IOException, SQLServerException, WrongParameterException, NoBalanceException, BannedException, NoNumberException {
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder("api_key", apiKey);
        queryStringBuilder.append("action", "setStatus")
                .append("status", status.getId())
                .append("id", phone.getId())
                .append("forward", forward ? "1" : "0");
        
        String url = BASE_URL + queryStringBuilder.build();

        String data = WebClient.get(url);

        Validator.validateData(data);

        return AccessStatusActivation.valueOf(AccessStatusActivation.class, data);
    }

    /**
     * Returns the state phone activation.
     * @param phone phone id to get activation state (not be null).
     * @return state activation.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public StateActivation getStatus(@NotNull Phone phone) 
            throws IOException, WrongParameterException, SQLServerException, NoBalanceException, BannedException, NoNumberException {
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder("api_key", apiKey);
        queryStringBuilder.append("action", "getStatus")
                .append("id", phone.getId());
        
        String url = BASE_URL + queryStringBuilder.build();
        String data = WebClient.get(url);

        String name = data;
        String code = null;

        Validator.validateData(data);

        if (data.contains(":")) {
            String[] parts = data.split(":");

            name = parts[0];
            code = parts[1];
        }

        StateActivation state = StateActivation.valueOf(StateActivation.class, name);
        state.setCode(code);

        return state;
    }

    /**
     * Returns the full text sms.
     * @param phone id activation.
     * @return full text sms.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public String getFullSms(@NotNull Phone phone)
            throws IOException, SQLServerException, WrongParameterException, NoBalanceException, BannedException, NoNumberException {
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder("api_key", apiKey);
        queryStringBuilder.append("action", "getFullSms")
                .append("id", phone.getId());
        
        String url = BASE_URL + queryStringBuilder.build();
        String data = WebClient.get(url);

        Validator.validateData(data);

        if (data.contains("FULL")) {
            return data;
        } else {
            return StateActivation.valueOf(StateActivation.class, data).getMessage();
        }
    }

    /**
     * Returns the all actual prices by country.
     * @return price list country.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero..
     */
    @NotNull
    public List<Country> getPrices() throws SQLServerException, NoBalanceException, IOException, BannedException, NoNumberException, WrongParameterException {
        return getPrices(null, null);
    }

    /**
     * Returns the actual prices by country.
     * @param service service for needed price list (default null).
     * <pre>{@code null, null -> all service and all country.}</pre>
     * @param countryId country number (default null).
     * @return price list country.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero..
     */
    @NotNull
    public List<Country> getPrices(Service service, Integer countryId)
            throws IOException, SQLServerException, WrongParameterException, NoBalanceException, BannedException, NoNumberException {
        String shortNameService = service.getShortName();
        
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder("api_key", apiKey);
        queryStringBuilder.append("action", "getPrices")
                .append("service", shortNameService)
                .append("country", countryId);

        String url = BASE_URL + queryStringBuilder.build();
        String data = WebClient.get(url);

        Validator.validateData(data);

        Type type = new TypeToken<Map<String, Map<String, Map<String, Double>>>>(){}.getType();
        Map<String, Map<String, Map<String, Double>>> countryMap = gson.fromJson(data, type);
        List<Country> countryList = new ArrayList<>();

        countryMap.forEach((countryCode, serviceMap) -> {
            List<ServiceWithCost> serviceWithCostList = new ArrayList<>();

            serviceMap.forEach((shortName, value) -> {
                serviceWithCostList.add(new ServiceWithCost(
                    shortName,
                    (int)Math.round(value.get("count")),
                    BigDecimal.valueOf(value.get("cost"))
                ));
            });

            countryList.add(new Country(
                new CountryInformation(Integer.parseInt(countryCode)),
                    serviceWithCostList
            ));
        });

        return countryList;
    }

    /**
     * Returns the country information.
     * @return country information.
     * @throws IOException if an I/O exception occurs.
     * @throws RentException if rent is cancel or finish.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public List<CountryInformation> getCountries()
            throws IOException, WrongParameterException, SQLServerException, NoBalanceException, BannedException, RentException, NoNumberException {
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder(new HashMap<String, Object>(){{
            put("api_key", apiKey);
            put("action", "getCountries");
        }});

        String url = BASE_URL + queryStringBuilder.build();
        String data = WebClient.get(url);

        Validator.validateData(data);

        Type type = new TypeToken<Map<String, Map<String, Object>>>(){}.getType();
        Map<String, Map<String, Object>> countryInformationMap = gson.fromJson(data, type);
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

    }

    /**
     * Returns the qiwi response with data on wallet.
     * @return qiwi response with data on wallet
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public QiwiResponse getQiwiRequisites()
            throws IOException, SQLServerException, WrongParameterException, NoBalanceException, BannedException, NoNumberException {
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder(new HashMap<String, Object>(){{
            put("api_key", apiKey);
            put("action", "getQiwiRequisites");
        }});

        String url = BASE_URL + queryStringBuilder.build();
        String data = WebClient.get(url);

        Validator.validateData(data);

        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> qiwiMap = gson.fromJson(data, type);
        QiwiStatus qiwiStatus = QiwiStatus.valueOf(QiwiStatus.class, qiwiMap.get("status").toUpperCase());

        return new QiwiResponse(qiwiStatus, qiwiMap.get("wallet"), qiwiMap.get("comment"));
    }

    /**
     * Returns the phone for additional service by forwarding.
     * @param phone phone activation.
     * @return phone for additional service by forwarding
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public Phone getAdditionalService(@NotNull Phone phone)
            throws IOException, SQLServerException, BannedException, NoNumberException, NoBalanceException, WrongParameterException {
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder("api_key", apiKey);
        queryStringBuilder.append("action", "getAdditionalService")
                .append("parentId", phone.getId());

        String url = BASE_URL + queryStringBuilder.build();
        String data = WebClient.get(url);
        Validator.validateData(data);

        String[] parts = data.split(":");
        String number = parts[2];
        int id = (int)Math.round(Double.parseDouble(parts[1]));

        return new Phone(number, id, false, phone.getService());
    }

    /**
     * Returns the list current activation.
     * @return list with first 10 activation.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public List<Phone> getCurrentActivationsDataTables()
            throws BaseSMSActivateException, NoNumberException, NoBalanceException, WrongParameterException, BannedException, SQLServerException, IOException {
        return getCurrentActivationsDataTables(0, 10);
    }

    /**
     * Returns the list current activation.
     * @param start (default 0).
     * @param length (default 10).
     * @return returns the list activation.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public List<Phone> getCurrentActivationsDataTables(int start, int length)
            throws IOException, BaseSMSActivateException, NoBalanceException, BannedException, NoNumberException, WrongParameterException, SQLServerException {
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder("api_key", apiKey);
        queryStringBuilder.append("action", "getCurrentActivationsDataTables")
                .append("start", start)
                .append("length", length)
                .append("order", "id")
                .append("orderBy", "asc");

        String url = BASE_URL + queryStringBuilder.build();
        String data = WebClient.get(url);
        Validator.validateData(data);

        Type type = new TypeToken<Map<String, Object>>(){}.getType();

        Map<String, Object> responseMap = gson.fromJson(data, type);
        if (responseMap.get("status").toString().equalsIgnoreCase("fail")) {
            throw new BaseSMSActivateException("No activations.", "Нет активаций.");
        }

        List<Map<String, Object>> currentPhoneActivationList = (List<Map<String, Object>>)responseMap.get("array");
        List<Phone> phoneList = new ArrayList<>();

        for (Map<String, Object> currentPhoneActivationMap : currentPhoneActivationList) {
            int id = (int)Double.parseDouble(currentPhoneActivationMap.get("id").toString());
            boolean forward = Boolean.parseBoolean(currentPhoneActivationMap.get("forward").toString());

            String number = currentPhoneActivationMap.get("phone").toString();
            Service service = new Service(currentPhoneActivationMap.get("service").toString());

            phoneList.add(new Phone(number, id, forward, service));
        }

        return phoneList;
    }
    
    /**
     * Returns the rent object with countries supported rent and accessed services by country.
     * @return rent object with countries supported rent and accessed services by country.
     * @throws IOException if an I/O exception occurs.
     * @throws RentException if rent is cancel or finish.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public Rent getRentServicesAndCountries()
            throws IOException, SQLServerException, BannedException, RentException, WrongParameterException, NoNumberException, NoBalanceException {
        return getRentServicesAndCountries(1, null, 0);
    }

    /**
     * Returns the rent object with countries supported rent and accessed services by country.
     * @param time time rent in hours (default 1).
     * @param operator mobile operator.
     * @param countryId country id (default 0).
     * @return the rent object with countries supported rent and accessed services by country.
     * @throws IOException if an I/O exception occurs.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public Rent getRentServicesAndCountries(
            int time,
            String operator,
            int countryId
    ) throws IOException, SQLServerException, BannedException, NoNumberException, NoBalanceException, WrongParameterException {
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder("api_key", apiKey);
        queryStringBuilder.append("action", "getRentServicesAndCountries")
                .append("county", countryId)
                .append("operator", operator)
                .append("time", time);

        String url = BASE_URL + queryStringBuilder.build();
        String data = WebClient.get(url);

        Validator.validateData(data);

        Type type = new TypeToken<Map<String, Map<String, Object>>>(){}.getType();
        Map<String, Map<String, Object>> rentCountriesServices = gson.fromJson(data, type);

        Map<String, Object> countryMap = rentCountriesServices.get("countries");
        Map<String, Object> operatorMap = rentCountriesServices.get("operators");
        Map<String, Object> servicesMap = rentCountriesServices.get("services");

        List<String> operatorNameList = new ArrayList<>();
        List<Integer> countryIdList = new ArrayList<>();
        List<ServiceWithCost> serviceWithCostList = new ArrayList<>();

        for (Object countryCode : countryMap.values())
            countryIdList.add((int)Math.round(Double.parseDouble(countryCode.toString())));

        for (Object name : operatorMap.values())
            operatorNameList.add(name.toString());

        servicesMap.forEach((shortName, service) -> {
            Map<String, Object> serviceMap = (Map<String, Object>) service;
            int countNumber = (int)Double.parseDouble(serviceMap.get("quant").toString());

            serviceWithCostList.add(new ServiceWithCost(
                    shortName,
                    countNumber,
                    new BigDecimal(serviceMap.get("cost").toString()))
            );
        });

        CountryInformation countryInformation = new CountryInformation(countryId);

        return new Rent(operatorNameList, new Country(countryInformation, serviceWithCostList), countryIdList);
    }

    /**
     * Returns the rent phone.
     * @param service service to which you need to get a number.
     * @return rent phone.
     * @throws IOException if an I/O exception occurs.
     * @throws RentException if rent is cancel or finish.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public PhoneRent getRentNumber(@NotNull Service service)
            throws IOException, SQLServerException, RentException, WrongParameterException, NoBalanceException, NoNumberException, BannedException {
       return getRentNumber(service, 1, null, 0, null);
    }

    /**
     * Returns the rent phone.
     * @param service service to which you need to get a number.
     * @param time time rent (default 1 hour).
     * @param operator mobile operator.
     * @param countryId id country (default 0).
     * @param urlWebhook url for webhook.
     * @return rent phone.
     * @throws IOException if an I/O exception occurs.
     * @throws RentException if rent is cancel or finish.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public PhoneRent getRentNumber(
            @NotNull Service service,
            int time,
            String operator,
            int countryId,
            String urlWebhook
    ) throws IOException, SQLServerException, WrongParameterException, RentException, NoBalanceException, NoNumberException, BannedException {
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder("api_key", apiKey);
        queryStringBuilder.append("action", "getRentNumber")
                .append("time", time)
                .append("country", countryId)
                .append("operator", operator)
                .append("url", urlWebhook)
                .append("service", service.getShortName());

        String url = BASE_URL + queryStringBuilder.build();

        String data = WebClient.get(url);
        Validator.validateData(data);

        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> phoneMap = gson.fromJson(data, type);
        StatusRentNumber statusRent = StatusRentNumber.valueOf(StatusRentNumber.class, phoneMap.get("status").toString().toUpperCase());

        if (statusRent == StatusRentNumber.ERROR) {
            String errorMessage = phoneMap.get("message").toString();
            StateErrorRent stateErrorRent = StateErrorRent.valueOf(StateErrorRent.class, errorMessage);

            Validator.throwStateErrorRent(stateErrorRent);
        }

        phoneMap = (Map<String, Object>) phoneMap.get("phone");

        String number = phoneMap.get("number").toString();
        int id = (int)Math.round(Double.parseDouble(phoneMap.get("id").toString()));
        String endDate = phoneMap.get("endDate").toString();

        return new PhoneRent(number, id, false, service, endDate);
    }

    /**
     * Returns the list sms.
     * @param phone phone received in response when ordering a number.
     * @return list sms.
     * @throws IOException if an I/O exception occurs.
     * @throws RentException if rent is cancel or finish.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public List<Sms> getRentStatus(@NotNull Phone phone) throws IOException, SQLServerException, WrongParameterException, RentException, NoBalanceException, NoNumberException, BannedException {
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder("api_key", apiKey);
        queryStringBuilder.append("action", "getRentStatus").
                append("id", phone.getId());

        String url = BASE_URL + queryStringBuilder.build();

        String data = WebClient.get(url);
        Validator.validateData(data);

        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> responseMap = gson.fromJson(data, type);
        StateRent stateRent = StateRent.valueOf(StateRent.class, responseMap.get("status").toString());

        if (stateRent == StateRent.ERROR) {
            StateErrorRent stateErrorRent = StateErrorRent.valueOf(StateErrorRent.class, responseMap.get("message").toString());
            Validator.throwStateErrorRent(stateErrorRent);
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
     * @param phone phone for set status rent.
     * @param status status rent.
     * @return state rent.
     * @throws IOException if an I/O exception occurs.
     * @throws RentException if rent is cancel or finish.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public StateRent setRentStatus(@NotNull Phone phone, @NotNull StatusRent status)
            throws IOException, SQLServerException, WrongParameterException, RentException, NoBalanceException, NoNumberException, BannedException {
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder("api_key", apiKey);
        queryStringBuilder.append("action", "setRentStatus")
                .append("id", phone.getId())
                .append("status", status.getId());

        String url = BASE_URL + queryStringBuilder.build();
        String data = WebClient.get(url);
        Validator.validateData(data);

        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> stateMap = gson.fromJson(data, type);
        StateRent stateRent = StateRent.valueOf(StateRent.class, stateMap.get("status").toUpperCase());

        if (stateRent == StateRent.ERROR) {
            StateErrorRent stateErrorRent = StateErrorRent.valueOf(StateErrorRent.class, stateMap.get("message"));
            Validator.throwStateErrorRent(stateErrorRent);
        }

        return StateRent.SUCCESS;
    }

    /**
     * Returns the list rent phones.
     * @return list rent phones
     * @throws IOException if an I/O exception occurs.
     * @throws RentException if rent is cancel or finish.
     * @throws WrongParameterException if one of parameters is incorrect.
     * @throws BannedException if account has been banned.
     * @throws SQLServerException if error happened on SQL-server.
     * @throws NoBalanceException if no numbers.
     * @throws NoNumberException if in account balance is zero.
     */
    @NotNull
    public List<Phone> getRentList()
            throws IOException, SQLServerException, WrongParameterException, RentException, NoBalanceException, NoNumberException, BannedException {
        QueryStringBuilder queryStringBuilder = new QueryStringBuilder(new HashMap<String, Object>(){{
            put("api_key", apiKey);
            put("action", "getRentList");
        }});

        String url = BASE_URL + queryStringBuilder.build();

        String data = WebClient.get(url);
        Validator.validateData(data);

        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> responseMap = gson.fromJson(data, type);
        StateRent stateRent = StateRent.valueOf(StateRent.class, responseMap.get("status").toString().toUpperCase());

        if (stateRent != StateRent.SUCCESS) {
            StateErrorRent stateErrorRent = StateErrorRent.valueOf(StateErrorRent.class,
                    responseMap.get("message").toString().toUpperCase());
            Validator.throwStateErrorRent(stateErrorRent);
        }

        Map<String, Map<String, Object>> valuesMap = (Map<String, Map<String, Object>>)responseMap.get("values");
        List<Phone> phoneList = new ArrayList<>();

        for (Map<String, Object> phoneMap : valuesMap.values()) {
            String number = phoneMap.get("phone").toString();
            int id = (int)Math.round(Double.parseDouble(phoneMap.get("id").toString()));

            phoneList.add(new Phone(number, id, false, null));
        }

        return phoneList;
    }
}
