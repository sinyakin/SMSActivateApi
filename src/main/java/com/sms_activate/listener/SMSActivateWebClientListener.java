package com.sms_activate.listener;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

public interface SMSActivateWebClientListener {
  /**
   *
   * @param cid
   * @param request
   * @param statusCode
   * @param response
   */
  void handle(int cid, @NotNull String request, int statusCode, @NotNull String response);
}
