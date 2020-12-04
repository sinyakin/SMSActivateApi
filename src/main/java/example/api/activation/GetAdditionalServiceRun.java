package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.respone.activation.SMSActivateActivation;
import com.sms_activate.error.SMSActivateBannedException;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.base.SMSActivateBaseTypeError;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.respone.activation.set_status.SMSActivateSetStatusRequest;

public class GetAdditionalServiceRun {
  public static void main(String[] args) {
    try {
      final int REFERRAL_IDENTIFIER = 0;
      // create SMSActivateApi object for requests
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      smsActivateApi.setRef(REFERRAL_IDENTIFIER);

      // request activation
      SMSActivateActivation parentActivation = smsActivateApi.getNumber(0, "av", true);

      // print info
      System.out.println(parentActivation.getId());
      System.out.println(parentActivation.getNumber());


      smsActivateApi.setStatus(parentActivation.getId(), SMSActivateSetStatusRequest.SEND_READY_NUMBER);
      // check: https://sms-activate.ru/ru/getNumber

      // request new activation for additional service
      SMSActivateActivation childActivation = smsActivateApi.getAdditionalService(parentActivation.getId(), "ym");

      // print info about additional activation
      System.out.println(childActivation.getId());
      System.out.println(childActivation.getNumber());
    } catch (SMSActivateWrongParameterException e) {
      if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_ACTION) {
        System.out.println("Contact support.");
      } if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_KEY) {
        System.out.println("Your api-key is incorrect.");
      } else {
        // todo check other wrong parameter
        System.out.println(e.getMessage() + "  " + e.getMessage());
      }
    } catch (SMSActivateBannedException e) {
      System.out.println("Your account has been banned wait " + e.getEndDate());
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
