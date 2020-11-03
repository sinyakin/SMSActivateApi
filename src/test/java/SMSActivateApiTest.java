import com.google.gson.Gson;
import com.sms_activate.SMSActivateApi;

import com.sms_activate.Service;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class SMSActivateApiTest {
    private SMSActivateApi smsActivateApi;

    @Before
    public void setUp() {
        smsActivateApi = new SMSActivateApi("");
    }

    @Test
    public void getNumbersStatus_Test() {
        try {
            List<Service> serviceList = smsActivateApi.getNumbersStatus();

            URL url = new URL("https://sms-activate.ru/stubs/handler_api.php?country=&api_key=695Aee8A191cbc011c16745bdbA36822&action=getNumbersStatus&operator=");

            String data = "";
            URLConnection urlConnection = url.openConnection();

            try (BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                StringBuilder response = new StringBuilder();

                while ((data = r.readLine()) != null) {
                    response.append(data);
                }

                data = response.toString();
            }
            Gson gson = new Gson();
            Map m = gson.fromJson(data, Map.class);

            Assert.assertThat(m.keySet().size(), CoreMatchers.is(serviceList.size()));
        } catch (Exception ignored) {

        }
    }
}
