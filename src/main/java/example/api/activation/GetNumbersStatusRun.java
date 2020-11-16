package example.api.activation;
import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.common.SQLServerException;
import com.sms_activate.error.common.WrongParameterException;
import com.sms_activate.service.ServiceWithForward;

import java.io.IOException;
import java.util.List;

  public class GetNumbersStatusRun {
  public static void main(String[] args) {
    try {
      int countryId = 0; // see more https://sms-activate.ru/ru/api2
      String operator = "mts"; // see more https://sms-activate.ru/ru/api2

      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      System.out.println("API_KEY: " + smsActivateApi.getApiKey());
      System.out.println("REFERRAL_LINK: " + smsActivateApi.getRef());

      List<ServiceWithForward> serviceWithForwardList = smsActivateApi.getNumbersStatus(/*countryId, operator*/);

      for (ServiceWithForward serviceWithForward : serviceWithForwardList) {
        System.out.println("> short name: " + serviceWithForward.getShortName());
        System.out.println(">> count number: " + serviceWithForward.getCountNumber());

        if (serviceWithForward.isForward()) {
          System.out.println(">> forward supported");
        } else {
          System.out.println(">> forward not supported");
        }

        System.out.println("----------------------------------------------------");
      }
    } catch (WrongParameterException | SQLServerException e) {
      System.out.println(e.getEnglishMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
