package com.example.notion2scheduler.validator;

import java.net.http.HttpResponse;

/**
 * Validates the HTTP response of a request.
 */
public class HTTP {
    /**
     * Checks if the request was successful.
     * @param httpResponse The response of the request.
     */
    public void validate(final HttpResponse<String> httpResponse) throws Exception {
        if (httpResponse.statusCode() < 200 || httpResponse.statusCode() >= 300) {
            throw new Exception(
                String.format(
                    "Incorrect status code: %s for response: %s",
                    httpResponse.statusCode(),
                    httpResponse.body()
                )
            );
        }
    }
}
