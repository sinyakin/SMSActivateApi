package example;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.country.Country;
import com.sms_activate.country.ServiceByCountry;
import com.sms_activate.error.common.SQLServerException;
import com.sms_activate.error.common.WrongParameterException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class GetPricesRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY", "REFERRAL_LINK");
      List<ServiceByCountry> serviceByCountryList = smsActivateApi.getPrices();

      serviceByCountryList.forEach(x -> {
        System.out.println("Country id: " + x.getCountryInformation().getId());

        System.out.println("Services in country: ");

        x.getServiceCostList().forEach(service -> {
          System.out.println("    ShortName: " + service.getShortName());
          System.out.println("    CountNumber: " + service.getCountNumber());
        });
      });
    } catch (WrongParameterException | SQLServerException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
