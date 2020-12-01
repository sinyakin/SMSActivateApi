package example.api.multithread;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.get_status.SMSActivateGetStatus;
import com.sms_activate.error.base.SMSActivateBaseException;

public class RentRun {
  public static void main(String[] args) throws SMSActivateBaseException, InterruptedException {
    SMSActivateApi smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
    // todo added test with thread.
  }
}
