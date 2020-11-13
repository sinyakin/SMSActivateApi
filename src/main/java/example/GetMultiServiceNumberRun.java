package example;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.AccessStatusActivation;
import com.sms_activate.activation.StatusActivationRequest;
import com.sms_activate.error.BannedException;
import com.sms_activate.error.NoBalanceException;
import com.sms_activate.error.NoNumberException;
import com.sms_activate.error.common.SQLServerException;
import com.sms_activate.error.common.WrongParameterException;
import com.sms_activate.phone.Phone;

import java.io.IOException;
import java.util.List;

public class GetMultiServiceNumberRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY", "REFERRAL_LINK");
      List<Phone> phoneList = smsActivateApi.getMultiServiceNumber("go,tg", 0, "0, 0", "mts");

      for (Phone phone : phoneList) {
        System.out.println("id > " + phone.getId());
        System.out.println("number > " + phone.getNumber());
        System.out.println("shortname > " + phone.getService().getShortName());
        System.out.println("---------------------------\n");

        // check https://sms-activate.ru/ru/getNumber
        Thread.sleep(toMilliseconds(20));
      }

      for (Phone phone : phoneList) {
        AccessStatusActivation accessStatusActivation = smsActivateApi.setStatus(phone, StatusActivationRequest.CANCEL);
        System.out.println(accessStatusActivation.getEnglishMessage());
        System.out.println("---------------------------");

        // check https://sms-activate.ru/ru/getNumber
        Thread.sleep(toMilliseconds(20));
      }
    } catch (WrongParameterException | NoNumberException | NoBalanceException | SQLServerException | BannedException e) {
      System.out.println(e.getEnglishMessage());
    } catch (IOException |InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static long toMilliseconds(long seconds) {
    return seconds * 1000;
  }
}
