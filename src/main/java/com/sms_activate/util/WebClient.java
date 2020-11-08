package com.sms_activate.util;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class WebClient {
    public String get(@NotNull String url) throws IOException {
        return get(new URL(url));
    }

    public String get(@NotNull URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        return load(urlConnection);
    }

    public String post(@NotNull String url, @NotNull List<Object> dataList) throws IOException {
        return post(new URL(url), dataList);
    }

    public String post(@NotNull URL url, @NotNull List<Object> dataList) throws IOException {
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

    private String load(URLConnection urlConnection) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream()))) {
            String data;
            StringBuilder builder = new StringBuilder();

            while ((data = reader.readLine()) != null) {
                builder.append(data);
            }

            reader.close();
            return builder.toString();
        }
    }
}
