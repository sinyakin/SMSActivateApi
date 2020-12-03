package com.sms_activate;

import com.sms_activate.error.base.SMSActivateBaseException;
import com.sms_activate.error.wrong_parameter.SMSActivateWrongParameterException;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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
  public String getOrThrowCommonException(@NotNull SMSActivateURLBuilder smsActivateURLBuilder, @NotNull SMSActivateValidator validator)
    throws SMSActivateBaseException {
    try {
      HttpURLConnection urlConnection = (HttpURLConnection) smsActivateURLBuilder.build().openConnection();
      urlConnection.setRequestMethod("GET");
      urlConnection.setRequestProperty("accept-Encoding", "gzip, json");

      String data = load(urlConnection);
      validator.throwCommonExceptionByName(data);

      return data;
    } catch (IOException e) {
      throw new SMSActivateBaseException("Problems with network connection.", "Проблемы с сетевым подключением.");
    }
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
  public String post(@NotNull URL url, @NotNull List<String> dataList) throws IOException {
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

    urlConnection.setRequestMethod("POST");
    urlConnection.setDoOutput(true);
    urlConnection.setRequestProperty("accept-Encoding", "gzip");

    try (BufferedWriter writer = new BufferedWriter(
      new OutputStreamWriter(new GZIPOutputStream(urlConnection.getOutputStream())))) {
      for (String data : dataList) {
        writer.write(data);
      }
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
  private String load(@NotNull HttpURLConnection urlConnection) throws IOException {
    InputStreamReader inputStreamReader;

    if (urlConnection.getContentEncoding() != null && urlConnection.getContentEncoding().contains("gzip")) {
      inputStreamReader = new InputStreamReader(new GZIPInputStream(urlConnection.getInputStream()));
    } else {
      inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
    }

    BufferedReader reader = new BufferedReader(inputStreamReader);
    String data = read(reader);
    reader.close();
    return data;
  }

  /**
   * Reads the data from stream.
   *
   * @param reader reader.
   * @return data.
   * @throws IOException if an I/O exception occurs.
   */
  @NotNull
  private String read(@NotNull BufferedReader reader) throws IOException {
    String data;
    StringBuilder builder = new StringBuilder();

    while ((data = reader.readLine()) != null) {
      builder.append(data);
    }

    return builder.toString();
  }
}
