package com.sms_activate.arch.rent;

import com.sms_activate.arch.main_response.SMSActivateMainResponse;
import com.sms_activate.arch.main_response.SMSActivateMainStatusResponse;
import org.jetbrains.annotations.NotNull;

public class SMSActivateGetRentNumberResponse extends SMSActivateMainResponse {
  private final int id;
  private final String number;
  private final String endDate;
  private final String serviceName;

  public SMSActivateGetRentNumberResponse(int id, @NotNull String number, @NotNull String endDate, @NotNull String serviceName) {
    super(SMSActivateMainStatusResponse.SUCCESS);
    this.id = id;
    this.number = number;
    this.endDate = endDate;
    this.serviceName = serviceName;
  }

  public int getId() {
    return id;
  }

  @NotNull
  public String getNumber() {
    return number;
  }

  @NotNull
  public String getEndDate() {
    return endDate;
  }

  @NotNull
  public String getServiceName() {
    return serviceName;
  }
}
