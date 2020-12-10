package com.sms_activate.listener;

import org.jetbrains.annotations.NotNull;

public interface SMSActivateErrorListener {
  /**
   * The method is called every time it receives invalid data from the server.
   *
   * @param errorFromServer error name from server.
   */
  void onActivateError(@NotNull String errorFromServer);
}
