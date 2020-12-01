package example.api.rent;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.rent.SMSActivateGetRentNumberResponse;
import com.sms_activate.rent.set_rent_status.SMSActivateSetRentStatusRequest;
import com.sms_activate.rent.set_rent_status.SMSActivateSetRentStatusResponse;

public class SetRentStatusRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      SMSActivateGetRentNumberResponse number = smsActivateApi.getRentNumber("vk");
      SMSActivateSetRentStatusResponse smsActivateSetRentStatusResponse = smsActivateApi.setRentStatus(number.getId(), SMSActivateSetRentStatusRequest.CANCEL);
      System.out.println(smsActivateSetRentStatusResponse.getStatus().getMessage());
    } catch (SMSActivateWrongParameterException e) {
      e.printStackTrace();
    } catch (SMSActivateBaseException e) {
      e.printStackTrace();
    }
  }
}
