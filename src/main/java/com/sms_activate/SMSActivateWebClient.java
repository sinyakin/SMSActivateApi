package com.sms_activate;

import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

class SMSActivateWebClient {
  /**
   * Get data or throw common exception if error is happened.
   *
   * @param smsActivateURLBuilder url builder.
   * @param validator             data validator.
   * @return load data from url.
   * @throws SMSActivateWrongParameterException if one of parameters is incorrect.
   * @throws SMSActivateBaseException           if error happened on position SMSActivate.
   */
  @NotNull
  public static String getOrThrowCommonException(@NotNull SMSActivateURLBuilder smsActivateURLBuilder, @NotNull SMSActivateValidator validator)
      throws SMSActivateBaseException {
    try {
      String data = get(smsActivateURLBuilder.build());
      validator.throwCommonExceptionByName(data);
      return data;
    } catch (IOException ignored) {
      throw new SMSActivateBaseException("Problem with network connection.", "Проблемы с сетевым подключением.");
    }
  }

  /**
   * Method get to load data by URL object.
   *
   * @param url target url.
   * @return load data from url.
   * @throws IOException if an I/O exception occurs.
   */
  @NotNull
  public static String get(@NotNull URL url) throws IOException {
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    urlConnection.setRequestMethod("GET");
    urlConnection.setRequestProperty("accept-Encoding", "gzip, deflate, json");

    return load(urlConnection);
  }

  /**
   * Method post for send on server.
   *
   * @param url      target url.
   * @param dataList data for server.
   * @return data form url.
   * @throws IOException if an I/O exception occurs.
   */
  @NotNull
  public static String post(@NotNull URL url, @NotNull List<String> dataList) throws IOException {
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

    urlConnection.setRequestMethod("POST");
    urlConnection.setRequestProperty("accept-Encoding", "gzip,deflate");
    urlConnection.setDoOutput(true);

    try (BufferedWriter writer = new BufferedWriter(
        new OutputStreamWriter(new GZIPOutputStream(urlConnection.getOutputStream())))) {
      dataList.forEach(x -> {
        try {
          writer.write(x);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    }

    return load(urlConnection);
  }

  /**
   * Returns the data from connection.
   *
   * @param urlConnection connection on server.
   * @return data from server.
   * @throws IOException if an I/O exception occurs.
   */
  @NotNull
  private static String load(@NotNull URLConnection urlConnection) throws IOException {
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(new GZIPInputStream(urlConnection.getInputStream())))) {
      String data;
      StringBuilder builder = new StringBuilder();

      while ((data = reader.readLine()) != null) {
        builder.append(data);
      }

      return builder.toString();
    }
  }
}
