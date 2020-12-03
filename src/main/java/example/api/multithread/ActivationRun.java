package example.api.multithread;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.set_status.SMSActivateSetStatusRequest;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ActivationRun {
  public static void main(String[] args) throws SMSActivateWrongParameterException, IOException, InterruptedException {
    final int COUNT_THREAD = 30;
    SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
    long start = System.currentTimeMillis();

    try {
      smsActivateApi.getNumber(0, "vk");
    } catch (SMSActivateBaseException e) {
      e.printStackTrace();
    }

    System.out.println(System.currentTimeMillis() - start);

/*    ExecutorService pool = Executors.newFixedThreadPool(20);
    for (int i = 0; i < COUNT_THREAD; i++) {
      pool.submit(() -> {
        try {
          smsActivateApi.setStatus(smsActivateApi.getNumber(0, "fu").getId(), SMSActivateSetStatusRequest.CANCEL);
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
    boolean win = pool.awaitTermination(20, TimeUnit.SECONDS);
    win = true;
 */
  }
}
