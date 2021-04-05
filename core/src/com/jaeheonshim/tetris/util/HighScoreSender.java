package com.jaeheonshim.tetris.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class HighScoreSender {
    private static final String secret = "3QG2Y1p2vz719nF0GiLxMgTz0Kg7V9UO";
    private static Random random = new Random();

    public static void getScores(final Consumer<HighScoreEntry[]> consumer) {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://localhost:8080/highscore").timeout(2000).build();

        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                if(httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
                    String[] res = httpResponse.getResultAsString().trim().split("\n");
                    List<HighScoreEntry> entries = new ArrayList<>();

                    for(String s : res) {
                        if(!s.trim().isEmpty()) {
                            entries.add(new HighScoreEntry(s.split(":")[0], Integer.parseInt(s.split(":")[1])));
                        }
                    }

                    consumer.accept(entries.toArray(new HighScoreEntry[0]));
                }
            }

            @Override
            public void failed(Throwable t) {
                consumer.accept(new HighScoreEntry[0]);
            }

            @Override
            public void cancelled() {

            }
        });
    }

    public static void sendVerifiedScore(String name, int score) {
        String payload = name + ":" + score + ":" + System.currentTimeMillis() + ":" + UUID.randomUUID().toString();
        payload += ("=" + generateHash(payload));

        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.POST).url("http://localhost:8080/highscore").content(payload).build();
        Gdx.net.sendHttpRequest(httpRequest, null);
    }

    private static String generateHash(String payload) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] dataHash = messageDigest.digest(payload.getBytes(StandardCharsets.UTF_8));

        return bytesToHex(dataHash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
