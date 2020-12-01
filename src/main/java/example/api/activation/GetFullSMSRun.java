package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateActivation;
import com.sms_activate.activation.SMSActivateGetFullSmsResponse;
import com.sms_activate.activation.extra.SMSActivateStatusNumber;
import com.sms_activate.activation.set_status.SMSActivateSetStatusRequest;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.util.Scanner;

public class GetFullSMSRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      SMSActivateActivation activation = smsActivateApi.getNumber(0, "tg");
      System.out.println("Please register your number " + activation.getNumber() + " with ID " + activation.getId());
      Scanner scanner = new Scanner(System.in);

      while (true) {
        System.out.println("Input ch(check sms)/q(quit)");
        String response = scanner.next();

        if (response.equals("q")) {
          break;
        } else if (response.equals("ch")) {
          SMSActivateGetFullSmsResponse smsActivateGetFullSmsResponse = smsActivateApi.getFullSms(activation.getId());

          if (smsActivateGetFullSmsResponse.getSmsActivateGetFullTypeResponse() == SMSActivateStatusNumber.FULL_SMS) {
            System.out.println(smsActivateGetFullSmsResponse.getText());
            smsActivateApi.setStatus(activation.getId(), SMSActivateSetStatusRequest.FINISH);
            break;
          }
          System.out.println(smsActivateGetFullSmsResponse.getSmsActivateGetFullTypeResponse().getMessage());
          System.out.println("Please register your number " + activation.getNumber() + " with ID " + activation.getId());
        }
      }
    } catch (SMSActivateWrongParameterException e) {
      System.out.println(e.getWrongParameter());
      System.out.println(e.getMessage());
    } catch (SMSActivateBaseException e) {
      System.out.println(e.getTypeError());
      System.out.println(e.getMessage());
    }

  }
}
