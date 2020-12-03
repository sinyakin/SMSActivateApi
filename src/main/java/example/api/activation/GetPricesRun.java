package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateGetPricesResponse;
import com.sms_activate.activation.extra.SMSActivateGetPriceInfo;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.util.Set;

public class GetPricesRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      SMSActivateGetPricesResponse smsActivateGetPricesResponse = smsActivateApi.getPricesByCountryIdAndServiceShortName(
        0, "vk"
      );

      // getAllPrices - returns full price list.

      SMSActivateGetPriceInfo vk = smsActivateGetPricesResponse.getPriceInfo(0, "vk");
      //print info about vk
      System.out.println(">>> Cost: " + vk.getCost());
      System.out.println(">>> count phone numbers: " + vk.getCount());

      // output all services
      for (Integer countryId : smsActivateGetPricesResponse.getCountryIdSet()) {
        Set<String> servicesByCountryId = smsActivateGetPricesResponse.getServicesByCountryId(countryId);

        for (String shortName : servicesByCountryId) {
          SMSActivateGetPriceInfo priceInfo = smsActivateGetPricesResponse.getPriceInfo(countryId, shortName);

          System.out.println(">>> Service shortname: " + shortName);
          System.out.println(">>> Cost: " + priceInfo.getCost());
          System.out.println(">>> count phone numbers: " + priceInfo.getCount());
          System.out.println("==========================================================================");
        }
      }
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
