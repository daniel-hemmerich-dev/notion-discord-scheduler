package com.example.notion2scheduler.sdk;

import com.example.notion2scheduler.validator.HTTP;
import com.google.gson.JsonObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Connect to Discord and post a message.
 */
@Service
@ConfigurationProperties(prefix = "discord")
public class Discord {
    private static String uri;

    public String getUri() { return uri; }

    public void setUri(String uri) { Discord.uri = uri; }

    /**
     * Posts a message to Discord.
     * @param message The message to post on Discord.
     */
    public boolean postMessage(final String message) {
        try {
            JsonObject jsonBody = new JsonObject();
            jsonBody.addProperty("content", message);

            HttpRequest request = HttpRequest.newBuilder().
                uri(new URI(Discord.uri))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString()))
                .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> httpResponse = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );

            (new HTTP()).validate(httpResponse);
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }
}
