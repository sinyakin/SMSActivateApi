import com.sms_activate.SMSActivateApi;
import com.sms_activate.phone.Phone;
import com.sms_activate.rent.StateRent;
import com.sms_activate.rent.StatusRent;
import com.sms_activate.service.Service;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static String val(@NotNull String a) {
        int w = Integer.parseInt(a);
        if (w < 0) return null;
        return (w * w) + "";
    }

    public static void main(String[] args) {
        try {
            final String TEST_KEY_1 = "eec9A5519dAA3d12d293e83AAc83c6bc";
            final String TEST_KEY_2 = "65942e7978ce8d2fc9f31bAffd325160";
            Map<String, String> map = new HashMap<>(){{
                put("WW", TEST_KEY_1);
                put("DD", TEST_KEY_2);
            }};

            System.out.println(map.entrySet().stream().map(Objects::toString).collect(Collectors.joining("&")));
//            SMSActivateApi smsActivateApi = new SMSActivateApi(TEST_KEY_2);
//            Phone phone = smsActivateApi.getRentNumber(new Service("go"));
//            StateRent stateRent = smsActivateApi.setRentStatus(phone, StatusRent.CANCEL);
            //System.out.println(stateRent.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
