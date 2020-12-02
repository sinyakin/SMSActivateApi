package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateActivation;
import com.sms_activate.activation.set_status.SMSActivateSetStatusRequest;
import com.sms_activate.error.base.SMSActivateBaseException;

public class GetAdditionalServiceRun {
  public static void main(String[] args) {
    try {
      // create SMSActivateApi object for
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");

      // request activation
      SMSActivateActivation parentActivation = smsActivateApi.getNumber(0, "av", null, null, true);

      // print info
      System.out.println(parentActivation.getId());
      System.out.println(parentActivation.getNumber());

      /*
        if service is ot then need set status
        smsActivateApi.setStatus(parentActivation.getId(), SMSActivateSetStatusRequest.SEND_READY_NUMBER);
       */
      //smsActivateApi.setStatus(parentActivation.getId(), SMSActivateSetStatusRequest.SEND_READY_NUMBER);

      // check: https://sms-activate.ru/ru/getNumber

      // request new activation for additional service
      SMSActivateActivation childActivation = smsActivateApi.getAdditionalService(parentActivation.getId(), "av");

      // print info
      System.out.println(childActivation.getId());
      System.out.println(childActivation.getNumber());
    } catch (SMSActivateBaseException e) {
      System.out.println(e.getMessage());
    }
  }
}
