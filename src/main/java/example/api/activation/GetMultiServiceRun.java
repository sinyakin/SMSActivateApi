package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.respone.activation.SMSActivateGetMultiServiceNumberResponse;
import com.sms_activate.error.SMSActivateBannedException;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.base.SMSActivateBaseTypeError;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.util.*;

public class GetMultiServiceRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");

      // multi-service number is an activation that can be used for more than 1 service.

      /*Set<String> operatorSet = new HashSet<>();
      operatorSet.add("mts");
      operatorSet.add("tele2");*/

      Map<String, Boolean> serviceMap = new HashMap<>();
      serviceMap.put("av", true); // true - номер с переадресацией
      serviceMap.put("vk", false);

      //todo сделай метод getMultiServiceNumber, который принимает serviceMap - это удобно, а внутри сам преобразовывай в нужное для протокола

      /*SMSActivateGetMultiServiceNumberResponse smsActivateGetMultiServiceNumberResponse = smsActivateApi.getMultiServiceNumber(
        0, serviceMap, null,
        // forward
        new ArrayList<Boolean>() {{
          add(true); // av with forward
          add(false);
        }}
        //null
      );

      // print info about activations
      smsActivateGetMultiServiceNumberResponse.getSMSActivateActivationList().forEach(activation -> {
        System.out.println("Id: " + activation.getId());
        System.out.println("Number: " + activation.getNumber());
        System.out.println("Service name: " + activation.getShortName());
        System.out.println("===============================================");
      });*/
    }  catch (SMSActivateWrongParameterException e) {
      if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_ACTION) {
        System.out.println("Contact support.");
      } if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_KEY) {
        System.out.println("Your api-key is incorrect.");
      }

      // todo check other wrong parameter
      System.out.println(e.getMessage() + "  " + e.getMessage());
    } /*catch (SMSActivateBannedException e) {
      System.out.println("Your account has been banned wait " + e.getEndDate());
    } */catch (SMSActivateBaseException e) {
      if (e.getTypeError() == SMSActivateBaseTypeError.NO_BALANCE) {
        System.out.println("Top up balance.");
      }
      if (e.getTypeError() == SMSActivateBaseTypeError.NO_NUMBERS) {
        System.out.println("Send request later....");
      }

      // todo check other type error
      System.out.println(e.getTypeError() + "  " + e.getMessage());
    }
  }
}
