package example.api.rent;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.base.SMSActivateBaseTypeError;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.rent.SMSActivateGetRentNumberResponse;
import com.sms_activate.rent.set_rent_status.SMSActivateSetRentStatusRequest;
import com.sms_activate.rent.set_rent_status.SMSActivateSetRentStatusResponse;

public class SetRentStatusRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      // request rent number
      SMSActivateGetRentNumberResponse number = smsActivateApi.getRentNumber("vk");
      /*
        set status for rent
        example: CANCEL
        */
      SMSActivateSetRentStatusResponse smsActivateSetRentStatusResponse = smsActivateApi.setRentStatus(number.getId(), SMSActivateSetRentStatusRequest.CANCEL);

      // print desc about new status
      System.out.println("Description of the current lease status: " + smsActivateSetRentStatusResponse.getStatus().getMessage());
    } catch (SMSActivateWrongParameterException e) {
      e.printStackTrace();
    } catch (SMSActivateBaseException e) {
      if (e.getTypeError() == SMSActivateBaseTypeError.CANT_CANCEL) {
        System.out.println("The lease time has passed more than 20 minutes");
      } else {
        // todo check other type error
        System.out.println(e.getTypeError() + "  " + e.getMessage());
      }
    }
  }
}
