package example.api.multithread;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.SMSActivateActivation;
import com.sms_activate.activation.set_status.SMSActivateSetStatusRequest;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;

import java.util.ArrayList;
import java.util.List;

public class ActivationRun {
  public static void main(String[] args) throws SMSActivateWrongParameterException {
    final int COUNT_THREAD = 30;

    long start = System.currentTimeMillis();
    List<Thread> threadList = new ArrayList<>();
    SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");

    for (int i = 0; i < COUNT_THREAD; i++) {
      threadList.add(new Thread(() -> {
        try {
          SMSActivateActivation smsActivateGetNumberStatusResponse = smsActivateApi.getNumber(0, "tn");
          Thread current = Thread.currentThread();

          System.out.println(smsActivateGetNumberStatusResponse.getNumber() + " in thread " + Thread.currentThread().getName());
/*
          new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
              new Thread(() -> {
                try {
                  SMSActivateGetStatusResponse status = smsActivateApi.getStatus(smsActivateGetNumberStatusResponse.getId());

                  if (status.getSMSActivateGetStatus() == SMSActivateGetStatus.OK || status.getSMSActivateGetStatus() == SMSActivateGetStatus.CANCEL) {
                    System.out.println("End thread: " + current.getName());
                    current.interrupt();
                  } else {
                    System.out.println(current.getName());
                  }
                } catch (SMSActivateBaseException e) {
                  e.printStackTrace();
                }
              }).start();
            }
          }, 0, 100);
*/

          smsActivateApi.setStatus(smsActivateGetNumberStatusResponse.getId(), SMSActivateSetStatusRequest.CANCEL);
        } catch (SMSActivateWrongParameterException e) {
          System.out.println(e.getWrongParameter());
          System.out.println(e.getMessage());
        } catch (SMSActivateBaseException e) {
          System.out.println(e.getTypeError());
          System.out.println(e.getMessage());
        }

        System.out.println(Thread.currentThread().getName());

        System.out.println("=====================================");
      }, "Name: " + (i + 1)));
    }

    for (Thread thread : threadList) {
      thread.start();
    }

    System.out.println("Time: " + (System.currentTimeMillis() - start));
  }
}
