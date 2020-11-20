package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.qiwi.SMSActivateGetQiwiRequisitesResponse;
import com.sms_activate.qiwi.SMSActivateQiwiStatus;

public class GetQiwiRequisitesRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
      SMSActivateGetQiwiRequisitesResponse smsActivateGetQiwiRequisitesResponse = smsActivateApi.getQiwiRequisites();

      if (smsActivateGetQiwiRequisitesResponse.getStatus() == SMSActivateQiwiStatus.SUCCESS) {
        System.out.println("Comment: " + smsActivateGetQiwiRequisitesResponse.getComment());
        System.out.println("Wallet: " + smsActivateGetQiwiRequisitesResponse.getWallet());
        System.out.println("Up to date: " + smsActivateGetQiwiRequisitesResponse.getUpToDate());
      }
    } catch (SMSActivateWrongParameterException e) {
      System.out.println(e.getWrongParameter());
    } catch (SMSActivateBaseException e) {
      System.out.println(e.getTypeError());
    }
  }
}
