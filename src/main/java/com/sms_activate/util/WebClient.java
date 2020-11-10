package com.sms_activate.util;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class WebClient {
  /**
   * Method get to load data by name url.
   *
   * @param url target url.
   * @return load data from url.
   * @throws IOException if an I/O exception occurs.
   */
  //todo
  @NotNull
  public static String get(@NotNull String url) throws IOException {
    return get(new URL(url));
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
  public static String post(@NotNull String url, @NotNull List<Object> dataList) throws IOException {
    return post(new URL(url), dataList);
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
  public static String post(@NotNull URL url, @NotNull List<Object> dataList) throws IOException {
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

    urlConnection.setRequestMethod("POST");
    urlConnection.setDoOutput(true);

    try (BufferedWriter writer = new BufferedWriter(
        new OutputStreamWriter(urlConnection.getOutputStream()))) {
      dataList.forEach(x -> {
        try {
          writer.write(x.toString());
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
  //todo v obshem
  private static String load(@NotNull URLConnection urlConnection) throws IOException {
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(urlConnection.getInputStream()))) {
      String data;
      StringBuilder builder = new StringBuilder();

      while ((data = reader.readLine()) != null) {
        builder.append(data);
      }
      return builder.toString();
    }
  }
}
