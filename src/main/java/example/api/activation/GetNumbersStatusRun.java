package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.numbers_status.SMSActivateGetNumberStatusResponse;
import com.sms_activate.activation.numbers_status.SMSActivateGetNumbersStatusResponse;
import com.sms_activate.error.base.SMSActivateBaseException;
import org.jetbrains.annotations.NotNull;

public class GetNumbersStatusRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      System.out.println("Your api-key: " + smsActivateApi.getApiKey());

      SMSActivateGetNumbersStatusResponse smsActivateGetNumbersStatusResponse = smsActivateApi.getNumbersStatus();
      SMSActivateGetNumberStatusResponse go = smsActivateGetNumbersStatusResponse.get("go");

      printInfo(go);

      smsActivateGetNumbersStatusResponse.getSMSActivateGetNumberStatusResponseList().forEach(GetNumbersStatusRun::printInfo);
    } catch (SMSActivateBaseException e) {
      System.out.println(e.getTypeError());
      System.out.println(e.getMessage());
    }
  }

  private static void printInfo(@NotNull SMSActivateGetNumberStatusResponse smsActivateGetNumberStatusResponse) {
    System.out.println("Name: " + smsActivateGetNumberStatusResponse.getNameService());
    System.out.println("Count numbers: " + smsActivateGetNumberStatusResponse.getCountNumber());
    System.out.println("=============================================");
  }
}