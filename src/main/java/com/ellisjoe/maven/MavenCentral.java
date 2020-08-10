package com.ellisjoe.maven;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.escape.Escaper;
import com.google.common.net.UrlEscapers;
import org.immutables.value.Value;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public final class MavenCentral implements Repository {
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new GuavaModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    public static final Escaper urlEscaper = UrlEscapers.urlPathSegmentEscaper();

    public MavenCentral() {}

    @Override
    public List<Result> search(String query) {
        try {
            String uri = new StringBuilder()
                    .append("https://search.maven.org/solrsearch/select")
                    .append("?q=" + urlEscaper.escape(query))
                    .append("&start=0")
                    .append("&rows=20")
                    .toString();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(uri))
                    .build();
            String response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();

            return (List<Result>) (List<?>) mapper.readValue(response, FullResponse.class).response().docs();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Value.Immutable
    @JsonIgnoreProperties
    @JsonDeserialize(as = ImmutableFullResponse.class)
    interface FullResponse {
        Response response();
    }

    @Value.Immutable
    @JsonDeserialize(as = ImmutableResponse.class)
    interface Response {
        List<Doc> docs();
    }

    @Value.Immutable
    @JsonDeserialize(as = ImmutableDoc.class)
    interface Doc extends Result {
        @JsonProperty("g")
        String group();

        @JsonProperty("a")
        String artifact();

        @JsonProperty("latestVersion")
        String version();
    }
}
