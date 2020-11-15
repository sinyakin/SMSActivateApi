package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.country.Country;
import com.sms_activate.error.common.SQLServerException;
import com.sms_activate.error.common.WrongParameterException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class GetCountriesRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY", "REFERRAL_LINK");
      List<Country> countryList = smsActivateApi.getCountries();

      countryList.forEach(GetCountriesRun::printCountryInfo);
    } catch (WrongParameterException | SQLServerException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void printCountryInfo(@NotNull Country country) {
    System.out.println("Id: " + country.getId());
    System.out.println("EnglishName: " + country.getEnglishName());
    System.out.println("RussianName: " + country.getRussianName());
    System.out.println("ChineseName: " + country.getChineseName());
    System.out.println("==========================================");
  }
}
