package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateGetCountriesResponse;
import com.sms_activate.activation.extra.SMSActivateCountryInfo;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import org.jetbrains.annotations.NotNull;

public class GetCountriesRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      SMSActivateGetCountriesResponse smsActivateGetCountriesResponse = smsActivateApi.getCountries();

      // 0 (id) - Russia
      // id is needed for request when you need to specify a specific country
      printInfo(smsActivateGetCountriesResponse.get(0));

      // print info about all countries
      smsActivateGetCountriesResponse.getSMSActivateGetCountryResponseList().forEach(GetCountriesRun::printInfo);
    } catch (SMSActivateWrongParameterException e) {
      System.out.println(e.getWrongParameter());
    } catch (SMSActivateBaseException e) {
      System.out.println(e.getTypeError());
    }
  }

  private static void printInfo(@NotNull SMSActivateCountryInfo smsActivateCountryInfo) {
    System.out.println("Id: " + smsActivateCountryInfo.getId());
    System.out.println("Russian name: " + smsActivateCountryInfo.getRussianName());
    System.out.println("English name: " + smsActivateCountryInfo.getEnglishName());
    System.out.println("Chinese name: " + smsActivateCountryInfo.getChineseName());
    System.out.println("====================================================");
  }
}
