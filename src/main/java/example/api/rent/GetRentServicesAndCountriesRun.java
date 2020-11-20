package example.api.rent;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.rent.get_rent_services_and_countries.SMSActivateGetRentServicesAndCountriesResponse;

import java.util.HashSet;

public class GetRentServicesAndCountriesRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
      SMSActivateGetRentServicesAndCountriesResponse smsActivateGetRentServicesAndCountriesResponse = smsActivateApi.getRentServicesAndCountries(
          /*0, new HashSet<String>() {{
            add("mts");
            add("tele2");
          }}, 5*/
      );

      System.out.println("Services: ");
      smsActivateGetRentServicesAndCountriesResponse.getAllServices().forEach(x -> {
        System.out.println(">> Name: " + x.getName());
        System.out.println(">> Count numbers: " + x.getCount());
        System.out.println(">> Cost: " + x.getCost());
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
      System.out.println(e.getWrongParameter());
    } catch (SMSActivateBaseException e) {
      System.out.println(e.getTypeError());
    }
  }
}
