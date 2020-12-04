package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.respone.activation.SMSActivateActivation;
import com.sms_activate.respone.activation.SMSActivateGetStatusResponse;
import com.sms_activate.respone.activation.set_status.SMSActivateSetStatusRequest;
import com.sms_activate.respone.activation.set_status.SMSActivateSetStatusResponse;
import com.sms_activate.error.base.SMSActivateBaseException;

public class GetAndSetStatusRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");

      SMSActivateActivation smsActivateActivation = smsActivateApi.getNumber(0, "av", true);
      // print info about activation
      System.out.println("Id: " + smsActivateActivation.getId());
      System.out.println("Number: " + smsActivateActivation.getNumber());

      // check: https://sms-activate.ru/ru/getNumber

      //Thread.sleep(10000);

      SMSActivateGetStatusResponse smsActivateGetStatusResponse = smsActivateApi.getStatus(smsActivateActivation.getId());

      System.out.println("Description of the current lease status: " + smsActivateGetStatusResponse.getMessage());

      if (smsActivateGetStatusResponse.getCodeFromSMS() != null) {
        System.out.println("Code from sms: " + smsActivateGetStatusResponse.getCodeFromSMS());
      }

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
          System.out.println("Activation is complete.");
          break;
        case CANCEL: // if activation has been canceled.
          System.out.println("Activation is cancel.");
          break;
      }

      smsActivateGetStatusResponse = smsActivateApi.getStatus(smsActivateActivation.getId());
      System.out.println("Description of the current lease status: " + smsActivateGetStatusResponse.getMessage());
    } catch (SMSActivateBaseException e) {
      // todo check type error
      System.out.println(e.getTypeError());
      System.out.println(e.getMessage());
    }
  }
}
