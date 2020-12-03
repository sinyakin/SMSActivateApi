package example.api.multithread;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.set_status.SMSActivateSetStatusRequest;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ActivationRun {
  public static void main(String[] args) throws SMSActivateWrongParameterException, IOException, InterruptedException {
    final int COUNT_THREAD = 30;
    SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");

    System.out.println("Start!!!!!!!");
    ExecutorService pool = Executors.newFixedThreadPool(30);

    for (int i = 0; i < COUNT_THREAD; i++) {
      pool.submit(() -> {
        try {
          smsActivateApi.setStatus(smsActivateApi.getNumber(0, "df").getId(), SMSActivateSetStatusRequest.CANCEL);
        } catch (SMSActivateWrongParameterException e) {
          System.out.println(e.getWrongParameter());
          System.out.println(e.getMessage());
        } catch (SMSActivateBaseException e) {
          System.out.println(e.getTypeError());
          System.out.println(e.getMessage());
        }
      });
    }

    pool.shutdown();
    boolean win = pool.awaitTermination(15, TimeUnit.SECONDS);

    if (!win) {
      System.out.println("Lose....");
    }
  }
}
