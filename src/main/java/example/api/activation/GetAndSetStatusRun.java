package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.response.api_activation.SMSActivateActivation;
import com.sms_activate.response.api_activation.SMSActivateGetStatusResponse;
import com.sms_activate.client_enums.SMSActivateClientStatus;
import com.sms_activate.response.api_activation.SMSActivateSetStatusResponse;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.response.api_activation.enums.SMSActivateGetStatusActivation;
import com.sms_activate.response.api_activation.enums.SMSActivateServerStatus;

/**
 * This class shows how to deal with activation statuses.
 * <ul>
 *   <li>If you need to receive an SMS, then you need to set the MESSAGE_WAS_SENT status in advance;</li>
 *   <li>If you need to cancel the activation, then you need to send the CANCEL status (activation, which has already received an SMS, can only be completed);</li>
 *   <li>If you need additional SMS, then send the REQUEST_ONE_MORE_CODE status;</li>
 *   <li>To complete the activation, send the status (you can complete the activation only if you received an SMS) - FINISH.</li>
 * </ul>
 * <br/>
 * After changing the status, you will receive an answer about how the change went:
 * <ul>
 *   <li>READY - number readiness confirmed;</li>
 *   <li>RETRY_GET - waiting new sms;</li>
 *   <li>ACTIVATION - service has been activate;</li>
 *   <li>CANCEL - activation is canceled.</li>
 * </ul>
 * <p>For the ot + redirect service, you can send the FAKE_SMS status.</p>
 */
public class GetAndSetStatusRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      // 1. Set referral link.
      smsActivateApi.setRef("YOUR_REFERRAL_LINK");

      // 2. Request to get number.
      SMSActivateActivation activation = smsActivateApi.getNumber(0, "ot");
      // print info about activation
      System.out.println(activation);

      // after get number status your activation is WAIT_CODE
      SMSActivateGetStatusResponse smsActivateGetStatusResponse = smsActivateApi.getStatus(activation);
      assert smsActivateGetStatusResponse.getSMSActivateGetStatus() == SMSActivateGetStatusActivation.WAIT_CODE;

      // check: https://sms-activate.ru/ru/getNumber

      // 3. we set the status that the activation is ready to receive sms
      SMSActivateSetStatusResponse smsActivateSetStatusResponse = smsActivateApi.setStatus(activation,
        SMSActivateClientStatus.MESSAGE_WAS_SENT);

      // now status is WAIT_CODE.
      smsActivateGetStatusResponse = smsActivateApi.getStatus(activation);
      assert smsActivateGetStatusResponse.getSMSActivateGetStatus() == SMSActivateGetStatusActivation.WAIT_CODE;

      String code = null;

      if (smsActivateSetStatusResponse.getSMSActivateAccessStatus() == SMSActivateServerStatus.READY) {
        code = smsActivateApi.waitSms(activation, 5);

        // after received sms status is OK
        smsActivateGetStatusResponse = smsActivateApi.getStatus(activation);
        assert smsActivateGetStatusResponse.getSMSActivateGetStatus() == SMSActivateGetStatusActivation.OK;

        System.out.println("Code from sms: " + code);
      }

      // 4. if your service timed out and you need a new code (repeated), then you can simply set the REQUEST_ONE_MORE_CODE status,
      // after which you can request a new SMS from your service.
      // smsActivateSetStatusResponse = smsActivateApi.setStatus(activation, SMSActivateClientStatus.REQUEST_ONE_MORE_CODE);
      // smsActivateGetStatusResponse = smsActivateApi.getStatus(activation);
      // assert smsActivateGetStatusResponse.getSMSActivateGetStatus() == SMSActivateGetStatusActivation.WAIT_CODE;
      // if (smsActivateSetStatusResponse.getSMSActivateAccessStatus() == SMSActivateServerStatus.RETRY_GET) {
      //   code = smsActivateApi.waitSms(activation, 5);
      //   System.out.println("New code from sms: " + code);
      // }


      if (code == null)  {
        smsActivateSetStatusResponse = smsActivateApi.setStatus(activation, SMSActivateClientStatus.FINISH);

        if (smsActivateSetStatusResponse.getSMSActivateAccessStatus() == SMSActivateServerStatus.FINISH) {
          System.out.println("Activation is finished.");
        }

        //after activation is completed, the status will be OK
        smsActivateGetStatusResponse = smsActivateApi.getStatus(activation);
        assert smsActivateGetStatusResponse.getSMSActivateGetStatus() == SMSActivateGetStatusActivation.OK;
      } else {
        smsActivateSetStatusResponse = smsActivateApi.setStatus(activation, SMSActivateClientStatus.CANCEL);

        if (smsActivateSetStatusResponse.getSMSActivateAccessStatus() == SMSActivateServerStatus.CANCEL) {
          System.out.println("Activation is canceled.");
        }

        // after activation is canceled, the status will be CANCEL
        smsActivateGetStatusResponse = smsActivateApi.getStatus(activation);
        assert smsActivateGetStatusResponse.getSMSActivateGetStatus() == SMSActivateGetStatusActivation.CANCEL;
      }
    } catch (Exception ex) {
      if (ex instanceof SMSActivateBaseException) {
        // todo check type error
        System.out.println(((SMSActivateBaseException) ex).getTypeError());
        System.out.println(ex.getMessage());
      }
    }
  }
}
