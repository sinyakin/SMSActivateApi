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
      final int REFERRAL_IDENTIFIER = 0;
      SMSActivateApi smsActivateApi = new SMSActivateApi("rep");

      smsActivateApi.setRef(REFERRAL_IDENTIFIER);

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

      //we set the status that the activation is ready to receive
      // SMS after which you can use the activation for your service
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

      // uncomment this line if you need to cancel the activation, if you will not use it or wait 20 minutes.
      // smsActivateApi.setStatus(smsActivateActivation.getId(), SMSActivateSetStatusRequest.CANCEL);

      // check current status activation
      smsActivateGetStatusResponse = smsActivateApi.getStatus(smsActivateActivation.getId());
      System.out.println("Description of the current lease status: " + smsActivateGetStatusResponse.getMessage());
    } catch (SMSActivateBaseException e) {
      // todo check type error
      System.out.println(e.getTypeError());
      System.out.println(e.getMessage());
    }
  }
}
