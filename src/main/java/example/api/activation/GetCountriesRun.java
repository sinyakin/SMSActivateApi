package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.country.SMSActivateGetCountriesResponse;
import com.sms_activate.activation.country.SMSActivateGetCountryResponse;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import org.jetbrains.annotations.NotNull;

public class GetCountriesRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      SMSActivateGetCountriesResponse smsActivateGetCountriesResponse = smsActivateApi.getCountries();

      // output information about countries
      smsActivateGetCountriesResponse.getSMSActivateGetCountryResponseList().forEach(GetCountriesRun::printInfo);

      // 0 - Russia
      printInfo(smsActivateGetCountriesResponse.get(0));
    } catch (SMSActivateWrongParameterException e) {
      System.out.println(e.getWrongParameter());
    } catch (SMSActivateBaseException e) {
      System.out.println(e.getTypeError());
    }
  }

  private static void printInfo(@NotNull SMSActivateGetCountryResponse smsActivateGetCountryResponse) {
    System.out.println("Id: " + smsActivateGetCountryResponse.getId());
    System.out.println("Russian name: " + smsActivateGetCountryResponse.getRussianName());
    System.out.println("English name: " + smsActivateGetCountryResponse.getEnglishName());
    System.out.println("Chinese name: " + smsActivateGetCountryResponse.getChineseName());
    System.out.println("====================================================");
  }
}
