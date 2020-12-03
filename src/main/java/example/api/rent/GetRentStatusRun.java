package example.api.rent;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.base.SMSActivateBaseTypeError;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.rent.SMSActivateGetRentStatusResponse;
import com.sms_activate.rent.set_rent_status.SMSActivateSetRentStatusRequest;

public class GetRentStatusRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      // ID rent for check status rent
      SMSActivateGetRentStatusResponse smsActivateGetRentStatusResponse = smsActivateApi.getRentStatus(000);

      // count sms in rent
      System.out.println("Count sms: " + smsActivateGetRentStatusResponse.getCountSms());

      // info about each sms
      smsActivateGetRentStatusResponse.getSmsActivateSMSList().forEach(smsActivateSMS -> {
        System.out.println("Phone from: " + smsActivateSMS.getPhoneFrom());
        System.out.println("Text: " + smsActivateSMS.getText());
        System.out.println("Date: " + smsActivateSMS.getDate());
        System.out.println("=========================================");
      });
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
    } catch (SMSActivateBaseException e) {
      if (e.getTypeError() == SMSActivateBaseTypeError.WAIT_CODE) {
        System.out.println("No sms...");
      } else {
        // todo check other type error
        System.out.println(e.getTypeError() + "  " + e.getMessage());
      }
    }
  }
}
