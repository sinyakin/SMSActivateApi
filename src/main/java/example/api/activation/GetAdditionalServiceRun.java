package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateActivation;
import com.sms_activate.error.base.SMSActivateBaseException;

public class GetAdditionalServiceRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      SMSActivateActivation parentActivation = smsActivateApi.getNumber(0, "av", null, null, true);
      System.out.println(parentActivation.getId());
      System.out.println(parentActivation.getNumber());
      // check: https://sms-activate.ru/ru/getNumber

      SMSActivateActivation childActivation = smsActivateApi.getAdditionalService(parentActivation.getId(), "av");
      System.out.println(childActivation.getId());
      System.out.println(childActivation.getNumber());
    } catch (SMSActivateBaseException e) {
      System.out.println(e.getMessage());
    }
  }
}
