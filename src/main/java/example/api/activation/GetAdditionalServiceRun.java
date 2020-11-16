package example.api.activation;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.StatusActivationRequest;
import com.sms_activate.error.BannedException;
import com.sms_activate.error.NoBalanceException;
import com.sms_activate.error.NoNumberException;
import com.sms_activate.error.WrongResponseException;
import com.sms_activate.error.common.SQLServerException;
import com.sms_activate.error.common.WrongParameterException;
import com.sms_activate.phone.Phone;
import com.sms_activate.service.Service;

import java.io.IOException;
import java.util.Scanner;

public class GetAdditionalServiceRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      Service service = new Service("ot"); // ot - other
      int countryId = 0; //russia
      Phone phone = smsActivateApi.getNumber(service, countryId, null, null, true);
      System.out.println("Id: " + phone.getId());
      System.out.println("Number: " + phone.getNumber());
      // check: https://sms-activate.ru/ru/getNumber

      System.out.println("Register number in service" + service.getShortName());
      Scanner scanner = new Scanner(System.in);
      scanner.next();

      System.out.println(smsActivateApi.getFullSms(phone));
      System.out.println(smsActivateApi.setStatus(phone, StatusActivationRequest.FINISH).getEnglishMessage());

      Phone phone1 = smsActivateApi.getAdditionalService(phone, service);

      System.out.println("Id: " + phone1.getId());
      System.out.println("Number: " + phone1.getNumber());

      System.out.println(smsActivateApi.getStatus(phone).getEnglishMessage());
      System.out.println(smsActivateApi.getStatus(phone1).getEnglishMessage());

      Thread.sleep(15000);
      // check: https://sms-activate.ru/ru/getNumber

      System.out.println(smsActivateApi.setStatus(phone1, StatusActivationRequest.CANCEL).getEnglishMessage());
    } catch (WrongParameterException | NoBalanceException | BannedException | SQLServerException | NoNumberException e) {
      System.out.println(e.getMessage());
    } catch (IOException | InterruptedException | WrongResponseException e) {
      e.printStackTrace();
    }
  }
}
