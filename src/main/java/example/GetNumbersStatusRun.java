package example;
import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.SQLServerException;
import com.sms_activate.error.WrongParameterException;
import com.sms_activate.service.ServiceWithForward;

import java.io.IOException;
import java.util.List;


public class GetNumbersStatusRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("65942e7978ce8d2fc9f31bAffd325160", "937725");
      List<ServiceWithForward> serviceWithForwardList = smsActivateApi.getNumbersStatus();

      for (ServiceWithForward serviceWithForward : serviceWithForwardList) {
        System.out.println("short name: " + serviceWithForward.getShortName());
        System.out.println("count number: " + serviceWithForward.getCountNumber());
      }
    } catch (WrongParameterException | SQLServerException e) {
      System.out.println(e.getEnglishMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
