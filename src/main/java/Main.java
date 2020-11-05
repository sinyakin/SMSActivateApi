import com.google.gson.Gson;
import com.sms_activate.*;
import com.sms_activate.activation.AccessStatus;
import com.sms_activate.activation.State;
import com.sms_activate.activation.Status;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            final String TEST_KEY_1 = "eec9A5519dAA3d12d293e83AAc83c6bc";
            final String TEST_KEY_2 = "65942e7978ce8d2fc9f31bAffd325160";

            SMSActivateApi smsActivateApi = new SMSActivateApi(TEST_KEY_2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
