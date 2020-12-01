package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateActivation;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.util.HashSet;

public class GetNumberRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
      SMSActivateActivation smsActivateActivation = smsActivateApi.getNumber(
        0,
          "tn"
          /*new HashSet<String>(){{
            add("mts");
            add("tele2");
          }},
          new HashSet<String>(){{
            add("7918");
          }},
          false*/);
      System.out.println(smsActivateActivation.getId());
      System.out.println(smsActivateActivation.getNumber());
      // check: https://sms-activate.ru/ru/getNumber
    } catch (SMSActivateWrongParameterException e) {
      // TODO: check parameter.
      // example: BAD_KEY - your api-key is incorrect.
      System.out.println(e.getWrongParameter());
      System.out.println(e.getMessage());
    } catch (SMSActivateBaseException e) {
      System.out.println(e.getTypeError());
      System.out.println(e.getMessage());
    }
  }
}
