package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.common.SQLServerException;
import com.sms_activate.error.common.WrongParameterException;

import java.io.IOException;

public class GetBalanceAndCashBackRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");

      System.out.println("Balance: " + smsActivateApi.getBalance().toString());
      System.out.println("Balance + cashback: " + smsActivateApi.getBalanceAndCashBack().toString());
    } catch (WrongParameterException | SQLServerException e) {
      System.out.println(e.getEnglishMessage());
    } catch (IOException ignored) {
    }
  }
}
