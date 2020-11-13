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
import com.sms_activate.service.Service;

import java.io.IOException;
import java.util.function.Function;

public class GetNumberRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY", "REFERRAL_LINK");
      Phone phone = smsActivateApi.getNumber(new Service("go"), 0);

      System.out.println("Id: " + phone.getId());
      System.out.println("Number: " + phone.getNumber());
      //check https://sms-activate.ru/ru/getNumber

      Thread.sleep(toMilliseconds(60)); // sleep on 60s

      AccessStatusActivation accessStatusActivation = smsActivateApi.setStatus(phone, StatusActivationRequest.CANCEL);
      System.out.println(accessStatusActivation.getEnglishMessage());
    } catch (WrongParameterException | NoBalanceException | BannedException | SQLServerException | NoNumberException e) {
      System.out.println(e.getEnglishMessage());
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static long toMilliseconds(long second) {
    return second * 1000;
  }
}
