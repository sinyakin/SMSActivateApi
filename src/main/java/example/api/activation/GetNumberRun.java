package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.respone.activation.SMSActivateActivation;
import com.sms_activate.error.SMSActivateBannedException;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.base.SMSActivateBaseTypeError;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.util.*;

public class GetNumberRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");

      /*Set<String> operatorSet = new HashSet<>();
      operatorSet.add("mts");
      operatorSet.add("tele2");*/

      /*Set<String> phoneExceptionSet = new HashSet<>();
      phoneExceptionSet.add("7918");
      phoneExceptionSet.add("7928");*/

      //todo не пиши внутри метода new HashSet - смотрится не красиво) как выше написал - так пиши
      SMSActivateActivation smsActivateActivation = smsActivateApi.getNumber(
        0,
        "tn"
          /*operatorSet, phoneExceptionSet*/);

      System.out.println("Id activation: " + smsActivateActivation.getId());
      System.out.println("Phone number: " + smsActivateActivation.getNumber());
      // check: https://sms-activate.ru/ru/getNumber
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
