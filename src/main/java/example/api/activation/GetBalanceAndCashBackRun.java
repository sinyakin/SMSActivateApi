package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.balance.SMSActivateGetBalanceAndCashBackResponse;
import com.sms_activate.activation.balance.SMSActivateGetBalanceResponse;
import com.sms_activate.error.base.SMSActivateBaseException;

public class GetBalanceAndCashBackRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
      System.out.println("Your api-key: " + smsActivateApi.getApiKey());

      SMSActivateGetBalanceResponse getBalanceResponse = smsActivateApi.getBalance();
      SMSActivateGetBalanceAndCashBackResponse smsActivateGetBalanceAndCashBackResponse = smsActivateApi.getBalanceAndCashBack();

      System.out.println("Balance: " + getBalanceResponse.getBalance());
      System.out.println("Cashback: " + smsActivateGetBalanceAndCashBackResponse.getBalance());
      System.out.println("Cashback + balance: " + smsActivateGetBalanceAndCashBackResponse.getBalanceAndCashBack());
    } catch (SMSActivateBaseException e) {
      System.out.println(e.getMessage());
    }
  }
}
