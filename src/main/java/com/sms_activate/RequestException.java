package com.sms_activate;

import com.sms_activate.response.ErrorResponse;
import org.jetbrains.annotations.NotNull;

public class RequestException extends Exception {
    public RequestException(@NotNull ErrorResponse code) {
        super(code.getMessage());
    }
}
