package example.api.rent;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.rent.SMSActivateGetRentListResponse;

public class GetRentListRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
      SMSActivateGetRentListResponse smsActivateGetRentListResponse = smsActivateApi.getRentList();
      smsActivateGetRentListResponse.getSmsActivateGetRentResponseList().forEach(x -> {
        System.out.println("ID: " + x.getId());
        System.out.println("Number: " + x.getNumber());
        System.out.println("========================================");
      });
    }catch (SMSActivateWrongParameterException e) {
      if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_ACTION) {
        System.out.println("Contact support.");
      } if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_KEY) {
        System.out.println("Your api-key is incorrect.");
      } else {
        // todo check other wrong parameter
        System.out.println(e.getMessage() + "  " + e.getMessage());
      }
    } catch (SMSActivateBaseException e) {
      // todo check
      System.out.println(e.getTypeError() + "  " + e.getMessage());
    }
  }
}
