package example.api.multithread;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.respone.activation.SMSActivateActivation;
import com.sms_activate.respone.activation.set_status.SMSActivateSetStatusRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ActivationRun {
  public static void main(String[] args) throws SMSActivateWrongParameterException, InterruptedException {
    final int COUNT_THREAD = 30;
    final int STEP = 3;

    SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
    ExecutorService pool = Executors.newFixedThreadPool(COUNT_THREAD / STEP);

    System.out.println("Start!!!!!!!");

    for (int i = 0; i < COUNT_THREAD; i++) {
      pool.submit(() -> {
        try {
          List<SMSActivateActivation> smsActivateActivationList = new ArrayList<>();

          for (int j = 0; j < 3; j++) {
            smsActivateActivationList.add(smsActivateApi.getNumber(0, "tk"));
          }

          for (SMSActivateActivation activateActivation : smsActivateActivationList) {
            smsActivateApi.setStatus(activateActivation.getId(), SMSActivateSetStatusRequest.CANCEL);
          }
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
