package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.respone.activation.SMSActivateGetCurrentActivationsResponse;
import com.sms_activate.respone.activation.extra.SMSActivateCurrentActivation;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import org.jetbrains.annotations.NotNull;

public class GetCurrentActivationsRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      /*
        this parameter tells about how many activations need to be returned in one response
        default count is 10.
      */
      int countStringInBatch = 3;

      SMSActivateGetCurrentActivationsResponse smsActivateGetCurrentActivationsResponse = smsActivateApi.getCurrentActivations(1, countStringInBatch);

      if (smsActivateGetCurrentActivationsResponse.isExistActivation()) {
        // if you have more than 10 activations, then we can make request again
        if (smsActivateGetCurrentActivationsResponse.isExistNextBatch()) {
          // batch > 0!!!
          int batch = 1;

          do {
            System.out.println("\n======= " + batch + " ========\n");

            smsActivateGetCurrentActivationsResponse = smsActivateApi.getCurrentActivations(batch++, countStringInBatch);
            smsActivateGetCurrentActivationsResponse.getCurrentActivationList().forEach(GetCurrentActivationsRun::printInfoActivation);
          } while (smsActivateGetCurrentActivationsResponse.isExistNextBatch());

          System.out.println(batch);
        } else {
          System.out.printf("Count your activation is %d: %n", smsActivateGetCurrentActivationsResponse.getTotalCount());
          smsActivateGetCurrentActivationsResponse.getCurrentActivationList().forEach(GetCurrentActivationsRun::printInfoActivation);
        }
      } else {
        System.out.println("No activations.");
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
