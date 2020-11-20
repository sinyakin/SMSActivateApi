package example.api.rent;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.rent.SMSActivateGetRentNumberResponse;
import com.sms_activate.rent.get_rent_status.SMSActivateGetRentStatusResponse;

public class GetRentStatusRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
      SMSActivateGetRentStatusResponse smsActivateGetRentStatusResponse = smsActivateApi.getRentStatus(417694);
      System.out.println("Count sms: " + smsActivateGetRentStatusResponse.getQuantity());
      smsActivateGetRentStatusResponse.getSmsActivateSMSList().forEach(x -> {
        System.out.println("Phone from: " + x.getPhoneFrom());
        System.out.println("Text: " + x.getText());
        System.out.println("Date: " + x.getDate());
        System.out.println("=========================================");
      });
    } catch (SMSActivateWrongParameterException e) {
      e.printStackTrace();
    } catch (SMSActivateBaseException e) {
      e.printStackTrace();
    }
  }
}
