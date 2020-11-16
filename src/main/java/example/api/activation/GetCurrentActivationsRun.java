package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.WrongResponseException;
import com.sms_activate.error.common.SQLServerException;
import com.sms_activate.error.common.WrongParameterException;
import com.sms_activate.phone.Phone;

import java.io.IOException;
import java.util.List;

public class GetCurrentActivationsRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      List<Phone> phoneList = smsActivateApi.getCurrentActivations();

      System.out.println("Your current activations:");
      phoneList.forEach(phone -> {
        System.out.println("Id: " + phone.getId());
        System.out.println("Number: " + phone.getNumber());
        System.out.println("Service name: " + phone.getService());
      });
    } catch (WrongParameterException | SQLServerException | WrongResponseException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
