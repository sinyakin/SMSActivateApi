package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateGetNumbersStatusResponse;
import com.sms_activate.activation.extra.SMSActivateServiceInfo;
import com.sms_activate.error.base.SMSActivateBaseException;
import org.jetbrains.annotations.NotNull;

public class GetNumbersStatusRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
      System.out.println("Your api-key: " + smsActivateApi.getApiKey());

      // request count available numbers
      SMSActivateGetNumbersStatusResponse smsActivateGetNumbersStatusResponse = smsActivateApi.getNumbersStatus();

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
    System.out.println("count phone numberss: " + serviceInfo.getCountPhoneNumber());
    System.out.println("Supported: " + (serviceInfo.isForward() ? "yes" : "no"));
    System.out.println("=============================================");
  }
}
