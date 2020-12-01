package example.api.rent;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.rent.get_rent_list.SMSActivateGetRentListResponse;

public class GetRentListRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      SMSActivateGetRentListResponse smsActivateGetRentListResponse = smsActivateApi.getRentList();
      smsActivateGetRentListResponse.getSmsActivateGetRentResponseList().forEach(x -> {
        System.out.println("ID: " + x.getId());
        System.out.println("Number: " + x.getNumber());
        System.out.println("========================================");
      });
    } catch (SMSActivateWrongParameterException e) {
      e.printStackTrace();
    } catch (SMSActivateBaseException e) {
      e.printStackTrace();
    }
  }
}
