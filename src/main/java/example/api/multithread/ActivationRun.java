package example.api.multithread;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateActivation;
import com.sms_activate.activation.set_status.SMSActivateSetStatusRequest;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.base.SMSActivateBaseTypeError;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActivationRun {
  public static void main(String[] args) throws SMSActivateWrongParameterException, InterruptedException, IOException {
    final int COUNT_THREAD = 100;

    long start = System.currentTimeMillis();
    List<SMSActivateActivation> smsActivateActivationList = new ArrayList<>();
    List<Thread> threadGetNumberList = new ArrayList<>();
    SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");

    for (int i = 0; i < COUNT_THREAD; i++) {
      threadGetNumberList.add(new Thread(() -> {
        try {
          SMSActivateActivation activateActivation = smsActivateApi.getNumber(0, "tn");
          smsActivateActivationList.add(activateActivation);
          new Thread(() -> {
            for (int j = 0; j < COUNT_THREAD; j++) {
              threadGetNumberList.add(new Thread(() -> {
                for (int k = 0; k < COUNT_THREAD; k++) {
                  try {
                    SMSActivateActivation activateActivation1 = smsActivateApi.getNumber(0, "tn");
                  } catch (SMSActivateBaseException e) {
                    e.printStackTrace();
                  }
                }
              }));
            }
          }).start();
        } catch (SMSActivateWrongParameterException e) {
          System.out.println(e.getWrongParameter());
          System.out.println(e.getMessage());
        } catch (SMSActivateBaseException e) {
          System.out.println(e.getTypeError());
          System.out.println(e.getMessage());
        }
       }, "Name: " + (i + 1)));
    }

    /*new Thread(() -> {
      for (Thread thread : threadGetNumberList) {
        thread.start();
      }
    }).start();*/

    new Thread(() -> {
      for (int i = 0; i < threadGetNumberList.size(); i++) {
        threadGetNumberList.get(i).start();

        final int temp = i;

        new Thread(() -> {
          try {
            smsActivateApi.setStatus(smsActivateActivationList.get(temp).getId(), SMSActivateSetStatusRequest.CANCEL);
          } catch (SMSActivateBaseException | IndexOutOfBoundsException ignored) {
          }
        }).start();

        new Thread(() -> {
          try {
            smsActivateApi.getStatus(smsActivateActivationList.get(temp).getId());
            new Thread(() -> {
              try {
                smsActivateApi.getBalance();
              } catch (SMSActivateBaseException | IndexOutOfBoundsException ignored) {
              }
            }).start();
          } catch (SMSActivateBaseException | IndexOutOfBoundsException ignored) {
          }
        }).start();
      }
    }).start();

    System.in.read();

    System.out.println("Time: " + (System.currentTimeMillis() - start));
  }
}
