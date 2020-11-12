package example;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.BannedException;
import com.sms_activate.error.NoBalanceException;
import com.sms_activate.error.NoNumberException;
import com.sms_activate.error.common.SQLServerException;
import com.sms_activate.error.common.WrongParameterException;
import com.sms_activate.phone.Phone;
import com.sms_activate.service.Service;

import java.io.IOException;

public class GetNumberRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf", "");

      Phone phone = smsActivateApi.getNumber(new Service("go"), 0);

      System.out.println("Id: " + phone.getId());
      System.out.println("Number: " + phone.getNumber());
      System.out.println("Service: " + phone.getService().getShortName());
    } catch (WrongParameterException | NoBalanceException | BannedException | SQLServerException | NoNumberException e) {
      System.out.println(e.getEnglishMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
