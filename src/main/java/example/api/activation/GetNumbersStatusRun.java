package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.response.api_activation.SMSActivateGetNumbersStatusResponse;
import com.sms_activate.response.api_activation.extra.SMSActivateServiceInfo;
import com.sms_activate.error.base.SMSActivateBaseException;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * To find out information about the number of numbers in the service and their price, use the getNumbersStatus method.
 */
public class GetNumbersStatusRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi(System.getenv("API_KEY_SMS_ACTIVATE"));
      System.out.println("Your api-key: " + smsActivateApi.getApiKey());

      // 1. Request to get count available numbers in service.
      SMSActivateGetNumbersStatusResponse smsActivateGetNumbersStatusResponse = smsActivateApi.getNumbersStatusByDefaultSettingFromSite();

      // if you are interested in specific services in a country without a specific operator, then uncomment the line below.
      // smsActivateGetNumbersStatusResponse = smsActivateApi.getNumbersStatus(0, null);

      // if you are interested in services for specific operators without a specific country, then uncomment the line below.
      /*Set<String> operatorSet = new HashSet<>();
      operatorSet.add("mts");
      operatorSet.add("tele2");
      smsActivateGetNumbersStatusResponse = smsActivateApi.getNumbersStatus(null, operatorSet);*/
      /*
        if you need specify service
        you can get it by short name.

        more info about short name service: https://sms-activate.ru/ru/api2#quantity
       */
      SMSActivateServiceInfo go = smsActivateGetNumbersStatusResponse.getSMSActivateServiceInfoByShortName("go"); // google
      printInfo(go);

      //Thread.sleep(1500);

      // print info about all available services
      smsActivateGetNumbersStatusResponse.getAllServiceInfoList().forEach(GetNumbersStatusRun::printInfo);
    } catch (SMSActivateBaseException e) {
      System.out.println(e.getTypeError());
      System.out.println(e.getMessage());
    }
  }

  private static void printInfo(@NotNull SMSActivateServiceInfo serviceInfo) {
    System.out.println("Short name service: " + serviceInfo.getShortName());
    System.out.println("count phone numbers: " + serviceInfo.getCountPhoneNumber());
    System.out.println("Forwarding is available: " + (serviceInfo.isForward() ? "yes" : "no"));
    System.out.println("=============================================");
  }
}
