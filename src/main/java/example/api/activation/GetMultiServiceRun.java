package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateGetMultiServiceNumberResponse;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.util.ArrayList;
import java.util.HashSet;

public class GetMultiServiceRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
      SMSActivateGetMultiServiceNumberResponse smsActivateGetMultiServiceNumberResponse = smsActivateApi.getMultiServiceNumber(
        0,
        new HashSet<String>() {{
          add("go");
          add("tn");
        }},
        new HashSet<String>() {{
          add("mts");
          add("tele2");
        }},
        new ArrayList<Boolean>() {{
          add(false);
          add(false);
        }}
      );

      smsActivateGetMultiServiceNumberResponse.getSMSActivateActivationList().forEach(activation -> {
        System.out.println("Id: " + activation.getId());
        System.out.println("Number: " + activation.getNumber());
        System.out.println("Service name: " + activation.getShortName());
        System.out.println("===============================================");
      });
    } catch (SMSActivateWrongParameterException e) {
      System.out.println(e.getMessage());
    } catch (SMSActivateBaseException e) {
      e.printStackTrace();
    }
  }
}
