import com.sms_activate.SMSActivateApi;
import com.sms_activate.ErrorResponse;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ErrorResponseTest {
    private ErrorResponse value;
    private SMSActivateApi smsActivateApi;

    @Before
    public void setUp() {
        value = ErrorResponse.valueOf("NO_BALANCE");
        smsActivateApi = new SMSActivateApi("WWW");
    }

    @Test
    public void valueOf_Test() {
        Assert.assertThat(value, CoreMatchers.is(ErrorResponse.NO_BALANCE));
        Assert.assertThat(value.getMessage(), CoreMatchers.is("Закончился баланс"));
    }

    @Test
    public void equalsMessageException_Test() {
        try {
            float balance = smsActivateApi.getBalance();
        } catch (Exception e) {
            Assert.assertThat(e.getMessage(), CoreMatchers.is(ErrorResponse.BAD_KEY.getMessage()));
        }
    }
}
