package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.respone.activation.SMSActivateGetFullSmsResponse;
import com.sms_activate.respone.activation.extra.SMSActivateStatusNumber;
import com.sms_activate.respone.activation.set_status.SMSActivateSetStatusRequest;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.base.SMSActivateBaseTypeError;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.util.Scanner;

public class GetFullSMSRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");

      //SMSActivateActivation activation = smsActivateApi.getNumber(0, "vk");
      // the number must be used on the service for which you took it, else SMS will not come to it
      //System.out.println("Please use your activation " + activation.getNumber() + " with ID " + activation.getId());
      //smsActivateApi.setStatus(activation.getId(), SMSActivateSetStatusRequest.SEND_READY_NUMBER);
      Scanner scanner = new Scanner(System.in);

      while (true) {
        System.out.println("Input ch(check sms)/q(quit)");
        String response = scanner.next();

        if (response.equals("q")) {
          break;
        } else if (response.equals("ch")) {
          SMSActivateGetFullSmsResponse smsActivateGetFullSmsResponse = smsActivateApi.getFullSms(353147512);

          // if SMS arrived, the activation status will be FULL_SMS
          if (smsActivateGetFullSmsResponse.getSmsActivateGetFullTypeResponse() == SMSActivateStatusNumber.FULL_SMS) {
            System.out.println(smsActivateGetFullSmsResponse.getText());
            smsActivateApi.setStatus(353136785, SMSActivateSetStatusRequest.FINISH);
            break;
          }
          System.out.println(smsActivateGetFullSmsResponse.getSmsActivateGetFullTypeResponse().getMessage());
          //System.out.println("Please use your activation " + activation.getNumber() + " with ID " + activation.getId());
        }
      }
    }  catch (SMSActivateWrongParameterException e) {
      if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_ACTION) {
        System.out.println("Contact support.");
      } if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_KEY) {
        System.out.println("Your api-key is incorrect.");
      } else {
        // todo check other wrong parameter
        System.out.println(e.getMessage() + "  " + e.getMessage());
      }
    } catch (SMSActivateBaseException e) {
      if (e.getTypeError() == SMSActivateBaseTypeError.NO_BALANCE) {
        System.out.println("Top up balance.");
      }
      if (e.getTypeError() == SMSActivateBaseTypeError.NO_NUMBERS) {
        System.out.println("Send request later....");
      } else {
        // todo check other type error
        System.out.println(e.getTypeError() + "  " + e.getMessage());
      }
    }

  }
}
