import com.sms_activate.SMSActivateApi;
import com.sms_activate.activation.AccessStatusActivation;
import com.sms_activate.activation.StateActivation;
import com.sms_activate.activation.StatusActivation;
import com.sms_activate.phone.Phone;
import com.sms_activate.rent.Rent;
import com.sms_activate.service.Service;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            final String TEST_KEY_1 = "eec9A5519dAA3d12d293e83AAc83c6bc";
            final String TEST_KEY_2 = "65942e978ce8d2fc9f31bAffd325160";
            final String REF = "937725";
            SMSActivateApi smsActivateApi = new SMSActivateApi(TEST_KEY_2);
            System.out.println(smsActivateApi.getBalance());

            List<Phone> phoneList = smsActivateApi.getCurrentActivationsDataTables();
            System.out.println(phoneList.get(0));

//            StateActivation status = smsActivateApi.getStatus(phone);
//            System.out.println(status.getMessage());
//
//            if (status == StateActivation.STATUS_OK) {
//                System.out.println(status.getCode());
//                AccessStatusActivation accessStatusActivation = smsActivateApi.setStatus(phone, StatusActivation.FINISH);
//                Thread.sleep(15000);
//                System.out.println(accessStatusActivation.getMessage());
//                System.out.println(smsActivateApi.setStatus(phone, StatusActivation.FINISH).getMessage());
//            }
//
//            System.out.println(smsActivateApi.setStatus(phone, StatusActivation.CANCEL).getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
