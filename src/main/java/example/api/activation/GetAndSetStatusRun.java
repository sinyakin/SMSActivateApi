package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.AccessStatusActivation;
import com.sms_activate.activation.StateActivationResponse;
import com.sms_activate.activation.StatusActivationRequest;
import com.sms_activate.error.BannedException;
import com.sms_activate.error.NoBalanceException;
import com.sms_activate.error.NoNumberException;
import com.sms_activate.error.common.BaseSMSActivateException;
import com.sms_activate.error.common.SQLServerException;
import com.sms_activate.error.common.WrongParameterException;
import com.sms_activate.phone.Phone;
import com.sms_activate.service.Service;
import com.sms_activate.service.ServiceWithForward;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *  This example shows how you can most fully interact with API activation.
 */
public class GetAndSetStatusRun {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      List<ServiceWithForward> serviceList = smsActivateApi.getNumbersStatus(/*0, "mts"*/);

      serviceList.forEach(GetAndSetStatusRun::printInfoAboutService);
    } catch (WrongParameterException | SQLServerException e) {
      System.out.println(e.getEnglishMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void printInfoAboutService(@NotNull Service service) {
    System.out.println("Service: " + service.getShortName());

    if (service.getCountNumber() != 0) {
      System.out.println("Count number: " + service.getCountNumber());
    }
    System.out.println("==========================================");
  }
}
