package example.api.rent;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.SMSActivateBannedException;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.base.SMSActivateBaseTypeError;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.rent.extra.SMSActivateGetRentNumber;

public class GetRentNumberRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      // request rent for vk
      // you can set rent time
      SMSActivateGetRentNumber smsActivateGetRentNumberResponse = smsActivateApi.getRentNumber(
        "vk",
          0,
        "mts",
        SMSActivateApi.MINIMAL_RENT_TIME,
        "https://google.com/webhook.php"
        );

      // print info about rent
      System.out.println(">> ID: " + smsActivateGetRentNumberResponse.getId());
      System.out.println(">> Number: " + smsActivateGetRentNumberResponse.getNumber());
      System.out.println(">> End date: " + smsActivateGetRentNumberResponse.getEndDate());
    } catch (SMSActivateWrongParameterException e) {
      if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_ACTION) {
        System.out.println("Contact support.");
      }
      if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_KEY) {
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
