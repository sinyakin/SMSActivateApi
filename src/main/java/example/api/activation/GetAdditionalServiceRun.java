package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.respone.activation.SMSActivateActivation;
import com.sms_activate.error.SMSActivateBannedException;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.base.SMSActivateBaseTypeError;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.respone.activation.set_status.SMSActivateClientStatus;

public class GetAdditionalServiceRun {

  public static void main(String[] args) {
    try {
      // create SMSActivateApi object for requests
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");

      // request activation
      SMSActivateActivation activation = smsActivateApi.getNumber(0, "av", true);

      // print info
      System.out.println(activation);

      // send MESSAGE_WAS_SENT status after you sent a sms to the number
      smsActivateApi.setStatus(activation, SMSActivateClientStatus.MESSAGE_WAS_SENT);
      // check: https://sms-activate.ru/ru/getNumber

      //todo для Влада: сначала нужно получить смс и только потом можно запросить добавочный сервис
      //todo на сервисе ot+переадресация можно не получять смс (глянь на сайте), но метода в api нет
      //todo то есть этот твой пример всегда не получит getAdditionalService

      // request new activation for additional service
      SMSActivateActivation childActivation = smsActivateApi.getAdditionalService(activation.getId(), "ym");

      // print info about additional activation
      System.out.println(childActivation);

      smsActivateApi.setStatus(activation, SMSActivateClientStatus.FINISH);
      smsActivateApi.setStatus(childActivation, SMSActivateClientStatus.FINISH);
    } catch (SMSActivateWrongParameterException e) {
      if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_ACTION) {
        System.out.println("Contact support.");
      } if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_KEY) {
        System.out.println("Your api-key is incorrect.");
      } else {
        // todo check other wrong parameter
        System.out.println(e.getWrongParameter() + "  " + e.getMessage());
      }
    } catch (SMSActivateBannedException e) {
      System.out.println("Your account has been banned wait " + e.getEndDate());
    } catch (SMSActivateBaseException e) {
      if (e.getTypeError() == SMSActivateBaseTypeError.NO_BALANCE) {
        System.out.println("Top up balance.");
      }
      if (e.getTypeError() == SMSActivateBaseTypeError.NO_NUMBERS) {
        System.out.println("Send request later....");
      } else {
        // todo check other type error
        System.out.println(e.getTypeError() + "  " + e.getMessage());
      }
    }
  }
}
