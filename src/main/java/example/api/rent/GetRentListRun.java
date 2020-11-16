package example.api.rent;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.NoBalanceException;
import com.sms_activate.error.NoNumberException;
import com.sms_activate.error.common.SQLServerException;
import com.sms_activate.error.common.WrongParameterException;
import com.sms_activate.error.rent.RentException;
import com.sms_activate.phone.Phone;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class GetRentListRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      List<Phone> phoneList = smsActivateApi.getRentList();
      phoneList.forEach(GetRentListRun::printInfoAboutPhone);
    } catch (WrongParameterException | NoBalanceException | NoNumberException | SQLServerException | RentException e) {
      System.out.println(e.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  private static void printInfoAboutPhone(@NotNull Phone phone) {
    System.out.println("Id: " + phone.getId());
    System.out.println("Number: " + phone.getNumber());
  }
}
