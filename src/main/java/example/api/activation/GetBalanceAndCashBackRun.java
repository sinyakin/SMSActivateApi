package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.listener.SMSActivateWebClientListener;
import com.sms_activate.response.api_activation.SMSActivateGetBalanceAndCashBackResponse;
import com.sms_activate.error.base.SMSActivateBaseException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This example shows how you can get your account balance.
 */
public class GetBalanceAndCashBackRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");

      System.out.println("Your api-key: " + smsActivateApi.getApiKey());

      // request balance
      BigDecimal balance = smsActivateApi.getBalance();

      //request balance and cashback
      SMSActivateGetBalanceAndCashBackResponse smsActivateGetBalanceAndCashBackResponse = smsActivateApi.getBalanceAndCashBack();

      // print info about score
      System.out.println("Balance: " + balance);
      System.out.println("Cashback: " + smsActivateGetBalanceAndCashBackResponse.getCashBack());
      System.out.println("Cashback + balance: " + smsActivateGetBalanceAndCashBackResponse.getBalanceAndCashBack());
    } catch (SMSActivateBaseException e) {
      System.out.println(e.getMessage());
    }
  }
}
