package example.api.rent;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.NoBalanceException;
import com.sms_activate.error.NoNumberException;
import com.sms_activate.error.common.SQLServerException;
import com.sms_activate.error.common.WrongParameterException;
import com.sms_activate.error.rent.RentException;
import com.sms_activate.phone.PhoneRent;
import com.sms_activate.service.Service;

import java.io.IOException;

public class GetRentNumberRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      PhoneRent phone = smsActivateApi.getRentNumber(new Service("go")/*, 0, "mts", 3, null*/);

      System.out.println(phone.getId());
      System.out.println(phone.getNumber());
      System.out.println(phone.getEndDate());
      System.out.println(phone.getService().getShortName());
      //check https://sms-activate.ru/ru/rent
    } catch (IOException | SQLServerException | RentException | WrongParameterException | NoBalanceException | NoNumberException e) {
      e.printStackTrace();
    }

  }
}
