package example.api.multithread;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.rent.SMSActivateGetRentNumberResponse;
import com.sms_activate.rent.set_rent_status.SMSActivateSetRentStatusRequest;

import java.util.ArrayList;
import java.util.List;

public class RentRun {
  public static void main(String[] args) throws SMSActivateBaseException, InterruptedException {
    final int COUNT_THREAD = 30;
    long start = System.currentTimeMillis();

    SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
    List<Thread> threadList = new ArrayList<>();

    for (int i = 0; i < COUNT_THREAD; i++) {
      threadList.add(new Thread(() -> {
        try {
          SMSActivateGetRentNumberResponse gt = smsActivateApi.getRentNumber("gt");
          new Thread(() -> {
            try {
              smsActivateApi.setRentStatus(gt.getId(), SMSActivateSetRentStatusRequest.CANCEL);
            } catch (SMSActivateBaseException e) {
              e.printStackTrace();
            }
          }).start();
        } catch (SMSActivateBaseException e) {
          e.printStackTrace();
        }
      }));
    }

    new Thread(() -> {
      for (Thread t : threadList) {
        t.start();
      }
    }).start();

    System.out.println("End main: " + (System.currentTimeMillis() - start));
  }
}
