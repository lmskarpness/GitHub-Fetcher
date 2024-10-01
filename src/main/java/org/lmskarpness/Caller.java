package org.lmskarpness;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.URI;

import java.io.IOException;

// https://docs.github.com/en/rest/activity/events?apiVersion=2022-11-28
public class Caller {

    public static String getUser(String username) {
        // Request
        HttpRequest request = HttpRequest.newBuilder()    // GET by default
                .uri(URI.create("https://api.github.com/users/" + username + "/events"))
                .header("Accept", "application/vnd.github.v3+json") // Recommended by GitHub
                .build();                                                       // Build request

        // Response
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
