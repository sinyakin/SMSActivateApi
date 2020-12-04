import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.respone.rent.set_rent_status.SMSActivateSetRentStatusRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RentMultithreadTest {
  private SMSActivateApi smsActivateApi;
  private ExecutorService pool;

  private final int COUNT_THREAD = 30;
  private final int STEP = 3;

  @Before
  public void setUp() throws Exception {
    smsActivateApi = new SMSActivateApi("9A34fbf73d52752607e37ebA26f6f0bf");
    pool = Executors.newFixedThreadPool(COUNT_THREAD / STEP);

    for (int i = 0; i < COUNT_THREAD; i++) {
      pool.submit(() -> {
        try {
          smsActivateApi.setRentStatus(
            smsActivateApi.getRentNumberByServiceShortName("sd").getId(),
            SMSActivateSetRentStatusRequest.CANCEL
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
