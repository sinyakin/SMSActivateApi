import com.sms_activate.*;
import com.sms_activate.phone.Phone;
import com.sms_activate.rent.StateRent;
import com.sms_activate.rent.StatusRent;
import com.sms_activate.service.Service;

public class Main {
    public static void main(String[] args) {
        try {
            final String TEST_KEY_1 = "eec9A5519dAA3d12d293e83AAc83c6bc";
            final String TEST_KEY_2 = "65942e7978ce8d2fc9f31bAffd325160";

            SMSActivateApi smsActivateApi = new SMSActivateApi(TEST_KEY_2);
            Phone phone = smsActivateApi.getRentNumber(new Service("go"));
            StateRent stateRent = smsActivateApi.setRentStatus(phone, StatusRent.CANCEL);
            System.out.println(stateRent.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
