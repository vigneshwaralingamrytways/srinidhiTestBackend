package com.rytways.utils;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rytways.model.SmsCounter;
import com.rytways.model.Smsapiconfiguration;
import com.rytways.repository.SmsCounterRepo;
import com.rytways.repository.SmsapiconfigurationRepo;


@Service
public class SmsUtils {

    @Autowired
    private SmsCounterRepo smsCounterRepo;

    @Autowired
    private SmsapiconfigurationRepo smsapiconfigurationRepo;

    public void sendSMS(String mobile,Long userId,String mop,String message) {
        try {
            Smsapiconfiguration smsConfig = smsapiconfigurationRepo.findById(1).orElseThrow(() -> new RuntimeException("SMS Configuration not found"));
            int counter = smsCounterRepo.findMaxCounterId().orElse(0) + 1;
            
            String requestUrl = String.format(
                "https://www.smsgatewayhub.com/api/mt/SendSMS?APIKey=%s&senderid=%s&channel=%s&DCS=%s&flashsms=%s&number=%s&text=%s&route=%s",
                URLEncoder.encode(smsConfig.getApiKey(), "UTF-8"),
                URLEncoder.encode(smsConfig.getSenderId(), "UTF-8"),
                URLEncoder.encode(smsConfig.getChannel(), "UTF-8"),
                URLEncoder.encode(smsConfig.getDcs(), "UTF-8"),
                URLEncoder.encode(smsConfig.getFlashSms(), "UTF-8"),
                URLEncoder.encode(mobile, "UTF-8"),
                URLEncoder.encode(message, "UTF-8"),
                URLEncoder.encode(smsConfig.getRoute(), "UTF-8")
            );
            HttpURLConnection connection = (HttpURLConnection) new URL(requestUrl).openConnection();
            int responseCode = connection.getResponseCode();
//            if (responseCode != HttpURLConnection.HTTP_OK) {
//                String errorMessage = new String(connection.getErrorStream().readAllBytes());
//                throw new RuntimeException("Failed to send SMS. HTTP code: " + responseCode + ", Response: " + errorMessage);
//            } else {
//                String response = new String(connection.getInputStream().readAllBytes());
//                System.out.println("SMS API Response: " + response);
//            }
            if (responseCode != HttpURLConnection.HTTP_OK) {
                InputStream errorStream = connection.getErrorStream();
                String errorMessage = "";
                if (errorStream != null) {
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream))) {
                        StringBuilder responseBuilder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            responseBuilder.append(line);
                        }
                        errorMessage = responseBuilder.toString();
                    }
                }
                throw new RuntimeException("Failed to send SMS. HTTP code: " + responseCode + ", Response: " + errorMessage);
            } else {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    String response = responseBuilder.toString();
                    System.out.println("SMS API Response: " + response);
                }
            }


            connection.disconnect();
            
            SmsCounter smsCounter = new SmsCounter();
            smsCounter.setCounterId(counter);
            smsCounter.setUserId(userId);
            smsCounter.setType(mop);
            smsCounter.setCounterDateTime(LocalDateTime.now());
            smsCounterRepo.save(smsCounter);

            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   

}
