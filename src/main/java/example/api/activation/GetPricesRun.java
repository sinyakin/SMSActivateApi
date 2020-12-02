package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateGetPricesResponse;
import com.sms_activate.activation.extra.SMSActivateGetPriceInfo;
import com.sms_activate.error.SMSActivateBannedException;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.base.SMSActivateBaseTypeError;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

public class GetPricesRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
      SMSActivateGetPricesResponse smsActivateGetPricesResponse = smsActivateApi.getPrices(
        //0, "vk"// comment this parameter and you get all data.
      );

      SMSActivateGetPriceInfo smsActivateGetPriceInfo = smsActivateGetPricesResponse.get(0, "vk");
      System.out.println(">>> Cost: " + smsActivateGetPriceInfo.getCost());
      System.out.println(">>> Count number: " + smsActivateGetPriceInfo.getCount());

      // output all
      /*smsActivateGetPricesResponse.getSmsActivateGetPriceMap().forEach((countryId, value) -> {
        System.out.println("> Country id: " + countryId);

        value.forEach((serviceName, smsActivateGetPriceResponse) -> {
          System.out.println(">>> Cost: " + smsActivateGetPriceResponse.getCost());
          System.out.println(">>> Count number: " + smsActivateGetPriceResponse.getCount());
        });
        System.out.println("===================================================");
      });*/
    } catch (SMSActivateWrongParameterException e) {
      if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_ACTION) {
        System.out.println("Contact support.");
      } if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_KEY) {
        System.out.println("Your api-key is incorrect.");
      } else {
        // todo check other wrong parameter
        System.out.println(e.getMessage() + "  " + e.getMessage());
      }
    } catch (SMSActivateBaseException e) {
      // todo check
      System.out.println(e.getTypeError() + "  " + e.getMessage());
    }
  }
}
