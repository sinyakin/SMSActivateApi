package example.api.rent;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.rent.SMSActivateGetRentServicesAndCountriesResponse;

import java.util.HashSet;

public class GetRentServicesAndCountriesRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      // request services and countries where rent is supported
      SMSActivateGetRentServicesAndCountriesResponse smsActivateGetRentServicesAndCountriesResponse = smsActivateApi.getRentServicesAndCountries(
        0, // 0 - Russia
        // operators
        new HashSet<String>() {{
          add("mts");
          add("tele2");
        }},
        SMSActivateApi.MINIMAL_RENT_TIME
      );

      System.out.println("Services: ");
      smsActivateGetRentServicesAndCountriesResponse.getAllServices().forEach(smsActivateRentService -> {
        System.out.println(">> Short name service: " + smsActivateRentService.getName());
        System.out.println(">> Count numbers: " + smsActivateRentService.getCount());
        System.out.println(">> Cost: " + smsActivateRentService.getCost());
        System.out.println("====================================");
      });

      System.out.println();
      System.out.println();

      System.out.println("Operators: ");
      smsActivateGetRentServicesAndCountriesResponse.getOperatorNameSet()
        .forEach(x -> System.out.println(">> name: " + x));

      System.out.println();
      System.out.println();

      System.out.println("Countries supported rent: ");
      smsActivateGetRentServicesAndCountriesResponse.getCountryIdSet()
        .forEach(x -> System.out.println(">> id: " + x));
    } catch (SMSActivateWrongParameterException e) {
      if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_ACTION) {
        System.out.println("Contact support.");
      }
      if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_KEY) {
        System.out.println("Your api-key is incorrect.");
      } else {
        // todo check other wrong parameter
        System.out.println(e.getMessage() + "  " + e.getMessage());
      }
    } catch (SMSActivateBaseException e) {
      // todo check type error
      System.out.println(e.getTypeError() + "  " + e.getMessage());
    }
  }
}
