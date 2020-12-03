package example.api.multithread;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.set_status.SMSActivateSetStatusRequest;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActivationRun {
  public static void main(String[] args) throws SMSActivateWrongParameterException, IOException {
    final int COUNT_THREAD = 20;
    SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");

    long start = System.currentTimeMillis();
    List<Thread> threadGetNumberList = new ArrayList<>();

    for (int i = 0; i < COUNT_THREAD; i++) {
      int numberThread = i;

      threadGetNumberList.add(new Thread(() -> {
        try {
          smsActivateApi.setStatus(smsActivateApi.getNumber(0, "fu").getId(), SMSActivateSetStatusRequest.CANCEL);
        } catch (SMSActivateWrongParameterException e) {
          System.out.println(e.getWrongParameter());
          System.out.println(e.getMessage());
        } catch (SMSActivateBaseException e) {
          System.out.println(e.getTypeError());
          System.out.println(e.getMessage());
        }

        System.out.println(numberThread + " hello world!");
      }));
    }

    new Thread(() -> {
      for (Thread thread : threadGetNumberList) {
        thread.start();
      }
    }).start();

    System.in.read();
    System.out.println("Time: " + (System.currentTimeMillis() - start));
  }
}
