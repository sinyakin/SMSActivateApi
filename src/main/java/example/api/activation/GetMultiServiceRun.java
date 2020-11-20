package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateGetMultiServiceNumberResponse;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.util.ArrayList;
import java.util.HashSet;

public class GetMultiServiceRun {
  public static void main(String[] args) {
    //9A34fbf73d52752607e37ebA26f6f0bf
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      SMSActivateGetMultiServiceNumberResponse smsActivateGetMultiServiceNumberResponse = smsActivateApi.getMultiServiceNumber(
          new HashSet<String>() {{
            add("vk");
            add("go");
            add("tg");
          }}, 0,
          new ArrayList<Boolean>() {{
            add(false);
            add(false);
            add(false);
          }},
          null
      );

      smsActivateGetMultiServiceNumberResponse.getSMSActivateActivationList().forEach(x -> {
        System.out.println("Id: " + x.getId());
        System.out.println("Number: " + x.getNumber());
        System.out.println("Service name: " + x.getServiceName());
        System.out.println("===============================================");
      });
    } catch (SMSActivateWrongParameterException e) {
      System.out.println(e.getMessage());
    } catch (SMSActivateBaseException e) {
      e.printStackTrace();
    }
  }
}
