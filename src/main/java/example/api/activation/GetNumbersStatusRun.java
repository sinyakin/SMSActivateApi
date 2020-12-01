package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateGetNumbersStatusResponse;
import com.sms_activate.activation.extra.SMSActivateServiceInfo;
import com.sms_activate.error.base.SMSActivateBaseException;
import org.jetbrains.annotations.NotNull;

public class GetNumbersStatusRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      System.out.println("Your api-key: " + smsActivateApi.getApiKey());

      SMSActivateGetNumbersStatusResponse smsActivateGetNumbersStatusResponse = smsActivateApi.getNumbersStatus();
      SMSActivateServiceInfo go = smsActivateGetNumbersStatusResponse.get("go"); // google

      printInfo(go);

      Thread.sleep(1500);

      smsActivateGetNumbersStatusResponse.getSMSActivateGetNumberStatusResponseList().forEach(GetNumbersStatusRun::printInfo);
    } catch (SMSActivateBaseException e) {
      System.out.println(e.getTypeError());
      System.out.println(e.getMessage());
    } catch (InterruptedException ignored) {
    }
  }

  private static void printInfo(@NotNull SMSActivateServiceInfo smsActivateServiceInfo) {
    System.out.println("Name: " + smsActivateServiceInfo.getShortName());
    System.out.println("Count numbers: " + smsActivateServiceInfo.getCountNumber());
    System.out.println("=============================================");
  }
}
