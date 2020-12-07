package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.respone.activation.SMSActivateActivation;
import com.sms_activate.respone.activation.SMSActivateGetStatusResponse;
import com.sms_activate.respone.activation.extra.SMSActivateGetStatusActivation;
import com.sms_activate.respone.activation.set_status.SMSActivateClientStatus;
import com.sms_activate.respone.activation.set_status.SMSActivateSetStatusResponse;
import com.sms_activate.error.base.SMSActivateBaseException;

/**
 * todo Допиши что делает класс в каждом примере
 */
public class GetAndSetStatusRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");

      SMSActivateActivation activation = smsActivateApi.getNumber(0, "av", false);
      // print info about activation
      System.out.println(activation);

      // check: https://sms-activate.ru/ru/getNumber

      //Thread.sleep(10000);
      //we set the status that the activation is ready to receive sms
      SMSActivateSetStatusResponse smsActivateSetStatusResponse = smsActivateApi.setStatus(activation,
          SMSActivateClientStatus.MESSAGE_WAS_SENT);

      String code = smsActivateApi.waitSms(activation, 5);
      System.out.println("Code from sms: " + code);

      //если нужна еще смс, то нужно отправить статус, сделай пример когда нужна еще одна смс и просто ниже закомменть его


      //Thread.sleep(10000);

      //этот свитч ты можешь закомментировать и написать "Как вручную обрабатывать статусы смс" и ниже пример
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
      // smsActivateApi.setStatus(activation.getId(), SMSActivateSetStatusRequest.CANCEL);

      //todo после того как юзер закончил работу с номером нужно обязательно завершить активацию
      //todo написал как вариант примера когда нужно слать FINISH, а когда CANCEL
      if (code == null) smsActivateApi.setStatus(activation, SMSActivateClientStatus.FINISH);
      else smsActivateApi.setStatus(activation, SMSActivateClientStatus.CANCEL);
    } catch (Exception ex) {
      if (ex instanceof SMSActivateBaseException) {
        // todo check type error
        System.out.println(((SMSActivateBaseException) ex).getTypeError());
        System.out.println(ex.getMessage());
      }
    }
  }
}
