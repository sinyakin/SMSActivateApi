package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.common.SQLServerException;
import com.sms_activate.error.common.WrongParameterException;
import com.sms_activate.qiwi.QiwiResponse;

import java.io.IOException;

public class GetQiwiRequisitesRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      QiwiResponse qiwiResponse = smsActivateApi.getQiwiRequisites();
      System.out.println("Status: " + qiwiResponse.getQiwiStatus().getMessage());
      System.out.println("Comment: " + qiwiResponse.getComment());
      System.out.println("Wallet: " + qiwiResponse.getWalletNumber());
    } catch (WrongParameterException | SQLServerException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
