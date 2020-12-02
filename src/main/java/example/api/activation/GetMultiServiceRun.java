package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateGetMultiServiceNumberResponse;
import com.sms_activate.error.SMSActivateBannedException;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.base.SMSActivateBaseTypeError;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.util.ArrayList;
import java.util.HashSet;

public class GetMultiServiceRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
      SMSActivateGetMultiServiceNumberResponse smsActivateGetMultiServiceNumberResponse = smsActivateApi.getMultiServiceNumber(
        0,
        // services
        new HashSet<String>() {{
          add("av");
          add("vk");
        }},null
        // operators
/*        new HashSet<String>() {{
          add("mts");
          add("tele2");
        }}*/,
        // forward
        new ArrayList<Boolean>() {{
          add(true); // go with forward
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
      });
    }  catch (SMSActivateWrongParameterException e) {
      if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_ACTION) {
        System.out.println("Contact support.");
      } if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_KEY) {
        System.out.println("Your api-key is incorrect.");
      } else {
        // todo check other wrong parameter
        System.out.println(e.getMessage() + "  " + e.getMessage());
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
