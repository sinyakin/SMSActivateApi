package example.api.multithread;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateActivation;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActivationRun {
  public static void main(String[] args) throws SMSActivateWrongParameterException, IOException {
    final int COUNT_THREAD = 30;
    SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");

    long start = System.currentTimeMillis();
    List<SMSActivateActivation> smsActivateActivationList = new ArrayList<>();
    List<Thread> threadGetNumberList = new ArrayList<>();

    for (int i = 0; i < COUNT_THREAD; i++) {
      threadGetNumberList.add(new Thread(() -> {
        try {
          SMSActivateActivation activateActivation = smsActivateApi.getNumber(0, "tn");
          smsActivateActivationList.add(activateActivation);
        } catch (SMSActivateWrongParameterException e) {
          System.out.println(e.getWrongParameter());
          System.out.println(e.getMessage());
        } catch (SMSActivateBaseException e) {
          System.out.println(e.getTypeError());
          System.out.println(e.getMessage());
        }
       }, "Name: " + (i + 1)));
    }

    new Thread(() -> {
      for (int i = 0; i < threadGetNumberList.size(); i++) {
        threadGetNumberList.get(i).start();
      }
    }).start();

    System.out.println("Time: " + (System.currentTimeMillis() - start));
  }
}
