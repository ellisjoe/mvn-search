package com.ellisjoe.http;

import com.google.common.escape.Escaper;
import com.google.common.net.UrlEscapers;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class HttpClient {
    private static final Escaper urlEscaper = UrlEscapers.urlPathSegmentEscaper();
    private static final java.net.http.HttpClient httpClient = java.net.http.HttpClient.newHttpClient();

    private HttpClient() {}

    public static String request(HttpRequest request) {
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static String escape(String param) {
        return urlEscaper.escape(param);
    }
}
