package com.sms_activate;

import com.sms_activate.error.SMSActivateUnknownException;
import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import com.sms_activate.listener.SMSActivateWebClientListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.GZIPInputStream;

class SMSActivateWebClient {
  /**
   * Counter of the number of requests.
   */
  private static final AtomicInteger COUNT_REQUEST = new AtomicInteger();

  /**
   * Listener for every request.
   */
  private final SMSActivateWebClientListener smsActivateWebClientListener;

  /**
   * Constructor with listener for every request.
   *
   * @param smsActivateWebClientListener listener for every request
   */
  public SMSActivateWebClient(@Nullable SMSActivateWebClientListener smsActivateWebClientListener) {
    this.smsActivateWebClientListener = smsActivateWebClientListener;
  }

  /**
   * Get data or throw common exception if error is happened.
   *
   * @param smsActivateURLBuilder url builder.
   * @param validator             data validator.
   * @return load data from url.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateBaseException           if error happened by base type.
   * @throws SMSActivateUnknownException        if error not documented.
   */
  @NotNull
  public String getOrThrowCommonException(@NotNull SMSActivateURLBuilder smsActivateURLBuilder, @NotNull SMSActivateValidator validator)
    throws SMSActivateBaseException {
    try {
      int cid = COUNT_REQUEST.incrementAndGet();

      HttpURLConnection urlConnection = (HttpURLConnection) smsActivateURLBuilder.build().openConnection();
      urlConnection.setRequestMethod("GET");
      urlConnection.setRequestProperty("accept-Encoding", "gzip, json");

      int statusCode = urlConnection.getResponseCode();

      String data = load(urlConnection);

      if (smsActivateWebClientListener != null) {
        smsActivateWebClientListener.onActivateWebClient(cid, urlConnection.getURL().toString(), statusCode, data);
      }

      validator.throwCommonExceptionByName(data);

      return data;
    } catch (IOException e) {
      throw new SMSActivateUnknownException("Problems with network connection.", e.getMessage());
    }
  }

  /**
   * Returns the data from connection.
   *
   * @param urlConnection connection on server.
   * @return data from server.
   * @throws IOException if an I/O exception occurs.
   */
  @NotNull
  private String load(@NotNull HttpURLConnection urlConnection) throws IOException {
    InputStreamReader inputStreamReader;

    boolean gzip = urlConnection.getContentEncoding().contains("gzip");
    InputStream inputStream = urlConnection.getErrorStream() == null ? urlConnection.getInputStream() : urlConnection.getErrorStream();
    if (gzip) {
      inputStream = new GZIPInputStream(inputStream);
    }
    inputStreamReader = new InputStreamReader(inputStream);

    try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
      String data;
      StringBuilder builder = new StringBuilder();

      while ((data = reader.readLine()) != null) {
        builder.append(data);
      }

      return builder.toString();
    }
  }
}
