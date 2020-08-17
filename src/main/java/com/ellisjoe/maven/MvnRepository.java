package com.ellisjoe.maven;

import com.ellisjoe.http.HttpClient;
import org.immutables.value.Value;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

public final class MvnRepository implements Repository {
    private static final String MVN_REPO_URL = "https://mvnrepository.com";

    public MvnRepository() {
    }

    @Override
    public List<Result> search(String query) {
        String uri = new StringBuilder()
                .append(MVN_REPO_URL)
                .append("/search")
                .append("?q=" + HttpClient.escape(query))
                .toString();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();
        String response = HttpClient.request(request);

        List<CompletableFuture<Result>> results = Jsoup.parse(response)
                .getElementById("maincontent")
                .getElementsByClass("im")
                .stream()
                .flatMap(e -> e.getElementsByClass("im-subtitle").stream())
                .map(e -> e.select("a"))
                .map(SearchResult::from)
                .map(e -> getVersion(e).thenApply(v -> Result.of(e.group(), e.artifact(), v)))
                .collect(toList());
        return results.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    private static CompletableFuture<String> getVersion(SearchResult result) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(MVN_REPO_URL + result.artfactRef()))
                .build();
        return HttpClient.asyncRequest(request).thenApply(MvnRepository::parseVersion);
    }

    private static String parseVersion(String response) {
        return Jsoup.parse(response)
                .getElementsByClass("vbtn release")
                .get(0)
                .text();
    }

    @Value.Immutable
    interface SearchResult {
        String group();

        String artifact();

        String artfactRef();

        static SearchResult from(Elements elements) {
            return ImmutableSearchResult.builder()
                    .group(elements.get(0).text())
                    .artifact(elements.get(1).text())
                    .artfactRef(elements.get(1).attr("href"))
                    .build();
        }
    }
}
