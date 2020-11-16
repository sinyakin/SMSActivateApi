package example.api.rent;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.NoBalanceException;
import com.sms_activate.error.NoNumberException;
import com.sms_activate.error.common.SQLServerException;
import com.sms_activate.error.common.WrongParameterException;
import com.sms_activate.error.rent.RentException;
import com.sms_activate.phone.Phone;
import com.sms_activate.rent.StatusRentRequest;
import com.sms_activate.service.Service;

import java.io.IOException;

public class GetAndSetRentStatusRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      Phone phone = smsActivateApi.getRentNumber(new Service("go"));
      System.out.println(phone.getId());
      System.out.println(phone.getNumber());

      //System.out.println(smsActivateApi.getRentStatus(phone));
      System.out.println(smsActivateApi.setRentStatus(phone, StatusRentRequest.CANCEL).getMessage());
    } catch (WrongParameterException | NoBalanceException | NoNumberException | SQLServerException | RentException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
