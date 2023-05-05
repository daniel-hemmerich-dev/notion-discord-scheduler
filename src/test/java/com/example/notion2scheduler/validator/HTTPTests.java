package com.example.notion2scheduler.validator;

import org.junit.jupiter.api.Test;
import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests that the HTTP response is validated correctly.
 */
public class HTTPTests {
    @Test
    public void thatAStatusCodeBetween200And300IsValid() {
        HttpResponse<String> httpResponse = new HttpResponse<>() {
            @Override
            public int statusCode() {
                return 200;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public String body() {
                return null;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };

        try {
            (new HTTP()).validate(httpResponse);
            assertThat(true).isEqualTo(true);
        } catch (Exception exception) {
            assertThat(true).isEqualTo(false);
        }
    }

    @Test
    public void thatAStatusCodeBelow200IsNotValid() {
        HttpResponse<String> httpResponse = new HttpResponse<>() {
            @Override
            public int statusCode() {
                return 199;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public String body() {
                return null;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };

        try {
            (new HTTP()).validate(httpResponse);
            assertThat(true).isEqualTo(false);
        } catch (Exception exception) {
            assertThat(true).isEqualTo(true);
        }
    }

    @Test
    public void thatAStatusCodeOver299IsNotValid() {
        HttpResponse<String> httpResponse = new HttpResponse<>() {
            @Override
            public int statusCode() {
                return 300;
            }

            @Override
            public HttpRequest request() {
                return null;
            }

            @Override
            public Optional<HttpResponse<String>> previousResponse() {
                return Optional.empty();
            }

            @Override
            public HttpHeaders headers() {
                return null;
            }

            @Override
            public String body() {
                return null;
            }

            @Override
            public Optional<SSLSession> sslSession() {
                return Optional.empty();
            }

            @Override
            public URI uri() {
                return null;
            }

            @Override
            public HttpClient.Version version() {
                return null;
            }
        };

        try {
            (new HTTP()).validate(httpResponse);
            assertThat(true).isEqualTo(false);
        } catch (Exception exception) {
            assertThat(true).isEqualTo(true);
        }
    }
}
