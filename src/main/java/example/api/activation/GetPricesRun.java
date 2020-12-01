package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateGetPricesResponse;
import com.sms_activate.activation.extra.SMSActivateGetPriceInfo;
import com.sms_activate.error.base.SMSActivateBaseException;

public class GetPricesRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      SMSActivateGetPricesResponse smsActivateGetPricesResponse = smsActivateApi.getPrices(
        0, "vk"// comment this parameter and you get all data.
      );

      SMSActivateGetPriceInfo smsActivateGetPriceInfo = smsActivateGetPricesResponse.get(0, "vk");
      System.out.println(">> Service name: " + smsActivateGetPriceInfo.getShortName());
      System.out.println(">>> Cost: " + smsActivateGetPriceInfo.getCost());
      System.out.println(">>> Count number: " + smsActivateGetPriceInfo.getCount());

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
