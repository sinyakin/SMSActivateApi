package example.api.rent;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.respone.rent.SMSActivateGetRentListResponse;

public class GetRentListRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      /*
      Rent list is a list your current rent.
       */

      SMSActivateGetRentListResponse smsActivateGetRentListResponse = smsActivateApi.getRentList();

      // print info about each rent
      smsActivateGetRentListResponse.getRentNumberList().forEach(activateRentNumber -> {
        System.out.println("ID: " + activateRentNumber.getId());
        System.out.println("Number: " + activateRentNumber.getNumber());
        System.out.println("========================================");
      });
    } catch (SMSActivateWrongParameterException e) {
      if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_ACTION) {
        System.out.println("Contact support.");
      }
      if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_KEY) {
        System.out.println("Your api-key is incorrect.");
      } else {
        // todo check other wrong parameter
        System.out.println(e.getWrongParameter() + "  " + e.getMessage());
      }
    } catch (SMSActivateBaseException e) {
      // todo check
      System.out.println(e.getTypeError() + "  " + e.getMessage());
    }
  }
}
