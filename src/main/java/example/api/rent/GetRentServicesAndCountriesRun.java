package example.api.rent;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.country.ServiceByCountry;
import com.sms_activate.error.common.SQLServerException;
import com.sms_activate.error.common.WrongParameterException;
import com.sms_activate.rent.Rent;

import java.io.IOException;

public class GetRentServicesAndCountriesRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf", "REFERRAL_LINK");
      Rent rent = smsActivateApi.getRentServicesAndCountries(/*0, "mts", 1*/);

      ServiceByCountry serviceByCountry = rent.getServiceByCountry();

      System.out.println("Country Id: " + serviceByCountry.getCountryInformation().getId());
      System.out.println("=======================================");
      System.out.println();

      System.out.println("Information about the services taking into account the operator: ");
      serviceByCountry.getServiceCostList().forEach(serviceWithCost -> {
        System.out.println("ShortName: " + serviceWithCost.getShortName());
        System.out.println("Count number: " + serviceWithCost.getCountNumber());
        System.out.println("Cost: " + serviceWithCost.getCost());
        System.out.println("=======================================");
      });

      System.out.println();
      System.out.println("Operators: ");
      rent.getOperatorNameList().forEach(operator -> {
        System.out.println(operator);
        System.out.println("=======================================");
      });
    } catch (WrongParameterException | SQLServerException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
