package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.get_prices.SMSActivateGetPriceResponse;
import com.sms_activate.activation.get_prices.SMSActivateGetPricesResponse;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.util.Map;

public class GetPricesRun {
  public static void main(String[] args) {
    /*
      example json:
        {
          "CountryId": {
            "ServiceName": {
              "cost": someCost,
              "count": comeCountNumber
             }
            }
           }
     */

    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
      SMSActivateGetPricesResponse smsActivateGetPricesResponse = smsActivateApi.getPrices(
          "vk", 0 // comment this parameter and you get all data.
      );

      SMSActivateGetPriceResponse smsActivateGetPriceResponse = smsActivateGetPricesResponse.get(0, "vk");
      System.out.println(">> Service name: " + smsActivateGetPriceResponse.getName());
      System.out.println(">>> Cost: " + smsActivateGetPriceResponse.getCost());
      System.out.println(">>> Count number: " + smsActivateGetPriceResponse.getCount());

      // output all
      /*smsActivateGetPricesResponse.getSmsActivateGetPriceMap().forEach((countryId, value) -> {
        System.out.println("> Country id: " + countryId);

        value.forEach((serviceName, smsActivateGetPriceResponse) -> {
          System.out.println(">> Service name: " + serviceName);

          System.out.println(">>> Cost: " + smsActivateGetPriceResponse.getCost());
          System.out.println(">>> Count number: " + smsActivateGetPriceResponse.getCount());
        });
        System.out.println("===================================================");
      });*/
    } catch (SMSActivateBaseException e) {
      e.printStackTrace();
    }
  }
}
