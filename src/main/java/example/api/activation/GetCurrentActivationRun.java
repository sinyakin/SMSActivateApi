package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.SMSActivateOrderBy;
import com.sms_activate.activation.current_activation.SMSActivateGetCurrentActivationsResponse;
import com.sms_activate.activation.set_status.SMSActivateSetStatusRequest;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

public class GetCurrentActivationRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      SMSActivateGetCurrentActivationsResponse smsActivateGetCurrentActivationsResponse = smsActivateApi.getCurrentActivations();

      if (smsActivateGetCurrentActivationsResponse.isExistActivation()) {

        for (int i = 2; smsActivateGetCurrentActivationsResponse.isExistNext(); i++) {
          smsActivateGetCurrentActivationsResponse.getSMSActivateGetCurrentActivationResponseSet().forEach(x -> {
            try {
              smsActivateApi.setStatus(x.getId(), SMSActivateSetStatusRequest.CANCEL);
            } catch (SMSActivateBaseException e) {
              e.printStackTrace();
            }
          });

          smsActivateGetCurrentActivationsResponse = smsActivateApi.getCurrentActivations(i, SMSActivateOrderBy.ASC);
        }

        smsActivateGetCurrentActivationsResponse.getSMSActivateGetCurrentActivationResponseSet().forEach(x -> {
          System.out.println("Id: " + x.getId());
          System.out.println("Number: " + x.getNumber());
          System.out.println("Service: " + x.getServiceName());
          System.out.println("Country id: " + x.getCountryId());
        });
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
