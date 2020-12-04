package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameter;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.respone.qiwi.SMSActivateGetQiwiRequisitesResponse;
import com.sms_activate.respone.qiwi.SMSActivateQiwiStatus;

public class GetQiwiRequisitesRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      SMSActivateGetQiwiRequisitesResponse smsActivateGetQiwiRequisitesResponse = smsActivateApi.getQiwiRequisites();

      if (smsActivateGetQiwiRequisitesResponse.getStatus() == SMSActivateQiwiStatus.SUCCESS) {
        System.out.println("Comment: " + smsActivateGetQiwiRequisitesResponse.getComment());
        System.out.println("Wallet: " + smsActivateGetQiwiRequisitesResponse.getWallet());
        System.out.println("Up to date: " + smsActivateGetQiwiRequisitesResponse.getUpToDate());
      } else {
        System.out.println("Now payment of kiwi is not possible.");
      }
    } catch (SMSActivateWrongParameterException e) {
      if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_ACTION) {
        System.out.println("Contact support.");
      } if (e.getWrongParameter() == SMSActivateWrongParameter.BAD_KEY) {
        System.out.println("Your api-key is incorrect.");
      } else {
        // todo check other wrong parameter
        System.out.println(e.getMessage() + "  " + e.getMessage());
      }
    } catch (SMSActivateBaseException e) {
      // todo check
      System.out.println(e.getTypeError() + "  " + e.getMessage());
    }
  }
}
