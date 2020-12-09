package example.api.multithread;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.client_enums.SMSActivateClientRentStatus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RentRun {
  public static void main(String[] args) throws Exception {
    final int COUNT_THREAD = 20;
    final int STEP = 2;

    SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
    ExecutorService pool = Executors.newFixedThreadPool(COUNT_THREAD / STEP);

    for (int i = 0; i < COUNT_THREAD; i++) {
      pool.submit(() -> {
        try {
          smsActivateApi.setRentStatus(smsActivateApi.getRentNumber(0, "yw").getId(), SMSActivateClientRentStatus.CANCEL);
        } catch (SMSActivateBaseException e) {
          e.printStackTrace();
        }
      });
    }

    pool.shutdown();
    boolean win = pool.awaitTermination(20, TimeUnit.SECONDS);

    if (!win) {
      System.out.println("Lose.....");
    }
  }
}
