package example;

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
import java.util.Locale;
import java.util.Scanner;

/**
 *  This example shows how you can most fully interact with API activation.
 */
public class GetAndSetStatusRun {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf", "937725");
      List<ServiceWithForward> serviceList = smsActivateApi.getNumbersStatus();

      serviceList.forEach(GetAndSetStatusRun::printInfoAboutService);

      System.out.print("Input service name: ");
      Service service = new Service(scanner.next());

      Phone phone = smsActivateApi.getNumber(service, 0);
      printInfoAboutPhone(phone);

      System.out.println("Please register number in service " + service.getShortName());

      StateActivationResponse stateActivationResponse = smsActivateApi.getStatus(phone);
      System.out.println(stateActivationResponse.getEnglishMessage());

      while (true) {
        try {
          System.out.println("Input c(cancel)/f(finish)/s(sms code)/q(quit)");
          String response = scanner.next().toLowerCase();
          boolean isQuit = response.equals("q");

          if (isQuit) {
            break;
          }

          switch (response) {
            case "c":
              AccessStatusActivation accessStatusActivation = smsActivateApi.setStatus(phone, StatusActivationRequest.CANCEL);

              if (accessStatusActivation == AccessStatusActivation.CANCEL) {
                System.out.println(accessStatusActivation.getMessage());
                return;
              }
              break;
            case "f":
              accessStatusActivation = smsActivateApi.setStatus(phone, StatusActivationRequest.FINISH);

              if (accessStatusActivation == AccessStatusActivation.ACTIVATION) {
                System.out.println(accessStatusActivation.getMessage());
                return;
              }
              break;
            case "s":
              String smsCode = smsActivateApi.getFullSms(phone);
              System.out.println("smsCode: " + smsCode);
              break;
          }
        } catch (BaseSMSActivateException e) {
          System.out.println(e.getEnglishMessage());
        } finally {
          System.out.println("Current status activation: " + smsActivateApi.getStatus(phone).getEnglishMessage());
        }
      }

      stateActivationResponse = smsActivateApi.getStatus(phone);
      System.out.println(stateActivationResponse.getEnglishMessage());
    } catch (WrongParameterException | NoNumberException | NoBalanceException | SQLServerException | BannedException e) {
      System.out.println(e.getEnglishMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void printInfoAboutPhone(@NotNull Phone phone) {
    System.out.println("Id: " + phone.getId());
    System.out.println("Number: " + phone.getNumber());
    printInfoAboutService(phone.getService());
  }

  private static void printInfoAboutService(@NotNull Service service) {
    System.out.println("Service: " + service.getShortName());

    if (service.getCountNumber() != 0) {
      System.out.println("Count number: " + service.getCountNumber());
    }
    System.out.println("==========================================");
  }
}
