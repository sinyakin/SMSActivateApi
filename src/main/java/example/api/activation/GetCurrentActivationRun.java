package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.SMSActivateOrderBy;
import com.sms_activate.activation.SMSActivateGetCurrentActivationsResponse;
import com.sms_activate.activation.set_status.SMSActivateSetStatusRequest;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

public class GetCurrentActivationRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
      SMSActivateGetCurrentActivationsResponse smsActivateGetCurrentActivationsResponse = smsActivateApi.getCurrentActivations();

      if (smsActivateGetCurrentActivationsResponse.isExistActivation()) {
        // if page <= 0 throws Exception.
        for (int page = 1; smsActivateGetCurrentActivationsResponse.isExistNext(); /*page++*/) {
          smsActivateGetCurrentActivationsResponse.getSMSActivateGetCurrentActivationResponseSet().forEach(activation -> {
            System.out.println("Id: " + activation.getId());
            System.out.println("Number: " + activation.getNumber());
            System.out.println("Service: " + activation.getServiceName());
            System.out.println("Country id: " + activation.getCountryId());
            System.out.println("=========================================");

            try {
              smsActivateApi.setStatus(activation.getId(), SMSActivateSetStatusRequest.CANCEL);
            } catch (SMSActivateBaseException e) {
              e.printStackTrace();
            }
          });

          smsActivateGetCurrentActivationsResponse = smsActivateApi.getCurrentActivations(page, SMSActivateOrderBy.ASC);
        }
      } else {
        System.out.println("No activation.");
      }
    } catch (SMSActivateWrongParameterException e) {
      e.printStackTrace();
    } catch (SMSActivateBaseException e) {
      e.printStackTrace();
    }
  }
}
