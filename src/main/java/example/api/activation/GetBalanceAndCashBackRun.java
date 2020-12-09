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
      SMSActivateApi smsActivateApi = new SMSActivateApi(System.getenv("API_KEY_SMS_ACTIVATE"));

      smsActivateApi.setSmsActivateExceptionListener(errorFromServer -> {
        System.out.println("Error from server: " + errorFromServer);
      });

      smsActivateApi.setSmsActivateWebClientListener((int cid, @NotNull String request, int a, @NotNull String response) -> {
        System.out.println(String.format(
          "CID: %d REQUEST: %s RESPONSE: %s",
          cid, request, response
        ));
      });

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
