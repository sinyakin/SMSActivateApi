package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.SMSActivateOrderBy;
import com.sms_activate.activation.SMSActivateGetCurrentActivationsResponse;
import com.sms_activate.activation.extra.SMSActivateCurrentActivation;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import org.jetbrains.annotations.NotNull;

public class GetCurrentActivationRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      /*
        this parameter tells about how many activations need to be returned in one response
        default count is 10.
      */
      int countStringInBatch = 5;

      SMSActivateGetCurrentActivationsResponse smsActivateGetCurrentActivationsResponse = smsActivateApi.getCurrentActivations(1, countStringInBatch, SMSActivateOrderBy.ASC);

      if (smsActivateGetCurrentActivationsResponse.isExistActivation()) {
        // if you have more than 10 activations, then we can make request again
        if (smsActivateGetCurrentActivationsResponse.isExistNextBatch()) {
          // if batch > 0!!!
          int batch = 1;

          do {
            smsActivateGetCurrentActivationsResponse.getSMSActivateGetCurrentActivationResponseSet().forEach(GetCurrentActivationRun::printInfoActivation);
            smsActivateGetCurrentActivationsResponse = smsActivateApi.getCurrentActivations(batch, countStringInBatch, SMSActivateOrderBy.ASC);
          } while (smsActivateGetCurrentActivationsResponse.isExistNextBatch());
        } else {
          System.out.printf("Count your activation is %d: %n", smsActivateGetCurrentActivationsResponse.getTotalCount());
          smsActivateGetCurrentActivationsResponse.getSMSActivateGetCurrentActivationResponseSet().forEach(GetCurrentActivationRun::printInfoActivation);
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

  private static void printInfoActivation(@NotNull SMSActivateCurrentActivation activation) {
    System.out.println("Id: " + activation.getId());
    System.out.println("Number: " + activation.getNumber());
    System.out.println("Service: " + activation.getServiceName());
    System.out.println("Country id: " + activation.getCountryId());
    System.out.println("=========================================");
  }
}
