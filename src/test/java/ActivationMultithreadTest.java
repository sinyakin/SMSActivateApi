import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.respone.activation.set_status.SMSActivateSetStatusRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ActivationMultithreadTest {
  private SMSActivateApi smsActivateApi;
  private ExecutorService pool;

  private final int COUNT_THREAD = 30;
  private final int STEP = 3;

  @Before
  public void setUp() throws Exception {
    smsActivateApi = new SMSActivateApi("API_KEY");
    smsActivateApi.setRef(0);
    pool = Executors.newFixedThreadPool(COUNT_THREAD / STEP);

    for (int i = 0; i < COUNT_THREAD; i++) {
      pool.submit(() -> {
        try {
          smsActivateApi.setStatus(
            smsActivateApi.getNumber(0, "vk").getId(),
            SMSActivateSetStatusRequest.CANCEL
          );
        } catch (SMSActivateBaseException ignored) {
        }
      });
    }
  }

  @Test
  public void multithreadRun() throws Exception {
    pool.shutdown();
    boolean win = pool.awaitTermination(20, TimeUnit.SECONDS);

    if (!win) {
      throw new RuntimeException();
    }
  }
}
