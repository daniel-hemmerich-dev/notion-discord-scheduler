package com.example.notion2scheduler.sdk;

import com.example.notion2scheduler.Status;
import com.example.notion2scheduler.validator.HTTP;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Connect to Notion to read and update pages.
 */
@Service
@ConfigurationProperties(prefix = "notion")
public class Notion {
    private static String uri;

    private static String token;

    private static String database;

    public String getUri() { return uri; }

    public void setUri(String uri) { Notion.uri = uri; }

    public String getToken() { return token; }

    public void setToken(String token) { Notion.token = token; }

    public String getDatabase() { return database; }

    public void setDatabase(String database) { Notion.database = database; }

    /**
     * Make a request against the Notion API.
     * @param uri The Uri to append to the base uri.
     * @param body The body data of the request.
     * @param method The HTTP method of the request.
     * @return Return
     */
    private JsonObject request(
        final String uri,
        final JsonObject body,
        final String method
    ) {
        JsonObject jsonResponse = new JsonObject();

        try {
            HttpRequest request = HttpRequest.newBuilder().
                uri(new URI(String.format("%s%s", this.getUri(), uri)))
                .headers("Authorization", this.getToken(), "Notion-Version", "2022-06-28", "Content-Type", "application/json")
                .method(method, HttpRequest.BodyPublishers.ofString(body.toString()))
                .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> httpResponse = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );

            (new HTTP()).validate(httpResponse);
            jsonResponse = JsonParser.parseString(httpResponse.body()).getAsJsonObject();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return jsonResponse;
    }

    /**
     * Returns the response of all pages with the status "scheduled".
     * @return The json of the response.
     */
    public JsonObject selectScheduledPosts() {
        JsonObject jsonBody = new JsonObject();
        JsonObject filter = new JsonObject();
        filter.addProperty("property", "Status");

        JsonObject checkbox = new JsonObject();
        checkbox.addProperty("equals", Status.SCHEDULED.name().toLowerCase());
        filter.add("status", checkbox);

        jsonBody.add("filter", filter);

        return request(
            String.format("databases/%s/query", database),
            jsonBody,
            "POST"
        );
    }

    /**
     * Update a post according to the parameter.
     * @param id The id of the post.
     * @param status The new status of the post.
     * @param color The color of the status label.
     * @return true if the post was updated successfully or false if not.
     */
    private boolean updatePost(
        final String id,
        final Status status,
        final String color
    ) {
        JsonObject jsonBody = new JsonObject();
        JsonObject propertyObject = new JsonObject();
        JsonObject statusObject = new JsonObject();
        JsonObject nameObject = new JsonObject();
        nameObject.addProperty("name", StringUtils.capitalize(status.name().toLowerCase()));
        nameObject.addProperty("color", color);
        statusObject.add("status", nameObject);
        propertyObject.add("Status", statusObject);
        jsonBody.add("properties", propertyObject);

        JsonObject response = request(
            String.format("pages/%s", id),
            jsonBody,
            "PATCH"
        );

        return !response.isEmpty();
    }

    /**
     * Update the post status to "posted".
     * @param id The id of the post.
     * @return true if the post was updated successfully or false if not.
     */
    public boolean markPostAsPosted(final String id) {
        return updatePost(
            id,
            Status.POSTED,
            "green"
        );
    }

    /**
     * Update the post status to "failed".
     * @param id The id of the post.
     * @return true if the post was updated successfully or false if not.
     */
    public boolean markPostAsFailed(final String id) {
        return updatePost(
            id,
            Status.FAILED,
            "red"
        );
    }
}
