package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateActivation;
import com.sms_activate.error.base.SMSActivateBaseException;

import java.util.HashSet;

public class GetNumberRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      SMSActivateActivation smsActivateActivation = smsActivateApi.getNumber(
          "vk",
          0/*,
          new HashSet<String>(){{
            add("7918");
          }},
          new HashSet<String>(){{
            add("mts");
            add("tele2");
          }},
          false*/);
      System.out.println(smsActivateActivation.getId());
      System.out.println(smsActivateActivation.getNumber());
      // check: https://sms-activate.ru/ru/getNumber
    } catch (SMSActivateBaseException e) {
      System.out.println(e.getTypeError());
      System.out.println(e.getMessage());
    }
  }
}
