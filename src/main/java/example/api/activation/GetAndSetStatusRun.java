package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateActivation;
import com.sms_activate.activation.get_status.SMSActivateGetStatusResponse;
import com.sms_activate.activation.set_status.SMSActivateSetStatusRequest;
import com.sms_activate.activation.set_status.SMSActivateSetStatusResponse;
import com.sms_activate.error.base.SMSActivateBaseException;

public class GetAndSetStatusRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
      SMSActivateActivation smsActivateActivation = smsActivateApi.getNumber(
        0,
        "vk"/*,
          new HashSet<String>(){{
            add("7918");
          }},
          new HashSet<String>(){{
            add("mts");
            add("tele2");
          }},
          false*/);
      System.out.println("Id: " + smsActivateActivation.getId());
      System.out.println("Number: " + smsActivateActivation.getNumber());
      // check: https://sms-activate.ru/ru/getNumber

      //Thread.sleep(10000);

      SMSActivateGetStatusResponse smsActivateGetStatusResponse = smsActivateApi.getStatus(smsActivateActivation.getId());

      System.out.println("Current status: " + smsActivateGetStatusResponse.getMessage());

      SMSActivateSetStatusResponse smsActivateSetStatusResponse = smsActivateApi.setStatus(smsActivateActivation.getId(),
          SMSActivateSetStatusRequest.SEND_READY_NUMBER);

      // check: https://sms-activate.ru/ru/getNumber

      //Thread.sleep(10000);

      switch (smsActivateSetStatusResponse.getSMSActivateAccessStatus()) {
        case READY: // if number is ready.
          System.out.println("Activation is ready");
          break;
        case RETRY_GET: // if we are wait retry sms.
          System.out.println("Activation wait retry sms....");
          break;
        case ACTIVATION: // if activation is completed is successfully.
          System.out.println("Activation is completed.");
          break;
        case CANCEL: // if activation has been canceled.
          System.out.println("Activation is canceled.");
          break;
      }

      smsActivateGetStatusResponse = smsActivateApi.getStatus(smsActivateActivation.getId());
      System.out.println("Current status: " + smsActivateGetStatusResponse.getMessage());
    } catch (SMSActivateBaseException e) {
      System.out.println(e.getTypeError());
      System.out.println(e.getMessage());
    }
  }
}
