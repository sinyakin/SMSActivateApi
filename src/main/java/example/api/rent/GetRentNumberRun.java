package example.api.rent;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.rent.SMSActivateGetRentNumberResponse;

public class GetRentNumberRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
      SMSActivateGetRentNumberResponse smsActivateGetRentNumberResponse = smsActivateApi.getRentNumber("vk");

      System.out.println(">> ID: " + smsActivateGetRentNumberResponse.getId());
      System.out.println(">> Number: " + smsActivateGetRentNumberResponse.getNumber());
      System.out.println(">> Service: " + smsActivateGetRentNumberResponse.getServiceName());
      System.out.println(">> End date: " + smsActivateGetRentNumberResponse.getEndDate());
    } catch (SMSActivateWrongParameterException e) {
      e.printStackTrace();
    } catch (SMSActivateBaseException e) {
      e.printStackTrace();
    }
  }
}
