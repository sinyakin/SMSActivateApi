package example.api.rent;

import com.sms_activate.SMSActivateApi;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.base.SMSActivateBaseTypeError;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.client_enums.SMSActivateClientRentStatus;
import com.sms_activate.response.api_rent.enums.SMSActivateRentStatus;
import com.sms_activate.response.api_rent.extra.SMSActivateGetRentNumber;

/**
 * <p>To cancel or end the lease, use the setRentStatus method.</p>
 * There are small conditions for establishing each status:
 * <ul>
 *   <li>
 *     <em>CANCEL</em> - If the time has passed less than 20 minutes, then you can cancel the rental with a refund;
 *   </li>
 *   <li>
 *     <em>FINISH</em> - Can be sent at any time for the current lease.
 *   </li>
 * </ul>
 */
public class SetRentStatusRun {
  public static void main(String[] args) {
    try {
      SMSActivateApi smsActivateApi = new SMSActivateApi("API_KEY");
      // 1. Request to get rent number
      SMSActivateGetRentNumber rent = smsActivateApi.getRentNumberByServiceShortName("gb");
      SMSActivateRentStatus smsActivateSetRentStatusResponse = smsActivateApi.setRentStatus(rent.getId(), SMSActivateClientRentStatus.CANCEL);

      // if you need to finish rent
      // SMSActivateRentStatus smsActivateSetRentStatusResponse = smsActivateApi.setRentStatus(rent.getId(), SMSActivateClientRentStatus.FINISH);

      // print desc about new status
      System.out.println("Description of the current lease status: " + smsActivateSetRentStatusResponse.getMessage());
    } catch (SMSActivateWrongParameterException e) {
      e.printStackTrace();
    } catch (SMSActivateBaseException e) {
      if (e.getTypeError() == SMSActivateBaseTypeError.CANT_CANCEL) {
        System.out.println("The lease time has passed more than 20 minutes");
      } else {
        // todo check other type error
        System.out.println(e.getTypeError() + "  " + e.getMessage());
      }
    }
  }
}
