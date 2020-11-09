import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.AccessStatusActivation;
import com.sms_activate.activation.StateActivation;
import com.sms_activate.activation.StatusActivation;
import com.sms_activate.phone.Phone;
import com.sms_activate.service.Service;

public class Main {
    public static void main(String[] args) {
        try {
            final String TEST_KEY_1 = "eec9A5519dAA3d12d293e83AAc83c6bc";
            final String TEST_KEY_2 = "65942e7978ce8d2fc9f31bAffd325160";
            final String REF = "937725";

            var smsActivateApi = new SMSActivateApi(TEST_KEY_2);
            Phone phone = smsActivateApi.getNumber(new Service("tg"), REF, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
