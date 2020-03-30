package com.cooper.article.data.feed;

import com.cooper.article.model.Article;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ArticleFeed implements ArticleDao {

    private final String BASE_URL = "https://dev.to/api";

    private final HttpClient client;

    {
        client = HttpClient.newHttpClient();
    }

    @Override
    public List<Article> getTopArticles(Optional<String> param) {

        if(param.isEmpty())
            throw new IllegalArgumentException("The article tag parameter must be provided");

        var params = String.format("articles?tag=%s", param.get());

        // create request to article api
        var request = HttpRequest.newBuilder()
                .uri(URI.create(String.join("/", BASE_URL, params)))
                .build();

        // send request
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // parse JSON response
            if(response.statusCode() == 200) {
                Gson parser = new Gson();
                return parser.fromJson(response.body(), new TypeToken<List<Article>>(){}.getType());
            }

        } catch (IOException | InterruptedException e) {
            // log
        }

        // return empty array if something went wrong and we didn't throw
        return new ArrayList<Article>();
    }
}
