package example.api.multithread;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateActivation;
import com.sms_activate.activation.SMSActivateGetCurrentActivationsResponse;
import com.sms_activate.activation.set_status.SMSActivateSetStatusRequest;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ActivationRun {
  public static void main(String[] args) throws SMSActivateWrongParameterException, IOException {
    final int COUNT_THREAD = 20;
    SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");

    long start = System.currentTimeMillis();
    List<Thread> threadGetNumberList = new ArrayList<>();

    for (int i = 0; i < COUNT_THREAD; i++) {
      threadGetNumberList.add(new Thread(() -> {
        try {
          List<SMSActivateActivation> smsActivateActivationList = new ArrayList<>();
          for (int j = 0; j < 5; j++) {
            SMSActivateActivation activateActivation = smsActivateApi.getNumber(0, "tn");
            smsActivateActivationList.add(activateActivation);
          }

          new Thread(() -> {
            for (SMSActivateActivation activateActivation : smsActivateActivationList) {
              try {
                smsActivateApi.setStatus(activateActivation.getId(), SMSActivateSetStatusRequest.CANCEL);
              } catch (SMSActivateBaseException e) {
                e.printStackTrace();
              }
            }
          }).start();
        } catch (SMSActivateWrongParameterException e) {
          System.out.println(e.getWrongParameter());
          System.out.println(e.getMessage());
        } catch (SMSActivateBaseException e) {
          System.out.println(e.getTypeError());
          System.out.println(e.getMessage());
        }
      }));
    }

    new Thread(() -> {
      for (Thread thread : threadGetNumberList) {
        thread.start();
      }

      new Thread(() -> {
        try {
          do {
            SMSActivateGetCurrentActivationsResponse currentActivations = smsActivateApi.getCurrentActivations();
            currentActivations.getAllActivation().forEach(activation -> {
              try {
                new Thread(() -> {
                  try {
                    smsActivateApi.setStatus(activation.getId(), SMSActivateSetStatusRequest.SEND_READY_NUMBER);
                  } catch (SMSActivateBaseException ignored) {
                  }
                }).start();

                smsActivateApi.setStatus(activation.getId(), SMSActivateSetStatusRequest.CANCEL);
              } catch (SMSActivateBaseException ignored) {
              }
            });

            if (!currentActivations.isExistNextBatch()) {
              break;
            }
          } while (true);
        } catch (SMSActivateBaseException e) {
          e.printStackTrace();
        }

        System.out.println("Hello world");
      }).start();
    }).start();

    System.in.read();
    System.out.println("Time: " + (System.currentTimeMillis() - start));
  }
}
