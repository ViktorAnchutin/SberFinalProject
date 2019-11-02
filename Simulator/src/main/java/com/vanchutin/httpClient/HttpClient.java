package com.vanchutin.httpClient;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Slf4j
public class HttpClient {

    private final String producerUrl = "http://localhost:8081/producer";

    public void post(String message){

        try {
            URL url = new URL(producerUrl);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            OutputStream out = con.getOutputStream();

            out.write(message.getBytes());

            log.info("Response Code:"
                    + con.getResponseCode());

        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }

    }


}
