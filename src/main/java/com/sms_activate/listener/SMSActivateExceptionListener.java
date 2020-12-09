package com.sms_activate.listener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SMSActivateExceptionListener {
  void handle(@NotNull String errorFromServer);
}
