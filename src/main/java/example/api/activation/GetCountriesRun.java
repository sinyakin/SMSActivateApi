package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.respone.activation.SMSActivateGetCountriesResponse;
import com.sms_activate.respone.activation.extra.SMSActivateCountryInfo;
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
      smsActivateGetCountriesResponse.getSMSActivateGetCountryInfoList().forEach(GetCountriesRun::printInfo);
    } catch (SMSActivateWrongParameterException e) {
      System.out.println(e.getWrongParameter());
    } catch (SMSActivateBaseException e) {
      System.out.println(e.getTypeError());
    }
  }

  private static void printInfo(@NotNull SMSActivateCountryInfo smsActivateCountryInfo) {
    System.out.println("Country id: " + smsActivateCountryInfo.getId());
    System.out.println("Russian name: " + smsActivateCountryInfo.getRussianName());
    System.out.println("English name: " + smsActivateCountryInfo.getEnglishName());
    System.out.println("Chinese name: " + smsActivateCountryInfo.getChineseName());
    System.out.println("Support multi-service: " + (smsActivateCountryInfo.isSupportMultiService() ? "yes" : "no"));
    System.out.println("Support retry: " + (smsActivateCountryInfo.isSupportRetry() ? "yes" : "no"));
    System.out.println("Visible in site: " + (smsActivateCountryInfo.isVisible() ? "yes" : "no"));
    System.out.println("Support rent: " + (smsActivateCountryInfo.isSupportRent() ? "yes" : "no"));
    System.out.println("====================================================");
  }
}
