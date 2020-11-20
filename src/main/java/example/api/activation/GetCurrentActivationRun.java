package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.current_activation.SMSActivateGetCurrentActivationsResponse;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

public class GetCurrentActivationRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
      SMSActivateGetCurrentActivationsResponse smsActivateGetCurrentActivationsResponse = smsActivateApi.getCurrentActivations();

      if (!smsActivateGetCurrentActivationsResponse.haveActivation()) {
        smsActivateGetCurrentActivationsResponse.getSMSActivateGetCurrentActivationResponseSet().forEach(x -> {
          System.out.println("id: " + x.getId());
          System.out.println("Number" + x.getNumber());
          System.out.println("Service: " + x.getServiceName());
          System.out.println("CountryName: " + x.getCountryId());
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
